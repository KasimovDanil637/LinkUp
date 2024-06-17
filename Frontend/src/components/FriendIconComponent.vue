<template>
    <div class="container-f">
      <div class="avatar-f" :style="avatarStyle"></div>
      <div class="wrapper-f">
        <div class="wrapper-text">{{ data.friend.name}} {{data.friend.secondName}}</div>
        <div class="wrapper-text-2">@{{ data.friend.username}}</div>
      </div>
    </div>
</template>

<script setup>
import {computed, defineEmits, defineProps, onMounted} from 'vue'
import router from "@/router";
const {data} = defineProps({
  data: Object
})
const avatarStyle = computed(() => {
  return {
    backgroundImage: 'url(data:image/jpeg;base64,' + data.avatar.data + ')'
  };
});

</script>

<style scoped>
.avatar-f {
  width: 55px;
  background-color: #89989f;
  height: 55px;
  border-radius: 100%;
  margin-right: 10px;
  background-size: cover;
}
.container-f {
  display: flex;
  width: 100%;
  margin-left: 10px;
}
.wrapper-text {
  padding-top: 10px;
  font-size: 14px;
}
.wrapper-text-2 {
  font-size: 12px;
  color: var(--text-color-2);
  text-align: left;
}

</style>