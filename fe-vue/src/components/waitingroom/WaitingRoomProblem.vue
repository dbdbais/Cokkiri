<script setup></script>

<template>
  <div class="box problem-container bold-text">
    문제 목록
    <button class="problem-btn bold-text" @click="$emit('open')">선택</button>
    <div class="problem-box box box-col text-font"></div>
  </div>
</template>

<style scoped>
.problem-container {
  width: 660px;
  height: 320px;
  padding: 10px 20px;
  position: relative;
}
.problem-box {
  width: 610px;
  height: 230px;
  margin-top: 10px;
  align-items: center;
}
.problem-btn {
  width: 100px;
  height: 40px;
  position: absolute;
  right: 20px;

  border-radius: 5px;
  border-width: 4px;
  border-radius: 5px;
  border-color: #3b72ff;
  background-color: #c7d5f7;
}
</style>
