<script setup>
import RecruitmentBadge from "./RecruitmentBadge.vue";

defineProps({
  detailData: Object,
});
</script>

<template>
  <div class="study-detail box-p">
    <div class="detail-content">
      <RecruitmentBadge class="badge" />
      <div class="title group-name">{{ detailData.groupName }}</div>
      <div class="top"></div>
      <div class="middle"></div>
      <div class="bottom"></div>
      <button class="content board-btn">가입신청</button>
    </div>
  </div>
</template>

<style scoped>
.top {
  height: 70px;
  background-color: yellow;
}
.middle {
  height: 230px;
  background-color: blue;
}
.bottom {
  height: 200px;
  background-color: gray;
}

.study-detail {
  width: 700px;
  height: 700px;
  z-index: 1;
  position: absolute;
  top: 5px;
  left: 325px;

  display: flex;
  justify-content: center;
}

.badge {
  top: 20px;
  left: 30px;
}
.detail-content {
  margin-top: 70px;
  width: 620px;
  height: 300px;
  background-color: red;
}
.board-btn {
  background-color: #ff4d4d;
}
</style>
