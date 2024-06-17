<template>
  <div class="post">
    <div class="header-post">
      <div v-if="item.avatar.data"  @click="checkProfile" :style="avatarStyle" class="avatar"></div>
      <div class="user">
        <div class="name"  @click="checkProfile">{{item.post.user.name}} {{item.post.user.secondName}}</div>
        <div class="date-container" >
          <div class="date">{{formattedDate}}</div>
        </div>
        <div class="delete" v-if="item.auth || item.admin">
          <button @click="deletePost" class="delete-btn">
            <img class="delete-img" src="../assets/icons/delete.png" alt="">
          </button>
        </div>
      </div>
    </div>
    <div class="message" v-if="item.post.message">
      <div class="message-text">
        {{item.post.message}}
      </div>
    </div>
    <div class="photo" v-if="item.post.photo" :style="photoStyle"></div>
    <div>
      <div class="container-likes">
        <button v-if="!actionFlag" class="like-btn" @click="sendAction('LIKE')">
          <img class="like" src="../assets/icons/like.png" alt="">
          <span class="count-like">{{likes}}</span>
        </button>
        <button v-if="actionFlag" class="like-btn active" @click="sendAction('LIKE')">
          <img class="like" src="../assets/icons/likeActive.png" alt="">
          <span class="count-like active-count">{{likes}}</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {computed, defineEmits, defineProps, ref} from 'vue'
import moment from 'moment';


const {item, avatar} = defineProps({
  item: Object,
})

const actionFlag = ref(item.action);
let likes = ref(item.likes)

const avatarStyle = computed(() => {
  return {
    backgroundImage: 'url(data:image/jpeg;base64,' + item.avatar.data + ')'
  };
});
const photoStyle = computed(() => {
  return {
    backgroundImage: 'url(data:image/jpeg;base64,' + item.post.photo + ')'
  };
});

const formattedDate = computed(() => {
  let month = moment(item.post.time).format('MM')
  let day = moment(item.post.time).format('DD')
  let year = moment(item.post.time).format('YYYY')
  let time = moment(item.post.time).format('HH:mm');

  let currentDate = new Date();
  let currentYear = currentDate.getFullYear();
  let currentDay = currentDate.getDate();

  let date_one = Number(currentDay) ===  Number(day) ? "сегодня в " + time : day + " " + formatDate[month] + " в " + time
  let date_two = day + " " + formatDate[month] + " " + year;

  return  year === currentYear + "" ? date_one : date_two
});
const emit = defineEmits(['checkFriend','reloadList'])
const checkProfile = () => {
  emit('checkFriend', item.post.user.username)
}
const deletePost = async () => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  try {
    const response = await fetch(`http://localhost:8090/post/delete?id=${item.post.id}`, {
      method: 'DELETE',
      headers: {
        Authorization: token,
        'Content-type': 'application/json; charset=UTF-8'
      }
    });
    if (response.ok) {
      reload()
    } else {
      console.error('Ошибка при получении данных:', response.status, response.text());
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
}

const reload = () => {
  emit('reloadList');
}

const sendAction = async (action) => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  try {
    const response = await fetch(`http://localhost:8090/post/action`, {
      method: 'POST',
      body: JSON.stringify({
        id: item.post.id,
        username: localStorage.getItem("username"),
      }),
      headers: {
        Authorization: token,
        'Content-type': 'application/json; charset=UTF-8'
      }
    });
    if (response.ok) {
      actionFlag.value = !actionFlag.value;
      if (actionFlag.value) {
        likes.value += 1;
      } else {
        likes.value -= 1;
      }

    } else {
      console.error('Ошибка при получении данных:', response.status, response.text());
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
}
const formatDate = {
  "01":"янв",
  "02":"фев",
  "03":"мар",
  "04":"апр",
  "05":"мая",
  "06":"июня",
  "07":"июля",
  "08":"авг",
  "09":"сен",
  "10":"окт",
  "11":"нояб",
  "12":"дек",
}
</script>

<style scoped>
.header-post {
  display: flex;
  margin: 10px 20px 15px 10px;
}
.avatar {
  background-size: cover;
  width: 50px;
  height: 50px;
  border-radius: 100%;
  margin-right: 15px;
}
.post {
  margin: 20px;
}
.user {
  margin-top: 5px;
}

.date{
  font-size: 14px;
  color: var(--text-color-3);
}
.time {
  color: var(--text-color-3);
  font-size: 14px;
}
.photo {
  width: 480px;
  margin-left: 15px;
  border-radius: 5px;
  height: 600px;
  margin-bottom: 15px;
}
.message {
  margin-left: 15px;
  width: 90%;
  margin-bottom: 15px;
}
.date-container {
  margin-top: 2px;
}
.delete{
  width: 30px;
  margin-left: 375px;
  margin-top: -35px;
}
.delete-img{
  width: 20px;
}
.delete-btn {
  border: none;
  background-color: var(--block-color-1);
}
.like {
  width: 20px;
  margin-right: 7px;
}
.container-likes {
  margin-left: 10px;
  display: flex;
}
.like-btn {
  height: 32px;
  width: 68px;
  border-radius: 30px;
  background-color: #f3f3f3;
  display: flex;
  justify-content: center;
  align-items: center;
  //border: 0.5px solid var(--text-color-3);
  border: none;
}
.count-like {
  font-size: 14px;
  color: var(--text-color-3);
}
.active {
  //border: 0.5px solid var(--text-color-5);
  background-color: #ffe8e8;
}
.active-count {
  color: var(--text-color-5);
}
</style>