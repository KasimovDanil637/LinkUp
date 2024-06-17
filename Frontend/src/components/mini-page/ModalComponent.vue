<template>
  <div class="modal">
    <slot></slot>
  </div>
</template>

<script setup>
</script>

<style scoped>
.modal {
  position: fixed;
  top: 45%;
  left: 50%;
  transform: translate(-45%, -50%);
  background-color: whitesmoke;
  width: 200px;
  height: 100px;
  z-index: 1;
  border: var(--border-1);
  border-radius: 10px;
}

</style>