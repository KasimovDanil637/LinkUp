<template>
  <div class="wrapper">
    <div class="header">
      <div class="background-profile"></div>
      <div class="avatar" :style="avatarStyle"></div>
      <div class="name">{{friendData.user.name}} {{friendData.user.secondName}}</div>
      <div class="username">@{{friendData.user.username}}</div>
      <slot v-if="friendData.auth"/>
      <button class="edit" @click="addFriend(friendData.user.username)" v-else-if="!friendData.friend && !accepted && !canceled">Добавить в друзья</button>
      <button class="edit disabled" v-if="accepted || friendData.invited">Запрос отправлен</button>
      <button class="edit" @click="deleteFriend(friendData.user.username)" v-if="friendData.friend && !canceled && !accepted">Удалить из друзей</button>
      <button class="edit disabled" v-else-if="canceled">Удален из друзей</button>
    </div>
    <div class="info">
      <div class="info-wrapper">
        <img class="icon" src="../../assets/icons/birthday.png" alt="" />
        <div class="info-text">День рождения:</div>
        <div class="info-text">{{ formatDate(friendData.info.birthday)  || "Не указано" }}</div>
      </div>
      <div class="info-wrapper">
        <img class="icon" src="../../assets/icons/location.png" alt="" />
        <div class="info-text">Город проживания:</div>
        <div class="info-text">{{ friendData.info.city || "Не указано"}}</div>
      </div>
      <div class="info-wrapper">
        <img class="icon" src="../../assets/icons/edu.png" alt="" />
        <div class="info-text">Место учебы:</div>
        <div class="info-text">{{ friendData.info.education || "Не указано"}}</div>
      </div>
    </div>
    <div class="main-wrapper">
      <div class="posts">
        <PostsWriteComponent @reloadList="reloadList" v-if="friendData.auth" />
        <div class="posts-list">
          <div class="header-list">Все записи</div>
          <div class="line"></div>
          <PostsListComponent class="posts-list-component" @reloadList="reloadList" v-if="postLoading" :data="posts"/>
        </div>
      </div>
      <div class="friends">
        <FriendsComponent :key="friendData.user.username" main :username="friendData.user.username" @checkFriend="openFriend">
          Друзья
        </FriendsComponent>
      </div>
    </div>
  </div>
</template>

<script setup>
import PostsWriteComponent from '@/components/PostsWriteComponent.vue'
import PostsListComponent from '@/components/PostsListComponent.vue'
import FriendsComponent from '@/components/FriendsComponent.vue'
import {ref, defineProps, onMounted, computed, reactive, watch, defineEmits} from 'vue'
const postLoading = ref(false)

const accepted = ref(false)
const canceled = ref(false)
let { data } = defineProps({
  data: Object
})
const friendData = ref(data);
let posts = ref(Object)
const avatarStyle = computed(() => {
  let avatar = friendData.value.avatar.data
  return {
    backgroundImage: 'url(data:image/jpeg;base64,' + avatar + ')'
  };
});
onMounted(async () => {
  await getList(friendData.value.user.username)
})
const reloadList = async () => {
  postLoading.value = false
  await getList(friendData.value.user.username)
}
const openFriend = async (username) => {
  accepted.value = false
  await getFriendData(username)
  await getList(username);
  if (friendData.value.auth){
      close(true)
  } else {
      close(false)
  }
}
function formatDate(dateString) {
  if (!dateString) return null;
  const date = new Date(dateString);
  const options = {day: 'numeric', month: 'long', year: 'numeric'};
  return date.toLocaleDateString('ru-RU', options);
}
const getFriendData = async (username) => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  accepted.value = false
  try {
    const response = await fetch('http://localhost:8090/app/user?username=' + username, {
      method: 'GET',
      headers: {
        Authorization: token
      }
    });
    if (response.ok) {
      friendData.value = await response.json();
      console.log(friendData.value)
    } else {
      console.error('Ошибка при получении данных:', response.status, response.text());
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
}
const addFriend = async (username) => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  try {
    const response = await fetch('http://localhost:8090/invite/add', {
      method: 'POST',
      body: JSON.stringify({
            value: username
          }),
      headers: {
        Authorization: token,
        'Content-type': 'application/json; charset=UTF-8'
      }
    });
    if (response.ok) {
      accepted.value = true
    } else {
      console.error('Ошибка при получении данных:', response.status, response.text());
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
}





const deleteFriend = async (username) => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  try {
    const response = await fetch('http://localhost:8090/friend/delete?username=' + username, {
      method: 'DELETE',
      headers: {
        Authorization: token
      }
    });
    if (response.ok) {
      canceled.value = true
    } else {
      console.error('Ошибка при получении данных:', response.status, response.text());
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
}







const  emit = defineEmits(['close']);
function close(flag) {
  emit('close', flag);
}

const getList = async (username) => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  postLoading.value = false
  try {
    const response = await fetch(`http://localhost:8090/post/list?username=${username}`, {
      method: 'GET',
      headers: {
        Authorization: token,
      }
    }).then((data) => {
      return data.json()
    })
        .then(data => {
          posts.value = data
          postLoading.value = true

        })
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
}
</script>

<style scoped>
.wrapper {
  margin-top: 10px;
}
.main-wrapper {
  display: flex;
  max-height: 500px;
}
.header-list {
  width: 520px;
  font-size: 18px;
  margin-left: 30px;
  padding-top: 15px;
  padding-bottom: 15px;
  color: var(--text-color-3);
}
.posts {
  width: 620px;
  margin-right: 10px;
  margin-bottom: 50px;
}
.posts-list {
  border: var(--border-1);
  background-color: var(--block-color-1);
  border-radius: 5px ;
  margin-top: 10px;
}
.header {
  border: var(--border-1);
  height: 330px;
  background-color: white;
  border-radius: 5px;
  margin-bottom: 10px;
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
  top: 200px;
  margin-left: 20px;
  background-color: #c7c7c7;
  background-size: cover;
  width: 150px;
  height: 150px;
  border-radius: 100%;
  border: 4px solid white;
}
.name {
  margin-top: 15px;
  font-weight: 550;
  font-size: 24px;
  margin-left: 200px;
  margin-bottom: 5px;
}
.username {
  font-weight: 550;
  font-size: 16px;
  margin-left: 200px;
  color: var(--text-color-2);
}

.info {
  width: var(--centr-size);
  display: flex;
  height: 55px;
  background-color: white;
  border-radius: 5px;
  gap: 50px;
  border: var(--border-1);
}
.info-wrapper {
  padding-top: 20px;
  display: flex;
}
.line {
  width: 520px;
  margin: 0 auto;
  border-bottom: var(--border-1);
}
.icon {
  margin-left: 20px;
  margin-right: 3px;
  width: 15px;
  height: 15px;
}
.info-text {
  color: var(--text-color-3);
  font-size: 14px;
  margin-right: 5px;
}
.friends {
  width: 270px;
}
.edit {
  border: none;
  font-size: 16px;
  width: 210px;
  height: 35px;
  position: absolute;
  margin-left: 550px;
  top: 310px;
  color: var(--intr-color-1);
  background-color: var(--intr-color-2);
  border-radius: 5px;
}
.edit:hover {
  background-color: var(--hover-block-2);
}
.disabled {
  width: 210px;
  background-color: var(--disabled-btn);
  color: var(--disabled-text);
}
.disabled:hover {
  background-color: var(--disabled-btn);
  color: var(--disabled-text);
}
.posts-list-component {
  max-height: 700px;
  overflow: auto;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.posts-list-component::-webkit-scrollbar {
  display: none;
}
</style>