<template>
  <div class="board-home">
    <h2 class="title top-title">스터디 모집 게시판</h2>
    <div class="board-container">
      <StudyRecruitment />
      <StudyRank />
    </div>
  </div>
</template>

<script setup>
import StudyRecruitment from "@/components/board/StudyRecruitment.vue";
import StudyRank from "@/components/board/StudyRank.vue";
</script>

<style scoped>
.board-home {
  display: flex;
  justify-content: center;
}
/* 정기 스터디 모집 게시판 전체 */
.board-container {
  /* 크기 */
  width: 1450px;
  height: 740px;
  /* 위치 */
  margin-top: 10px;
  padding-top: 10px;
  position: relative;
  /* 색깔, 테두리 */
  background-color: #c191ff;
  border: 5px solid #3b72ff;
  border-radius: 10px;
  /* 배치 */
  display: flex;
  justify-content: center;
}
/* 맨위 글자 */
.top-title {
  position: absolute;
  top: 30px;
  left: 130px;
}
</style>
