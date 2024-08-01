<script setup>
import { onMounted, ref } from "vue";
import RecruitmentBadge from "./RecruitmentBadge.vue";

defineProps({
  groupData: Object,
});

// const isRecruitment = ref(recruitment);
</script>

<template>
  <div class="study-group">
    <RecruitmentBadge :group-badge="groupData.isRecruitment" />
    <h3 class="group-name nomal-text">{{ groupData.groupName }}</h3>

    <div class="created nomal-text">
      {{ groupData.createdAt }}
    </div>

    <div class="group-intro nomal-text">
      {{ groupData.intro }}
    </div>
    <div class="member nomal-text">
      {{ groupData.currentMember }} / {{ groupData.limitMember }}
    </div>
    <span class="nomal-text intro-plus">...</span>
  </div>
</template>

<style scoped>
.member {
  font-size: 35px;
  color: #dbe7ff;

  position: absolute;
  bottom: 5px;
  right: 15px;
  background-color: transparent;
}
.member > img {
  margin-top: 10px;
}
/* 생성날짜 */
.created {
  font-size: 20px;
  color: #dbe7ff;
  background-color: transparent;

  position: absolute;
  top: 2px;
  right: 10px;
}
/* 전체 */
.study-group {
  /* 크기, 위치 */
  width: 1180px;
  height: 120px;
  margin-top: 20px;
  /* 색깔, 테두리 */
  background-color: #c191ff;
  border: 5px solid #3b72ff;
  border-radius: 10px;
  /* 위치 */
  position: relative;

  overflow: hidden;
}
/* 스터디 소개글 생략 */
.intro-plus {
  /* 폰트, 색깔 */
  font-size: 30px;
  color: #dbe7ff;
  background-color: transparent;
  /* 위치 */
  position: absolute;
  bottom: 1px;
  left: 590px;
}
/* 스터디 소개글 */
.group-intro {
  /* 크기 */
  width: 600px;
  height: 50px;
  overflow: hidden;
  /* 색깔, 폰트 */
  color: #dbe7ff;
  background-color: transparent;
  font-size: 30px;
  /* 위치 */
  position: absolute;
  bottom: 0px;
  left: 20px;
}
.group-name {
  /* 위치 */
  position: absolute;
  top: 2px;
  left: 130px;
  /* 색깔 */
  color: #dbe7ff;
  background-color: transparent;

  font-size: 35px;
}

/* 광택 효과 */
.study-group::before {
  position: absolute;
  top: 0;
  left: -85px;
  content: "";
  display: block;
  width: 65px;
  height: 120px;
  transform: skewX(-20deg);
  background-color: #ddc3ff;
}

.study-group:hover {
  color: #fff;
  background-color: #8d39fb;
}

.study-group:hover::before {
  left: 1250px;
  transition: all 1s ease-in;
}
</style>
