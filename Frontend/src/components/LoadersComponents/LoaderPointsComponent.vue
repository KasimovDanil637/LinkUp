<template>
  <div><span class="loader-p"></span></div>
</template>

<script setup>
</script>

<style scoped>
.loader-p {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  display: block;
  margin: 20px auto;
  position: relative;
  left: -100px;
  box-sizing: border-box;
  animation: shadowRolling 2s linear infinite;
}

@keyframes shadowRolling {
  0% {
    box-shadow: 0px 0 rgba(255, 255, 255, 0), 0px 0 rgba(255, 255, 255, 0), 0px 0 rgba(255, 255, 255, 0), 0px 0 rgba(255, 255, 255, 0);
  }
  12% {
    box-shadow: 100px 0 #0065ff, 0px 0 rgba(255, 255, 255, 0), 0px 0 rgba(255, 255, 255, 0), 0px 0 rgba(255, 255, 255, 0);
  }
  25% {
    box-shadow: 110px 0 rgb(123, 204, 92), 100px 0 #0065ff, 0px 0 rgba(255, 255, 255, 0), 0px 0 rgba(255, 255, 255, 0);
  }
  36% {
    box-shadow: 120px 0 #fffc00, 110px 0 rgb(123, 204, 92), 100px 0 #0065ff, 0px 0 rgba(255, 255, 255, 0);
  }
  50% {
    box-shadow: 130px 0 #ff4b4b, 120px 0 #fffc00, 110px 0 #7bcc5c, 100px 0 #0065ff;
  }
  62% {
    box-shadow: 200px 0 rgba(255, 255, 255, 0), 130px 0 #ff4b4b, 120px 0 #fffc00, 110px 0 #7bcc5c;
  }
  75% {
    box-shadow: 200px 0 rgba(255, 255, 255, 0), 200px 0 rgba(255, 255, 255, 0), 130px 0 #ff4b4b, 120px 0 #fffc00;
  }
  87% {
    box-shadow: 200px 0 rgba(255, 255, 255, 0), 200px 0 rgba(255, 255, 255, 0), 200px 0 rgba(255, 255, 255, 0), 130px 0 #ff4b4b;
  }
  100% {
    box-shadow: 200px 0 rgba(255, 255, 255, 0), 200px 0 rgba(255, 255, 255, 0), 200px 0 rgba(255, 255, 255, 0), 200px 0 rgba(255, 255, 255, 0);
  }
}
</style>