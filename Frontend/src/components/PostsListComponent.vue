x<template>
  <div class="container">
    <div v-if="data.length === 0">
      <div class="no-item">Пока нет ни одной записи</div>
    </div>
    <div v-else v-for="(item, index) in data">
      <PostListItemComponent @reloadList="reload" @checkFriend="checkProfile" :key="index" :item="item"/>
      <div class="line"></div>
      <br>
    </div>
  </div>
</template>

<script setup>
import {defineEmits, defineProps, onMounted, ref} from "vue";
import PostListItemComponent from "@/components/PostListItemComponent.vue";
let {data} = defineProps({
  data: Object
})

const  emit = defineEmits(['checkFriend', 'reloadList']);

const checkProfile = (username) => {
  emit('checkFriend', username);
}
const reload = () => {
  emit('reloadList');
}

</script>

<style scoped>

.line {
  width: 520px;
  margin: 0 auto;
  border-bottom: var(--border-1);
}
.no-item {
  display: flex;
  justify-items: center;
  text-align: center;
  align-content: center;
  align-items: center;
  justify-content: center;
  width: 520px;
  height: 197px;
  color: var(--text-color-3);
}
.container-2 {
  border: var(--border-1);
  background-color: var(--block-color-1);
  border-radius: 5px ;
}
</style>