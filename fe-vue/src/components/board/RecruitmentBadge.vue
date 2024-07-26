<script setup>
defineProps({
  groupBadge: Boolean,
});
</script>

<template>
  <div
    class="recruitment-badge nomal-text"
    :class="groupBadge ? 'open' : 'close'"
  >
    {{ groupBadge ? "모집중" : "종료" }}
  </div>
</template>

<style scoped>
.recruitment-badge {
  /* 크기 */
  width: 80px;
  height: 40px;
  /* 폰트, 테두리 */
  border: 5px solid;
  border-radius: 10px;
  font-size: 15px;
  /* 배치 */
  display: flex;
  justify-content: center;
  align-items: center;
  /* 위치 */
  position: absolute;
  top: 5px;
  left: 10px;
}
/* 모집중 */
.open {
  border-color: #3b72ff;
  color: #3b72ff;
  background-color: #dbe7ff;
}
/* 종료 */
.close {
  border-color: #b5b5b5;
  color: #b5b5b5;
  background-color: #6e6e6f;
}
</style>
