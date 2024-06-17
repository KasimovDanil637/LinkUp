<template>
  <div class="wrapper">
      <div class="mini-avatar" :style="avatarStyle"></div>
      <div class="name">{{data.user.name}} {{data.user.secondName}}</div>
      <div class="username">@{{data.user.username}}</div>
  </div>
</template>

<script setup>
import {computed, defineProps} from 'vue'
import LoadingComponent from "@/components/LoadersComponents/LoadingComponent.vue";

const { data } = defineProps({
  data: Object
})
const avatarStyle = computed(() => {
  return {
    backgroundImage: 'url(data:image/jpeg;base64,' + data.avatar.data + ')'
  };
});
</script>

<style scoped>
.wrapper {
  height: 55px;
  margin-top: 2px;
  margin-bottom: 10px;
  background-color: var(--block-color-1);
  border-radius: 5px;
}
.mini-avatar {
  position: absolute;
  margin-top: 5px;
  margin-left: 5px;
  background-size: cover;
  width: 45px;
  height: 45px;
  border-radius: 100%;
}
.name {
  font-size: 14px;
  padding-top: 12px;
  margin-left: 60px;
}
.username {
  margin-left: 60px;
  font-size: 12px;
  color: var(--text-color-2);
}
</style>