<script setup></script>

<template>
  <div class="box-pdb search">
    <button class="title search-btn">검색</button>
  </div>
</template>

<style scoped>
/* 검색창 */
.search {
  /* 크기, 위치 */
  width: 880px;
  height: 70px;
  margin-top: 20px;
  position: relative;
}

.search-btn {
  padding: 10px 20px;
  position: absolute;
  top: 3px;
  right: 3px;

  font-size: 20px;

  border-width: 5px;
  border-radius: 10px;
  border-color: #8aabff;
  background-color: #cfdbfb;
}
</style>
