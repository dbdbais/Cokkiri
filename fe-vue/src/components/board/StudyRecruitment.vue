<script setup>
import "@/assets/css/board.css";
import StudySearch from "@/components/board/StudySearch.vue";
import StudyRecruitmentGroup from "@/components/board/StudyRecruitmentGroup.vue";
import StudyDetail from "@/components/board/StudyDetail.vue";
import StudyCreate from "./StudyCreate.vue";
import { getStudyList } from "@/api/board";

import { onMounted, ref } from "vue";
const studyDetail = ref(false);
const studyCreate = ref(false);

const studyGroup = ref(false);
const detailData = ref("");
const preventCilck = ref("");

onMounted(() => {
  const success = (res) => {
    studyGroup.value = res.data;
    console.log(res.data);
  };
  const fail = (err) => {
    console.log(err);
  };
  getStudyList(success, fail);
});

const goDetail = function (group) {
  studyDetail.value = true;
  detailData.value = group;
};

const goCreate = function () {
  studyCreate.value = true;
};

const closeBtn = function () {
  studyDetail.value = false;
};
</script>

<template>
  <div class="container">
    <div class="title sub-title">정기 스터디 모집</div>
    <div class="recruitment">
      <StudyDetail
        v-if="studyDetail"
        :detail-data="detailData"
        @close="closeBtn"
      />
      <StudyCreate
        v-if="studyCreate"
        @createExit="
          () => {
            studyCreate = false;
          }
        "
      />
      <StudySearch />
      <StudyRecruitmentGroup
        v-for="group in studyGroup"
        :key="group.id"
        :group-data="group"
        :class="preventCilck"
        @click="goDetail(group)"
      />
    </div>
    <div class="create-btn btn bold-text md" @click="goCreate">
      스터디 만들기
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
  from {
  }

  to {
  }
}
</style>
