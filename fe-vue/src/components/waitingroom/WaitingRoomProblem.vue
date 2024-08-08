<script setup></script>

<template>
  <div class="box problem-container bold-text">
    문제 목록
    <button class="problem-btn" @click="$emit('open')">선택</button>
    <div class="problem-box box box-col text-font"></div>
  </div>
</template>

<style scoped>
.problem-container {
  width: 660px;
  height: 320px;
  padding: 10px 20px;
}
.problem-box {
  width: 610px;
  height: 220px;
  margin-top: 10px;
  align-items: center;
}
.problem-btn {
  width: 100px;
  height: 40px;
}
</style>
