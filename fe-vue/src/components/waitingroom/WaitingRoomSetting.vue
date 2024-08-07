<script setup></script>

<template>
  <div class="setup box">
    <div class="setup-con">
      <span class="bold-text title">방 설정</span>
      <img src="/src/assets/exit.svg" alt="나가기" class="exit" />
    </div>
  </div>
</template>

<style scoped>
.setup {
  width: 700px;
  height: 700px;
  position: absolute;
  top: 150px;
  left: 600px;

  background-color: #c191ff;
}

.bold-text {
  font-size: 25px;
}
.setup-con {
  padding: 15px 20px;
}
</style>
