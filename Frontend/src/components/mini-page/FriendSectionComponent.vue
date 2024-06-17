<template>
  <div class="wrapper">
    <div class="header">
      <div class="header-text">Друзья</div>
      <div class="line"></div>
      <div class="header-wrapper">
        <div v-if="flag"
             class="button"
             :key="index"
             v-for="(item, index) in friendData">
          <FriendIconComponent class="friend_item" :data="item"/>
          <button class="btn-view" @click="checkInvite(item.friend.username)">Профиль</button>
        </div>
        <div class="nothing" v-if="data.length === 0">
          Список друзей пуст
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {defineEmits, defineProps, onMounted, reactive, ref} from "vue";
import FriendIconComponent from "@/components/FriendIconComponent.vue";
let data = reactive(Object)
const flag = ref(false)
const friendData = ref(Object);
const {username} = defineProps({
  username: String
})

onMounted(async () => {
  await getFriendsData(username)
})
const getFriendsData = async () => {
  const jwt = localStorage.getItem('token')
  let token = 'Bearer ' + jwt
  try {
    const response = await fetch(`http://localhost:8090/friend/list?username=`, {
      method: 'GET',
      headers: {
        Authorization: token
      }
    })
    if (response.ok) {
      friendData.value = await response.json()
      flag.value = true
    } else {
      console.error('Ошибка при получении данных:', response.status);
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error)
  }
}
const emit = defineEmits(['checkInvite'])
function checkInvite(username) {
  emit('checkInvite', username)
}
</script>


<style scoped>
.wrapper{
  margin-top: 10px;
}
.header-wrapper {
  margin: 10px 30px;
}
.button {
  width: 100%;
  border: var(--border-1);
  border-radius: 5px;
  background-color: var(--block-color-1);
  margin-bottom: 10px;
}
.header {
  border: var(--border-1);
  background-color: white;
  border-radius: 5px;
  margin-bottom: 10px;
  height: 640px;
  width: var(--centr-size);
}
.btn-view {
  background-color: var(--intr-color-2);
  color: var(--intr-color-1);
  border: none;
  border-radius: 5px;
  position: absolute;
  margin-left: 600px;
  margin-top: -53px;
  width: 100px;
  height: 30px;
}
.header-text {
  font-size: 22px;
  margin-left: 50px;
  margin-top: 30px;
  color: var(--text-color-2);
  margin-bottom: 20px;
}
.line {
  width: 93%;
  margin: 0 auto;
  border: var(--border-1)
}
.nothing {
  position: absolute;
  color: var(--text-color-3);
  margin: 220px 290px;
}
.friend_item {
  padding: 10px;
}
</style>