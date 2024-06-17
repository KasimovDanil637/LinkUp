<template>
  <LoaderPointsComponent style="padding-top: 100px" v-if="!flag"/>
  <div class="template" v-else>
    <div class="wrapper-left">
      <MiniProfileComponent :data="userDataForMini"/>
      <MenuComponent :ticket="mainProfile" @buttonClicked="handleButtonClicked" class="menu"/>
      <SearchComponent @checkFriend="checkInvite"/>
    </div>
    <div class="wrapper-center">
      <ProfileComponent @close="closeProfile" v-if="profile && flag" :data="userData">
        <button @click="edit" class="edit">Редактировать профиль</button>
      </ProfileComponent>
      <EditProfileComponent @saved="editClose" :data="userData" v-if="editor && flag">
        <button @click="editClose" class="btn">Отмена</button>
      </EditProfileComponent>
      <InvitesComponent @checkInvite="checkInvite" v-if="invite"/>
      <FriendSectionComponent v-if="friends" @checkInvite="checkInvite"/>
      <NewsComponent @closeNewsWindow="closeNews" @checkFriend="checkInvite" v-if="news"/>
      <StatsComponent v-if="stats"/>
    </div>
  </div>
</template>

<script setup>
import MenuComponent from "@/components/MenuComponent.vue";
import MiniProfileComponent from "@/components/MiniProfileComponent.vue";
import {onMounted, ref} from "vue";
import ProfileComponent from "@/components/mini-page/ProfileComponent.vue";
import EditProfileComponent from "@/components/mini-page/EditProfileComponent.vue";
import LoaderPointsComponent from "@/components/LoadersComponents/LoaderPointsComponent.vue";
import InvitesComponent from "@/components/mini-page/InvitesComponent.vue";
import FriendSectionComponent from "@/components/mini-page/FriendSectionComponent.vue";
import NewsComponent from "@/components/mini-page/NewsComponent.vue";
import StatsComponent from "@/components/mini-page/StatsComponent.vue";
import SearchComponent from "@/components/SearchComponent.vue";
const profile = ref(true)
const editor = ref(false)
const invite = ref(false)
const friends = ref(false)
const stats = ref(false)
const news = ref(false)
const mainProfile = ref(true)
let userData = ref(Object)
let userDataForMini = ref(Object)
let flag = ref(false);

onMounted(async () => {
  if (profile.value) {
    await getUserData()
  }
});

const getUserData = async (username) => {
  const jwt = localStorage.getItem("token")
  let token = "Bearer " + jwt;
  let url = username === undefined ? "http://localhost:8090/app/user?username=" : 'http://localhost:8090/app/user?username=' + username
  try {
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        Authorization: token
      }
    });
    if (response.ok) {
      flag.value = false
      userData.value = await response.json();
      if (userData.value.auth) {
        userDataForMini.value = userData.value
      }
      flag.value = true
    } else {
      console.error('Ошибка при получении данных:', response.status);
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
  }
}
const edit = () => {
  profile.value = false
  editor.value = true
}
const editClose = () => {
  profile.value = true
  editor.value = false
}
const closeProfile = (flag) => {
  mainProfile.value = flag
}
const closeNews = () => {
    news.value = false
    setTimeout(() => {
      news.value = true
    }, 1)
}
const checkInvite = (username) => {
  getUserData(username)
  invite.value = false
  friends.value = false
  news.value = false
  profile.value = true
  stats.value = false
  mainProfile.value = false;
}
function handleButtonClicked (buttonName)  {
  console.log(buttonName)
  switch (buttonName) {
    case "first": {
      getUserData();
      profile.value = true
      invite.value = false
      friends.value = false
      stats.value = false
      news.value = false
      editor.value = false;
      mainProfile.value = true;
    } break;
    case "second": {
      profile.value = false
      invite.value = true
      friends.value = false
      stats.value = false
      news.value = false
      editor.value = false;
      mainProfile.value = true;
    } break;
    case "third": {
      profile.value = false
      invite.value = false
      friends.value = true
      stats.value = false
      news.value = false
      editor.value = false;
      mainProfile.value = true;
    } break;
    case "four": {
      profile.value = false
      invite.value = false
      friends.value = false
      stats.value = false
      news.value = true
      editor.value = false;
      mainProfile.value = true;
    } break;
    case "fifth": {
      profile.value = false
      invite.value = false
      friends.value = false
      stats.value = true
      news.value = false
      editor.value = false;
      mainProfile.value = true;
    } break;
}}

</script>

<style scoped>
.template {
  display: flex;
}
.wrapper-left {
  margin-top: 10px;
  width: 200px;
  margin-left: var(--left-size);
  margin-right: 10px;
}
.wrapper-center {
  width: var(--centr-size);
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
.btn{
  width: 163px;
  margin-right: 10px;
  height: 35px;
  border: none;
  background-color: var(--intr-color-2);
  color: var(--intr-color-1);
  border-radius: 5px;
  font-size: 16px;
}
.btn:hover {
  background-color: var(--hover-block-2);
}
</style>