<script setup>
import "@/assets/css/board.css";
import StudySearch from "@/components/board/StudySearch.vue";
import StudyRecruitmentGroup from "@/components/board/StudyRecruitmentGroup.vue";
import StudyDetail from "@/components/board/StudyDetail.vue";
import StudyCreate from "@/components/board/StudyCreate.vue";
import Page from "@/components/common/Page.vue";
import { getStudyList, getStudyDetail } from "@/api/board";

import { onMounted, ref } from "vue";
import { useLodingStore } from "@/stores/loading";

const currentPage = ref(1);

const studyDetail = ref(false);
const studyCreate = ref(false);

const studyGroup = ref("");
const detailData = ref("");
const preventCilck = ref("");

const loadingStore = useLodingStore();

const getRegularList = function (params) {
  studyCreate.value = false;
  const success = (res) => {
    if (currentPage.value > 1 && res.data.length === 0) {
      pageChange(0);
    }
    studyGroup.value = res.data;
    loadingStore.loadingSuccess();
    console.log(res.data);
  };
  const fail = (err) => {
    console.log(err);
  };
  getStudyList(params, success, fail);
};

onMounted(() => {
  loadingStore.loading();
  getRegularList();
});

const goDetail = function (group) {
  // console.log(group)
  const success = (res) => {
    console.log(res.data);
    const tiers = {
      SEED: 0,
      ORANGE: 0,
      APPLE: 0,
      KOREAMELON: 0,
      DURIAN: 0,
      KIWI: 0,
    };
    studyDetail.value = true;
    detailData.value = res.data;
    detailData.value.tiers.forEach((element) => {
      tiers[element]++;
    });
    detailData.value.tiers = tiers;
    // console.log(detailData.value)
  };
  const fail = (err) => {
    console.log(err);
  };

  getStudyDetail(group.sessionId, success, fail);
};

const goCreate = function () {
  studyCreate.value = true;
};

const pageChange = function (motion) {
  if (motion === 1) {
    currentPage.value += 1;
  } else if (motion === 0) {
    currentPage.value -= 1;
    if (currentPage.value === 0) {
      currentPage.value = 1;
    }
  }
  getRegularList({ page: currentPage.value });
};

const closeBtn = function () {
  studyDetail.value = false;
};
</script>

<template>
  <div class="container">
    <div class="bold-text sub-title">정기 스터디 모집</div>
    <div class="recruitment">
      <StudyDetail
        v-if="studyDetail"
        :detail-data="detailData"
        @close="closeBtn"
      />
      <StudyCreate v-if="studyCreate" @getRegularList="getRegularList" />
      <div class="flex-align top" style="margin-top: 20px">
        <StudySearch @search="getRegularList" />
        <button class="create-btn bold-text md" @click="goCreate">
          스터디 만들기
        </button>
      </div>
      <div class="study-group">
        <StudyRecruitmentGroup
          v-for="group in studyGroup"
          :key="group.id"
          :group-data="group"
          :class="preventCilck"
          @click="goDetail(group)"
        />
      </div>
      <Page class="page" :current-page="currentPage" @changePage="pageChange" />
    </div>
  </div>
</template>

<style scoped>
.container {
  background-color: transparent;
}
.top {
  width: 95%;
  justify-content: center;
}
/* 스터디 상세정보 클릭시 다른 스터디 클릭 금지 */
.prevent-click {
  pointer-events: none;
}
.create-btn {
  width: 200px;
  height: 80px;
  font-size: 30px;
  margin-left: 100px;
}
/* 정기 스터디 모집 */
.recruitment {
  /* 크기 */
  width: 1750px;
  height: 800px;
  /* 배치 */
  display: flex;
  flex-direction: column;
  align-items: center;

  position: relative;
  margin-top: 20px;
  /* 색깔, 테두리 */
  background-color: #dbe7ff;
  border: 5px solid #3b72ff;
  border-radius: 10px;
}
.study-group {
  display: grid;
  width: 95%;
  height: 100%;
  margin-top: 20px;
  grid-template-columns: 840px 840px;
  grid-template-rows: 150px 150px 150px;
  grid-gap: 30px;
}
.page {
  position: absolute;
  bottom: 10px;
}
</style>
