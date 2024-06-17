<template>
  <div class="wrapper">
      <div class="header">
        <div class="background-profile"></div>
        <label for="file-upload" class="custom-file-upload">
          <img class="avatar" v-if="previewFilePath !== '#'" :src="previewFilePath" alt="">
          <div class="avatar-bg" v-else :style="avatarStyle"></div>
        </label>
        <input id="file-upload" type="file" @click="switchFlag"    @change="uploadFile" style="display: none;">
        <div class="form">
          <div class="error">{{error.message}}</div>
          <div class="form-mini">
            <div class="form-item">
              <div class="form-text">Ваше имя</div>
              <input type="text" class="form-input" @change="switchFlag" v-model="source.user.name">
            </div>
            <div class="form-item">
              <div class="form-text">Ваша фамилия</div>
              <input type="text" class="form-input" @change="switchFlag" v-model="source.user.secondName">
            </div>
            <div class="form-item">
              <div class="form-text">Город проживания</div>
              <input type="text" class="form-input" @change="switchFlag" v-model="source.info.city">
            </div>
          </div>
          <div class="form-mini">
            <div class="form-item">
              <div class="form-text">Место учебы</div>
              <input type="text" class="form-input" @change="switchFlag" v-model="source.info.education">
            </div>
            <div class="form-item">
              <div class="form-text">Дата рождения</div>
              <input type="date" class="form-input" @change="switchFlag" v-model="source.info.birthday">
            </div>
            <div class="btns">
              <button @click="send" class="btn" :class="{isDisabled : !flag}" :disabled="!flag">Сохранить</button>
              <slot/>
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script setup>
import {computed, defineEmits, defineProps, onMounted, reactive, ref, watch} from "vue";
const img = ref(null)
const error = ref('')
const saved = ref(false)
const { data } = defineProps({
  data: Object
})
const source = reactive(data);
const flag = ref(false)
const sendData = reactive({
  name: source.user.name,
  secondName: source.user.secondName,
  birthday: source.info.birthday,
  city: source.info.city,
  education: source.info.education,
})

const avatarFlag = ref(false)
const previewFilePath = computed(() => {
  if (img.value) {
    avatarFlag.value = true;
    return URL.createObjectURL(img.value)
  }
  return "#";
})
const avatarStyle = computed(() => {
  return {
    backgroundImage: 'url(data:image/jpeg;base64,' + data.avatar.data + ')'
  };
});
const uploadFile = (e) => {
  const [file] = e.target.files
  source.avatar.data = file
  img.value = file
}
const saveAvatar = async () => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  const formData = new FormData();
  formData.append('file', source.avatar.data);
  try {
    const response = await fetch('http://localhost:8090/app/updateAvatar', {
      method: 'POST',
      body: formData,
      headers: {
        Authorization: token,
      }
    });
    if (response.ok) {
      saved.value = true
      location.reload()
    } else {
      error.value = await response.json();
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
}
const save = async () => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  try {
    const response = await fetch('http://localhost:8090/app/update', {
      method: 'POST',
      body: JSON.stringify(sendData),
      headers: {
        Authorization: token,
        'Content-type': 'application/json; charset=UTF-8'
      }
    });
    if (!response.ok) {
      console.error('Ошибка при получении данных:', response.status);
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
}
watch( source, () => {
  sendData.name = source.user.name;
  sendData.secondName = source.user.secondName;
  sendData.birthday = source.info.birthday;
  sendData.city = source.info.city;
  sendData.education = source.info.education;
  data.avatar.data = source.avatar.data
});
const  emit = defineEmits(['saved']);
function redirect() {
  emit('saved');
}
const switchFlag = () => {
  flag.value = true
}
const send = () => {
  save()
  if (avatarFlag.value) {
    saveAvatar()
  } else {
    redirect()
  }
  if (saved.value){
    redirect()
  }
}
</script>
<style scoped>
.error {
  position: absolute;
  margin-left: 300px;
  margin-top: -100px;
  color: var(--text-color-5);
}
.wrapper {
  margin-top: 10px;
}
.avatar-bg {
  position: absolute;
  top: 150px;
  margin-left: 20px;
  width: 210px;
  border: 4px solid white;
  height: 220px;
  background-size: cover;
}
.header {
  border: var(--border-1);
  background-color: white;
  border-radius: 5px;
  margin-bottom: 10px;
  height: 500px;
  width: var(--centr-size);
}
.background-profile {
  background-image: url('../../assets/img/img_2.png');
  background-size: cover;
  width: var(--centr-size);
  height: 220px;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
}
.avatar {
  position: absolute;
  top: 150px;
  margin-left: 20px;
  width: 210px;
  border: 4px solid white;
  height: 220px;
  background-size: cover;
}
.form {
  display: flex;
  margin-top: 120px;
}
.form-item{
  display: flex;
  margin-bottom: 10px;
}
.form-text {
  color: var(--text-color-2);
  margin-right: 10px;
  font-size: 14px;
  width: 140px;
  border: var(--border-1);
  border-radius: 5px;
  display: flex;
  text-align: center;
  justify-content: center;
  align-items: center;
  margin-left:30px;
}
.form-input{
  border: none;
  width: 180px;
  background-color: var(--block-color-2);
  border: var(--border-1);
  border-radius: 5px;
  height: 30px;
  font-size: 14px;
  color: var(--text-color-2);
  outline: none;
}
.form-mini {
  width: 400px;
}
.btns {
  margin-left: 30px;
}
.btn{
  width: 163px;
  margin-right: 10px;
  height: 35px;
  border: none;
  background-color: var(--intr-color-2);
  color: var(--intr-color-1);
  border-radius: 5px;
  font-size: 16px;
}
.btn:hover {
  background-color: var(--hover-block-2);
}
.isDisabled {
  background-color: var(--disabled-btn);
  color: var(--disabled-text);
}
.isDisabled:hover{
  background-color: var(--disabled-btn);
  color: var(--disabled-text);
}
</style>