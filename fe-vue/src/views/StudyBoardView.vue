<template>
  <Logo class="logo" />
  <div class="header">
    <div class="bold-text top-title">스터디 모집 게시판</div>
  </div>
  <div class="board-home">
    <div class="board-container">
      <StudyRecruitment />
    </div>
  </div>
</template>

<script setup>
import StudyRecruitment from "@/components/board/StudyRecruitment.vue";
import StudyRank from "@/components/board/StudyRank.vue";
import Logo from "@/components/common/Logo.vue";
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
}

.top-title {
  margin-left: 330px;
  margin-top: 15px;
  font-size: 50px;
}

.board-home {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

/* 정기 스터디 모집 게시판 전체 */
.board-container {
  /* 크기 */
  width: 1850px;
  height: 900px;
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
</style>
