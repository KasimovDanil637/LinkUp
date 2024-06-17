<template>
  <div class="search-wrapper">
    <div class="wrapper">
      <input class="search-input" type="text" v-model="username" placeholder="Поиск" @input="search">
      <button class="btn" @click="search">
        <img class="search-img" src="../assets/icons/search.png" alt="">
      </button>
    </div>
    <ul v-if="searchResults.length > 0" class="search-results">
      <li v-for="result in searchResults" :key="result.id">
        <div class="container-f" @click="checkProfile(result.friend.username)">
        <div class="avatar-f" :style="avatarStyle(result)"></div>
        <div class="wrapper-f">
          <div class="wrapper-text">{{ result.friend.name}} {{result.friend.secondName}}</div>
          <div class="wrapper-text-2">@{{ result.friend.username}}</div>
        </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script setup>
import {computed, defineEmits, ref, watch} from "vue";

let username = ref('');
let searchResults = ref([]);

watch((username), () => {
  if (username.value.length === 0){
    searchResults.value = []
  }
})
const avatarStyle = computed((result) => {
  return (result) => {
    return {
      backgroundImage: 'url(data:image/jpeg;base64,' + result.avatar.data + ')'
    };
  };

});
const search = async () => {
  const jwt = localStorage.getItem("token");
  let token = "Bearer " + jwt;
  if (username.value.length > 2) {
    try {
      const response = await fetch(`http://localhost:8090/search?username=${username.value}`, {
        method: 'GET',
        headers: {
          Authorization: token
        }
      });
      if (response.ok) {
        const data = await response.json();
        console.log(data)
        searchResults.value = data;
      } else {
        console.error('Ошибка при получении данных:', response.status, response.text());
      }
    } catch (error) {
      console.error('Ошибка при выполнении запроса:', error);
    }
  }
};
const emit = defineEmits(['checkFriend'])
const checkProfile = (username) => {
  emit('checkFriend', username)
}
</script>

<style scoped>
.search-results {
  margin-top: 45px;
  position: absolute;
  width: 198px;
  border-radius: 5px;
  background-color: var(--block-color-1);
  list-style-type: none;
  padding: 0;
  border: var(--border-1);
}

.search-results li {
  padding: 5px;
  cursor: pointer;
}

.search-results li:hover {
  background-color: var(--hover-block-4);
  border-radius: 5px;
}

.search-input {
  border-radius: 10px;
  background-color: var(--block-color-1);
  height: 40px;
  outline: none;
  border: none;
  padding: 0;
  margin-left: 10px;
  width: 150px;
  margin-right: 5px;
}
.search-img {
  margin-top: 4px;
  width: 20px;
  height: 20px;
}
.btn {
  border: none;
  background-color: var(--block-color-1);
}
.wrapper {
  display: flex;
}
.avatar-f {
  width: 45px;
  background-color: red;
  height: 45px;
  border-radius: 100%;
  margin-right: 10px;
  background-size: cover;
}
.container-f {
  display: flex;
  width: 100%;
}
.wrapper-text {
  padding-top: 7px;
  font-size: 14px;
}
.wrapper-text-2 {
  font-size: 12px;
  color: var(--text-color-2);
  text-align: left;
}
.search-wrapper {
  margin-top: 10px;
  width: 199px;
  height: 40px;
  border: var(--border-1);
  border-radius: 5px;
  background-color: var(--block-color-1);
  display: flex;
}
</style>