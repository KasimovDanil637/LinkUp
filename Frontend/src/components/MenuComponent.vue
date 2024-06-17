<template>
  <div class="wrapper">
    <button class="button" @click="check('first')" :class="{ isActive : first && ticket }">Мой профиль</button>
    <button class="button" @click="check('second')" :class="{ isActive : second }">Уведомления</button>
    <button class="button" @click="check('third')" :class="{ isActive : third }">Друзья</button>
    <button class="button" @click="check('four')" :class="{ isActive : four }">Новости</button>
    <button class="button" @click="check('fifth')" :class="{ isActive : fifth }">Статистика</button>
    <button class="button" @click="exit(null)">Выйти</button>
    <ModalComponent v-if="exitFlag">
      <div class="exit-text">Хотите выйти?</div>
      <button class="btn-exit" @click="exit(true)">Да</button>
      <button class="btn-exit" @click="exit(false)">Нет</button>
    </ModalComponent>
  </div>
</template>

<script setup>

import {ref} from "vue";

const first = ref(true)
const second = ref(false)
const third = ref(false)
const four = ref(false)
const fifth = ref(false)
const exitFlag = ref(false)
import { defineProps, defineEmits } from 'vue';
import router from "@/router";
import ModalComponent from "@/components/mini-page/ModalComponent.vue";


const {ticket} = defineProps({
  ticket: Boolean
})


const  emit = defineEmits(['buttonClicked']);
const exit = (flag) => {
  switch (flag){
    case null: exitFlag.value = true; break;
    case true: {
      localStorage.removeItem("token")
      router.push("/auth")
    } break;
    case false:exitFlag.value = false; break
  }

}
function checkBtn(buttonName) {
  emit('buttonClicked', buttonName);
}

const check = (item) => {
  checkBtn(item)
  switch (item) {
    case "first": {
      first.value = true
      second.value = false
      third.value = false
      four.value = false
      fifth.value = false
    } break;
    case "second": {
      first.value = false
      second.value = true
      third.value = false
      four.value = false
      fifth.value = false
    } break;
    case "third": {
      first.value = false
      second.value = false
      third.value = true
      four.value = false
      fifth.value = false
    } break;
    case "four": {
      first.value = false
      second.value = false
      third.value = false
      four.value = true
      fifth.value = false
    } break;
    case "fifth": {
      first.value = false
      second.value = false
      third.value = false
      four.value = false
      fifth.value = true
    } break;
  }
}
</script>

<style scoped>
.wrapper {
  height: 265px;
  background-color: var(--block-color-1);
  border-radius: 5px;
}
.exit-text {
  font-size: 16px;
  text-align: center;
  margin-top: 20px;
}
.btn-exit {
  margin-top: 10px;
  width: 70px;
  height: 30px;
  border: var(--border-1);
  border-radius: 5px;
  margin-left: 30px;
  background-color: var(--block-color-1);
}
.btn-exit:last-child {
  margin-left: 10px;
}
.button {
  background-color: var(--block-color-1);
  width: 200px;
  height: 40px;
  border: none;
  margin-bottom: 3px;
}
.button:hover{
  background-color: var(--hover-block-1);
}
.button:first-child{
  margin-top: 5px;
}
.isActive {
  background-color: var(--hover-block-1) ;
  border-left: 3px solid var(--intr-color-1);
  box-sizing: border-box;
}
.isNonActive {
  background-color: red ;
  border-left: 3px solid var(--intr-color-1);
  box-sizing: border-box;
}
</style>