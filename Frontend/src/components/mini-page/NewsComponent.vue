<template>
  <div class="news-container">
    <div class="post-section">
      <PostsWriteComponent @reloadList="reloadList"/>
      <div class="post-section-list">
        <PostsListComponent @reloadList="reloadList" @checkFriend="checkProfile" v-if="postLoading" :data="posts"/>
        <LoadingComponent class="loading" v-else/>
      </div>
    </div>
      <div class="filter">
        <div class="filter_header">Фильтр</div>
        <div class="line"></div>
        <div class="filter-items">
          <div class="item-text">По дате публикации </div>
          <div class="item-value">
            <button class="btn-item" :class="{active : day}" @click="doFilterDate('day')">Сегодня</button>
            <button class="btn-item" :class="{active : week}" @click="doFilterDate('week')">Неделя</button>
            <button class="btn-item" :class="{active : month}" @click="doFilterDate('month')">Месяц</button>
            <button class="btn-item" :class="{active : all}" @click="doFilterDate('all')">Все время</button>
          </div>
          <div class="line"></div>
          <div style="display: flex">
             <div class="item-text-2">Понравившиеся</div>
            <div class="toggle" v-if="toggle">
              <input type="checkbox" @click="doFilterLike()" class="check">
              <b class="b switch"></b>
              <b class="b track"></b>
            </div>
          </div>
          <div style="display: flex; margin-bottom: 10px">
            <div class="item-text-2">Только друзья</div>
            <div class="toggle" v-if="toggle">
              <input type="checkbox" @click="doFilterFriend()" class="check">
              <b class="b switch"></b>
              <b class="b track"></b>
            </div>
          </div>
          <div class="line"></div>
          <div class="item-text">Отсортировать по лайкам </div>
          <div class="item-value">
            <button class="btn-item" :class="{active : sortHigh}" @click="doFilterSort('high')">По возрастанию</button>
            <button class="btn-item" :class="{active : sortSmall}" @click="doFilterSort('small')">По убыванию</button>
          </div>
          <div class="line"></div>
          <button class="filter-clear" @click="clearFilter">Сбросить</button>
        </div>
        <FriendsComponent  @checkFriend="checkProfile" style="margin-top: 30px; height: 244px">Возможные друзья</FriendsComponent>
      </div>
  </div>
</template>

<script setup>
import PostsWriteComponent from "@/components/PostsWriteComponent.vue";
import PostsListComponent from "@/components/PostsListComponent.vue";
import {defineEmits, onMounted, ref} from "vue";

import LoadingComponent from "@/components/LoadersComponents/LoadingComponent.vue";
import FriendsComponent from "@/components/FriendsComponent.vue";
let posts = ref(Object)
let postsDefault = ref(Object)
onMounted(async () => {
  await getList()
})

let day = ref(false)
let week = ref(false)
let all = ref(true)
let month = ref(false)
let liked = ref(false)
let friended = ref(false)
let sortHigh = ref(false)
let sortSmall = ref(false)
let toggle = ref(true)
let dataChange = ref(Object)

let postLoading = ref(false);
const getList = async () => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  postLoading.value = false
  try {
    const response = await fetch(`http://localhost:8090/post/all`, {
      method: 'GET',
      headers: {
        Authorization: token,
      }
    }).then((data) => {
      return data.json()
    }).then(data => {
          posts.value = data
          postsDefault.value = data
          dataChange.value = data
          postLoading.value = true
        }).catch((error) => {
      console.log(error)
    })
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
}
const reloadList =async () => {
  postLoading.value = false
  await getList()
}
const doFilterDate = (value) => {
  posts.value = postsDefault.value;
  postLoading.value = false;
  const today = new Date().getDate();
  switch (value) {
    case "day":
      day.value = true;
      week.value = false;
      all.value = false;
      month.value = false;
      posts.value = posts.value.filter((d) => new Date(d.post.time).getDate() === today);
      break;
    case "week":
      day.value = false;
      week.value = true;
      all.value = false;
      month.value = false;
      posts.value = posts.value.filter((d) => {
        const postDate = new Date(d.post.time).getDate();
        return postDate >= today - 7 && postDate <= today;
      });
      break;
    case "month":
      day.value = false;
      week.value = false;
      all.value = false;
      month.value = true;
      posts.value = posts.value.filter((d) => {
        const postDate = new Date(d.post.time).getDate();
        return postDate >= today - 30 && postDate <= today;
      });
      break;
    case "all":
      day.value = false;
      week.value = false;
      all.value = true;
      month.value = false;
      posts.value = postsDefault.value;
      break;
  }
  setTimeout(() => {
    postLoading.value = true
  },200)
};

