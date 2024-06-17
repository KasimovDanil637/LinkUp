<template>
  <div class="container">
    <div class="text-friend"><slot/></div>
    <div class="line"></div>
    <div class="list">
      <button
        @click="checkBtn(data.friend.username)"
        v-if="flag"
        v-for="(data, index) in friendData"
        :key="index"
        class="list-item"
      >
        <FriendIconComponent :data="data" />
      </button>
    </div>
  </div>
</template>

<script setup>
import FriendIconComponent from '@/components/FriendIconComponent.vue'
import {
  defineEmits,
  defineProps,
  onMounted,
  reactive,
  ref,
} from 'vue'
import LoadingComponent from '@/components/LoadersComponents/LoadingComponent.vue'
let flag = ref(false)
let friendData = ref(Object)

const { username, main } = defineProps({
  username: String,
  main: Boolean
})

onMounted(async () => {
  if (main) {
    await getFriendsData()
  } else {
    await getPossibleFriends()
  }
})

const emit = defineEmits(['checkFriend'])
function checkBtn(username) {
  emit('checkFriend', username)
}
const getFriendsData = async () => {
  const jwt = localStorage.getItem('token')
  let token = 'Bearer ' + jwt
  try {
    const response = await fetch(`http://localhost:8090/friend/list?username=${username}`, {
      method: 'GET',
      headers: {
        Authorization: token
      }
    })
    if (response.ok) {
      friendData = await response.json()
      flag.value = true
    } else {
      console.error('Ошибка при получении данных:', response.status)
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error)
  }
}
const getPossibleFriends = async () => {
  const jwt = localStorage.getItem('token')
  let token = 'Bearer ' + jwt
  try {
    const response = await fetch(`http://localhost:8090/friend/possible`, {
      method: 'GET',
      headers: {
        Authorization: token
      }
    })
    if (response.ok) {
      friendData = await response.json()
      flag.value = true
    } else {
      console.error('Ошибка при получении данных:', response.status)
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error)
  }
}
</script>

<style scoped>
.container {
  margin-top: 10px;
  background-color: white;
  height: 260px;
  border: var(--border-1);
  border-radius: 5px;
}
.list-item {
  margin-bottom: 10px;
  border: none;
  width: 230px;
  background-color: var(--block-color-1);
}
.text-friend {
  color: var(--text-color-3);
  font-size: 16px;
  padding-top: 15px;
  padding-left: 15px;
  margin-bottom: 10px;
}
.line {
  width: 220px;
  margin: 0 auto;
  border-bottom: var(--border-1);
}
.list {
  margin: 10px auto;
  width: 238px;
  height: 200px;
  overflow: auto;
}
</style>