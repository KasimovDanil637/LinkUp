<template>
  <div>
    <form @submit.prevent.stop="submit">
      <div class="error">{{message.message}}</div>
      <div v-if="isLoading">
        <LoadingComponent style="margin-top: 100px "/>
      </div>
      <div class="wrapper" v-else>
        <div class="span">Ваше имя пользователя</div>
        <input class="input" required type="text" placeholder="Username" v-model="formData.username">
        <div class="span">Ваш пароль</div>
        <input class="input" required type="password" placeholder="Password" v-model="formData.password">
        <button class="submit" type="submit">Войти</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import router from "@/router";
import LoadingComponent from "@/components/LoadersComponents/LoadingComponent.vue";
const isLoading = ref(false)
const message = ref('')
const formData = reactive({
  username:'',
  password:''
})

const submit = () => {
  isLoading.value = true

  localStorage.removeItem("username");
  fetch('http://localhost:8090/auth/signin', {
    method: 'POST',
    body: JSON.stringify(formData),
    headers: {
      'Content-type': 'application/json; charset=UTF-8'
    }
  })
      .then( async (response) => {
        if (!response.ok) {
         message.value = await response.json()
        }
        return response.json()
      })
      .then((jwt) => {
        localStorage.setItem("token", jwt.token)
        router.push("/main")
      })
      .catch((error) => console.error(error))
      .finally(() => {
        isLoading.value = false
      })
}
</script>

<style scoped>
.wrapper {
  margin-top: 20px;
}
.input {
  width: 250px;
  margin-bottom: 20px;
  height: 35px;
  border: var(--border-1);
  border-radius: 10px;
  margin-left: 20px;
  background-color: var(--block-color-2);
}
.span{
  margin-left: 20px;
  font-size: 12px;
  margin-bottom: 3px;
}
.submit {
  width: 255px;
  margin-bottom: 20px;
  height: 35px;
  border: var(--border-1);
  border-radius: 10px;
  margin-left: 20px;
  background-color: var(--green-button);
}
.error {
  font-size: 14px;
  color: var(--text-color-5);
  position: absolute;
  top: 130px;
  width: 300px;
  text-align: center;
}
</style>
