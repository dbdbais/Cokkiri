<script setup></script>

<template>
  <div class="container">
    <div class="title sub-title">이달의 랭킹</div>
    <div class="rank"></div>
  </div>
</template>

<style scoped>
.container {
  background-color: transparent;
  margin-left: 40px;
}
/* 이달의 랭킹 */
.rank {
  /* 크기 */
  width: 400px;
  height: 680px;
  background-color: yellowgreen;

  /* 색깔, 테두리 */
  background-color: #dbe7ff;
  border: 5px solid #3b72ff;
  border-radius: 10px;
}
</style>
