<template>
  <div>
    <form @submit.prevent.stop="submit">
      <div class="error">{{message.message || message_password}}</div>
      <div v-if="isLoading">
        <LoadingComponent style="margin-top: 100px"/>
      </div>
      <div class="wrapper" v-else>
        <div class="span">Введите ваше имя</div>
        <input class="input" required type="text" placeholder="Username" v-model="formData.name" />
        <div class="span">Введите вашу фамилию</div>
        <input class="input" required type="text" placeholder="Username" v-model="formData.secondName" />
        <div class="span">Придумайте имя пользователя</div>
        <input class="input" required type="text" placeholder="Username" v-model="formData.username" />
        <div class="span">Введите почту</div>
        <input class="input" required type="text" placeholder="Email" v-model="formData.email" />
        <div class="span">Придумайте пароль</div>
        <input class="input" required type="password" placeholder="Password" v-model="formData.password" />
        <div class="span">Повторите пароль</div>
        <input class="input" required type="password" placeholder="Password" v-model="secondPassword" />
        <button class="submit" type="submit">Зарегистрироваться</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import router from "@/router";
import LoadingComponent from "@/components/LoadersComponents/LoadingComponent.vue";
const isLoading = ref(false)
const user = ref(null)
const message = ref('')
const message_password = ref('')
const formData = reactive({
  name: '',
  secondName: '',
  username: '',
  email: '',
  password: ''
})
const secondPassword = ref('')
const submit = () => {
  isLoading.value = true
  localStorage.removeItem("username");

  if (formData.password.length < 6) {
    message_password.value = 'Пароль должен содержать не менее 6 символов'
    isLoading.value = false
    return
  }

  if (formData.password !== secondPassword.value) {
    message_password.value = 'Пароли не совпадают'
    isLoading.value = false
    return
  }

  fetch('http://localhost:8090/auth/signup', {
    method: 'POST',
    body: JSON.stringify(formData),
    headers: {
      'Content-type': 'application/json; charset=UTF-8'
    }
  })
      .then(async (response) => {
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
  margin-top: 15px;
}
.input {
  width: 250px;
  margin-bottom: 15px;
  height: 30px;
  border: var(--border-1);
  border-radius: 10px;
  margin-left: 20px;
  background-color: var(--block-color-2);
}
.span {
  margin-left: 20px;
  font-size: 12px;
  margin-bottom: 3px;
}
.submit {
  width: 255px;
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
  top: 120px;
  width: 300px;
  text-align: center;
}
</style>