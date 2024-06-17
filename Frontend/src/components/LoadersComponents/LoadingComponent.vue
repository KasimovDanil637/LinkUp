<template>
  <div class="loading-container">
    <div class="loader"></div>
  </div>
</template>

<script setup>
</script>

<style>
.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.loader {
  border: 8px solid #f3f3f3;
  border-top: 8px solid var(--green-button);
  border-radius: 50%;
  width: 20px;
  height: 20px;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

</style>
