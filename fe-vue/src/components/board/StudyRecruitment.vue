<script setup>
import StudySearch from "@/components/board/StudySearch.vue";
import StudyRecruitmentGroup from "@/components/board/StudyRecruitmentGroup.vue";
import StudyDetail from "@/components/board/StudyDetail.vue";
import { ref } from "vue";
import studyGroupJson from "@/assets/data/studyGroup.json";
const studyDetail = ref(false);

const studyGroup = ref(studyGroupJson);
const detailData = ref("");
const preventCilck = ref("");

const goDetail = function (group) {
  studyDetail.value = true;
  detailData.value = group;
};

const closeBtn = function () {
  studyDetail.value = false;
};
</script>

<template>
  <div class="container">
    <div class="title sub-title">정기 스터디 모집</div>
    <div class="recruitment">
      <StudyDetail v-if="studyDetail" :detail-data="detailData" @close="closeBtn" />
      <StudySearch />
      <StudyRecruitmentGroup v-for="group in studyGroup" :key="group.id" :group-data="group" :class="preventCilck"
        @click="goDetail(group)" />

    </div>
  </div>
</template>

<style scoped>
.container {
  background-color: transparent;
}

/* 스터디 상세정보 클릭시 다른 스터디 클릭 금지 */
.prevent-click {
  pointer-events: none;
}

/* 정기 스터디 모집 */
.recruitment {
  /* 크기 */
  width: 950px;
  height: 680px;
  /* 배치 */
  display: flex;
  flex-direction: column;
  align-items: center;

  /* 색깔, 테두리 */
  background-color: #dbe7ff;
  border: 5px solid #3b72ff;
  border-radius: 10px;
}

@keyframes downModal {
  from {}

  to {}
}
</style>