const doFilterLike = () => {
  liked.value = !liked.value
  postLoading.value = false
  if (liked.value){
    posts.value = posts.value.filter((d) => d.action);
  }else{
    posts.value = postsDefault.value;
  }
  setTimeout(() => {
    postLoading.value = true
  },200)
}

const doFilterFriend= () => {
  friended.value = !friended.value
  postLoading.value = false
  if (friended.value){
    posts.value = posts.value.filter((d) => d.friend);
  }else{
    posts.value = postsDefault.value;
  }
  setTimeout(() => {
    postLoading.value = true
  },200)
}
const doFilterSort = (value) => {
  postLoading.value = false;
  if (value === 'small') {
    sortHigh.value = false
    sortSmall.value = true
    dataChange.value.sort((a, b) => a.likes - b.likes);
  } else {
    sortHigh.value = true
    sortSmall.value = false
    dataChange.value.sort((a, b) => b.likes - a.likes);
  }
  posts.value = dataChange.value
  setTimeout(() => {
    postLoading.value = true
  },200)
}
const  emit = defineEmits(['closeNewsWindow', 'checkFriend']);

const clearFilter = () => {
  emit('closeNewsWindow');
}
const checkProfile = (username) => {
  emit('checkFriend', username);
}
</script>

<style scoped>
.loading {
  margin-top: 250px;
}
.news-container {
  border-radius: 5px;
  margin-bottom: 10px;
  height: 640px;
  width: var(--centr-size);
  display: flex;
}
.post-section {
  width: 570px;
  margin-right: 10px;
}
.post-section-list {
  overflow: auto;
  height: 620px;
  width: 550px;
  border: var(--border-1);
  background-color: var(--block-color-1);
  border-radius: 5px ;
  margin-top: 10px;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.post-section-list::-webkit-scrollbar {
  display: none;
}
.filter {
  margin-top: 10px;
  width: 304px;
  background-color: var(--block-color-1);
  height: 420px;
  border-radius: 5px;
  border: var(--border-1);

}
.filter_header {
  color: var(--text-color-3);
  font-size: 18px;
  margin-left: 20px;
  margin-top: 10px;
  margin-bottom: 10px;
}
.line {
  width: 85%;
  margin: 0 auto;
  border-bottom: var(--border-1);
}
.item-text {
  margin: 10px auto 2px;
  font-size: 14px;
  color: var(--text-color-6);
  border: var(--border-1);
  width: 85%;
  padding: 3px;
  border-radius: 5px;
  text-align: center;
}
.item-text-2 {
  margin-top: 10px;
  margin-left: 13px;
  font-size: 14px;
  color: var(--text-color-6);
  border: var(--border-1);
  padding: 3px;
  width: 150px;
  border-radius: 5px;
  text-align: center;
}
.btn-item {
  height: 25px;
  width: 100%;
  border: none;
  font-size: 13px;
  color: var(--text-color-6);
  background-color: var(--block-color-1);
}
.item-value {
  margin-top: 2px;
  margin-bottom: 10px;
}
.active {
  background-color: var(--hover-block-2) ;
  width: 88%;
  margin-left: 14px;
  border-radius: 5px;
}


.toggle {
  margin-top: 11px;
  margin-left: 180px;
  position: absolute;
  width: 40px;
  height: 20px;
  border-radius: 100px;
  background-color: #ddd;
  overflow: hidden;
  box-shadow: inset 0 0 2px 1px rgba(0,0,0,.05);
}

.check {
  position: absolute;
  display: block;
  cursor: pointer;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  z-index: 6;
}

.check:checked ~ .track {
  box-shadow: inset 0 0 0 20px var(--hover-block-2);
}

.check:checked ~ .switch {
  right: 2px;
  left: 22px;
  transition: .35s cubic-bezier(0.785, 0.135, 0.150, 0.860);
  transition-property: left, right;
  transition-delay: .05s, 0s;
}

.switch {
  position: absolute;
  left: 2px;
  top: 2px;
  bottom: 2px;
  right: 22px;
  background-color: #fff;
  border-radius: 36px;
  z-index: 1;
  transition: .35s cubic-bezier(0.785, 0.135, 0.150, 0.860);
  transition-property: left, right;
  transition-delay: 0s, .05s;
  box-shadow: 0 1px 2px rgba(0,0,0,.2);
}

.track {
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  transition: .35s cubic-bezier(0.785, 0.135, 0.150, 0.860);
  box-shadow: inset 0 0 0 2px rgba(0,0,0,.05);
  border-radius: 40px;
}

.filter-clear {
  border: none;
  border-radius: 5px;
  width: 90%;
  margin-left: 13px;
  margin-top: 10px;
  height: 25px;
  font-size: 13px;
  padding: 3px;
  background-color: var(--hover-block-2);
}
</style>
