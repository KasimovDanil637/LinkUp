<template>
  <div class="input-container">
    <textarea @click="getSend" required class="textarea" v-model="inputText" @input="resizeTextarea" placeholder="Что у вас нового?"></textarea>
    <label for="file-upload" class="btn-img">
      <img v-if="!send" class="img" src="../assets/icons/photo.png" alt="">
    </label>
    <input id="file-upload" @change="uploadFile" type="file" style="display: none;">
    <div v-if="send" class="container-img">
      <img class="pre-img" :class="{none : !noneFlag}" :src="previewFilePath" alt="">
      <div class="btns">
        <div class="error">{{error.message || error2}}</div>
        <label for="file-upload" class="img item">
          <img v-if="send" class="img" src="../assets/icons/photo.png" alt="">
        </label>
        <input id="file-upload" @change="uploadFile" type="file" style="display: none;">
        <button class="btn item" @click="submit">Отправить</button>
        <button @click="clear" class="btn item">Отменить</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {computed, defineEmits, ref} from "vue";
let inputText = ref('')
let send = ref(false)
let value = ref(null)
let noneFlag = ref(false)
let error = ref('')
let error2 = ref('')
const savePhoto = async () => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  const formData = new FormData();
  formData.append('file', value.value);
  try {
    const response = await fetch('http://localhost:8090/post/savephoto', {
      method: 'POST',
      body: formData,
      headers: {
        Authorization: token,
      }
    });
    if (response.ok) {
      reload()
      clear()
    } else {
      error.value = await response.json()
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
}
const savePost = async () => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  try {
    const response = await fetch('http://localhost:8090/post/savemessage', {
      method: 'POST',
      body: JSON.stringify({
        value: inputText.value || " "
      }),
      headers: {
        Authorization: token,
        'Content-type': 'application/json; charset=UTF-8'
      }
    });
    if (response.ok) {
      if (value.value !== null) {
        await savePhoto()
      } else {
        reload()
        clear()
      }
    } else {
      console.error('Ошибка при получении данных:', response.status, response.text());
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
}

const submit = async () => {
  if (inputText.value.length > 0 || value.value != null ) {
    await savePost()
  } else {
    error2.value = "Нельзя отправить пустой пост"
  }
}

const emit = defineEmits([ 'reloadList']);

const reload = () => {
  emit('reloadList');
}

const resizeTextarea = (event) => {
  event.target.style.height = 'auto'
  event.target.style.height = (event.target.scrollHeight) + 'px'
}
const getSend = () => {
  send.value = true
}
const clear = () => {
  const ta = document.querySelector('.textarea')
  noneFlag.value = false
  ta.style.height = "25px"
  inputText.value = ""
  send.value = false
  value.value = null
  error.value = ''
}
const previewFilePath = computed(() => {
  if (value.value) {
    return URL.createObjectURL(value.value)
  }
  return "#"
})
const uploadFile = (e) => {
  noneFlag.value = true
  send.value = true
  const [file] = e.target.files
  value.value = file
}

</script>

<style scoped>
.input-container {
  margin-top: 10px;
  background-color: var(--block-color-1);
  border: var(--border-1);
  border-radius: 5px;
  margin-bottom: 10px;
}

.textarea {
  width: 470px;
  height: 25px;
  resize: none;
  overflow: hidden;
  margin-left: 20px;
  margin-right: 10px;
  margin-top: 10px;
  border: none;
}
.textarea:focus{
  outline: none;
}
.btns{
  margin-top: 10px;
  display: flex;
  margin-left: 285px;
  padding-bottom: 10px;
}
.error {
  position: absolute;
  margin-left: -240px;
  margin-top: 6px;
  font-size: 14px;
  color: var(--text-color-5);
}
.btn {
  width: 100px;
  height: 30px;
  background-color: var(--intr-color-2);
  color: var(--intr-color-1);
  border: none;
  border-radius: 5px;
}
.btn:first-child{
  margin-right: 10px;
}
.img {
  width: 30px;
}
.btn-img {
  margin-top: 7px;
  position: absolute;
  border: none;
  background-color: var(--block-color-1);
}
.pre-img {
  border-radius: 5px;
  margin-top: 20px;
  margin-bottom: 20px;
  margin-left: 25px;
  width: 500px;
}
.item {
  margin-right: 10px;
}
.none {
  display: none;
}
</style>
