<template>
  <div class="container">
    <a class="logo" href="/auth">
      <img class="icon" src="../assets/img/logo.png" alt="">
      <span class="name">LINKUP</span>
    </a>

  </div>
</template>

<script setup>
</script>

<style scoped>
.container {
  display: flex;
  background-color: var(--block-color-1);
  height: 50px;
  width: 100%;
  box-shadow: var(--box-shadow-1);
}
.logo {
  display: flex;
  font-weight: 800;
  font-size: 20px;
  font-family: "Roboto Light", sans-serif;
  text-decoration: none;
  color: var(--text-color-1);
  margin-left: var(--left-size);
  height: 40px;
}
.icon {
  position: absolute;
  margin-top: 6px;
  width: 40px;
}
.name {
  margin-left: 38px;
  padding-top: 15px;
}

</style>