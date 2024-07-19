<script setup>
import StudySearch from "@/components/board/StudySearch.vue";
import StudyRecruitmentGroup from "@/components/board/StudyRecruitmentGroup.vue";
import StudyDetail from "@/components/board/StudyDetail.vue";
import { ref } from "vue";
const studyDetail = ref(false);

const studyGroup = ref([
  {
    id: 1,
    isRecruitment: true,
    groupName: "집나간 김종덕",
    intro: "코딩 스터디 모집합니다. 마음편히 오세요. 어서오세요",
    currentMember: 2,
    limitMember: 4,
    createdAt: "2024.07.19",
  },
  {
    id: 2,
    isRecruitment: false,
    groupName: "돌아와 김종덕",
    intro: "코딩 스터디 모집합니다. 마음편히 오세요. 어서오세요",
    currentMember: 5,
    limitMember: 5,
    createdAt: "2024.07.19",
  },
  {
    id: 3,
    isRecruitment: true,
    groupName: "가랏! 김종덕",
    intro: "코딩 스터디 모집합니다. 마음편히 오세요. 어서오세요",
    currentMember: 3,
    limitMember: 6,
    createdAt: "2024.07.09",
  },
  {
    id: 4,
    isRecruitment: false,
    groupName: "김종덕1",
    intro: "코딩 스터디 모집합니다. 마음편히 오세요. 어서오세요",
    currentMember: 5,
    limitMember: 5,
    createdAt: "2024.07.19",
  },
]);
const detailData = ref("");
const preventCilck = ref("");

const goDetail = function (group) {
  studyDetail.value = true;
  detailData.value = group;
};
</script>

<template>
  <div class="container">
    <h3 class="title small-title">정기 스터디 모집</h3>
    <div class="recruitment">
      <StudyDetail v-if="studyDetail" :detail-data="detailData" />
      <StudySearch />
      <StudyRecruitmentGroup
        v-for="group in studyGroup"
        :key="group.id"
        :group-data="group"
        :class="preventCilck"
        @click="goDetail(group)"
      />
      <!-- <StudyRecruitmentGroup />
      <StudyRecruitmentGroup />
      <StudyRecruitmentGroup /> -->
    </div>
  </div>
</template>

<style scoped>
.container {
  background-color: transparent;
}
.modal {
}
.small-title {
  font-size: 25px;
  background-color: transparent;
  margin-bottom: 5px;
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
  from {
  }
  to {
  }
}
</style>
