<template>
  <div class="wrapper">
      <div class="container" :class="{reg : regCheck, log : loginCheck}">
        <button class="button left" @click="checkL" :class="{ isActive : loginCheck, b_left : !loginCheck }">Войти</button>
        <button class="button right" @click="checkR" :class="{ isActive : regCheck, b_right: !regCheck }">Зарегистрироваться</button>
        <LoginComponent v-if="loginCheck"/>
        <RegistrationComponent v-if="regCheck"/>
      </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import LoginComponent from "@/components/mini-page/LoginComponent.vue";
import RegistrationComponent from "@/components/mini-page/RegistrationComponent.vue";

const loginCheck = ref(true);
const regCheck = ref(false)

const checkL = () => {
  loginCheck.value = true
  regCheck.value = false
}
const checkR = () => {
  loginCheck.value = false
  regCheck.value = true
}
</script>

<style scoped>
.wrapper {
  width: 100%;
  padding-top: 100px;
}
.button {
  background-color: var(--block-color-2);
  font-size: 12px;
  width: 50%;
  height: 50px;
  border: 0;
  border-bottom: var(--border-1);
}
.left {
  border-radius: 10px 0 10px 0;
}
.right {
  border-radius: 0 10px 0 10px;
}
.isActive {
  background-color: var(--block-color-1);
  border-bottom: none;
}
.reg {
  background-color: var(--block-color-1);
  margin: 0 auto;
  width: 300px;
  height: 520px;
  border-radius: 10px;
  border: 1px solid rgba(0, 0, 0, 0.1);
}
.log {
  background-color: var(--block-color-1);
  margin: 0 auto;
  width: 300px;
  height: 300px;
  border-radius: 10px;
  border: 1px solid rgba(0, 0, 0, 0.1);
}
.b_left{
  border-right: var(--border-1);
}
.b_right {
  border-left: var(--border-1);
}
</style>