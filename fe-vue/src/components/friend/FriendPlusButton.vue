<script setup></script>

<template>
  <div class="friend-plus">
    <button class="friend-plus-btn">
      <img src="@/assets/friend-plus.svg" alt="친구추가" />
      <div class="friend-plus-text">친구추가</div>
    </button>
  </div>
</template>

<style scoped>
/* 버튼 */
.friend-plus-btn {
  /* 위치 */
  padding: 10px;
  position: absolute;
  top: 10px;
  left: 20px;
  /* 배치 */
  display: flex;
  align-items: center;
  color: #dbe7ff;
  /* 테두리 */
  border-width: 7px;
  border-radius: 10px;
  border-color: #9fbaff;
}

/* 버튼 안 글자 */
.friend-plus-text {
  /* 위치 */
  padding-left: 15px;
  /* 폰트 */
  font-family: "RixInooAriDuriR";
  font-size: 22px;
  color: white;
  -webkit-text-stroke: 1px black;
}
</style>
