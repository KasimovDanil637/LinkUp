<template>
  <div class="container-f">
    <div class="avatar-f" :style="avatarStyle"></div>
    <div class="wrapper-f">
      <div class="wrapper-text">{{ data.friend.name }} {{ data.friend.secondName }}</div>
      <div class="wrapper-text-2">@{{data.friend.username}}</div>
    </div>
    <div class="btns" >
      <button class="btn" @click="answer(data.friend.username, true)" v-if="!accepted && !canceled">Принять</button>
      <button class="btn disabled" v-else-if="accepted">Принято</button>
      <button class="btn"  @click="answer(data.friend.username, false)" v-if="!canceled && !accepted">Отклонить</button>
      <button class="btn disabled" v-else-if="canceled">Отклонено</button>
      <button style="display: none" v-else>Отклонено</button>
    </div>
  </div>
</template>

<script setup>
import {computed, defineProps, ref} from 'vue'
const { data } = defineProps({
  data: Object
})
const accepted = ref(false)
const canceled = ref(false)
const message = ref('')
const avatarStyle = computed(() => {
  let avatar = data.avatar.data
  return {
    backgroundImage: 'url(data:image/jpeg;base64,' + avatar + ')'
  };
});
const answer = async (username, flag) => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  try {
    const response = await fetch('http://localhost:8090/friend/add', {
      method: 'POST',
      body: JSON.stringify({
        username: username,
        flag: flag
      }),
      headers: {
        Authorization: token,
        'Content-type': 'application/json; charset=UTF-8'
      }
    });
    if (response.ok) {
      message.value = await response.json()
      if (message.value.message === "accept") {
        accepted.value = true;
      } else {
        canceled.value = true
      }
    } else {
      console.error('Ошибка при получении данных:', response.status, response.text());
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
}
</script>

<style scoped>
.btns {
  display: flex;
  margin-left: 13%;
  margin-top: 20px;
}
.btn{
  width: 100px;
  border: none;
  height: 30px;
  border-radius: 5px;
  margin-right: 10px;
  background-color: var(--intr-color-2);
  color: var(--intr-color-1);
}
.disabled {
  width: 200px;
  background-color: var(--disabled-btn);
  color: var(--disabled-text);
}
.avatar-f {
  width: 70px;
  background-color: white;
  height: 70px;
  border-radius: 100%;
  margin-right: 20px;
  background-size: cover;
}
.wrapper-f {
  text-align: left;
  width: 300px;
}
.container-f {
  margin: 10px 20px;
  display: flex;
  width: 100%;
}
.wrapper-text {
  padding-top: 10px;
  font-size: 18px;
}
.wrapper-text-2 {
  font-size: 16px;
  color: var(--text-color-2);
  text-align: left;
}
</style>