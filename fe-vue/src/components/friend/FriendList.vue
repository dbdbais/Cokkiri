<script setup>
import FriendPlusButton from "@/components/friend/FriendPlusButton.vue";
import FriendCategory from "@/components/friend/FriendCategory.vue";
</script>

<template>
  <div class="friend-list">
    <FriendPlusButton />
    <ul class="friends-container">
      <FriendCategory />
    </ul>
  </div>
</template>

<style scoped>
/* 친구 전체 창 */
.friend-list {
  /* 크기 */
  width: 300px;
  height: 500px;
  /* 테두리 */
  border: 5px solid #3b72ff;
  border-radius: 10px;
  /* 위치 */
  position: relative;
  /* 색깔 */
  background-color: #c191ff;

  /* 배치 */
  display: flex;
  flex-direction: column;
  align-items: center;
}
/* 친구 목록 */
.friends-container {
  width: 250px;
  height: 300px;
  background-color: red;
  margin-top: 100px;
}
</style>
