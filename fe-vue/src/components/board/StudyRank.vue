<script setup></script>

<template>
  <div class="container">
    <div class="sub-title bold-text">이달의 랭킹</div>
    <div class="rank md">
      <img src="/src/assets/system.svg" alt="점검" />
    </div>
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
  width: 500px;
  height: 800px;
  background-color: yellowgreen;
  margin-top: 20px;
  /* 색깔, 테두리 */
  background-color: #dbe7ff;
  border: 5px solid #3b72ff;
  border-radius: 10px;
}
</style>
