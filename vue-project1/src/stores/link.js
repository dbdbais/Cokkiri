import { ref, computed } from "vue";
import { defineStore } from "pinia";
// 링크 이동과 관련된 상태를 관리하는 store
export const useLinkStore = defineStore(
  "link",
  () => {
    // 스터디 모집 게시판 글자 표시 (T/F)
    const studyRecruitmentlink = ref(true);
    // 스터디 모집 게시판 클릭 시 호출
    const gostudyRecruitment = function () {
      studyRecruitmentlink.value = false;
    };
    // 메인 페이지 이동 시 호출
    const goHome = function () {
      studyRecruitmentlink.value = true;
    };
    return { studyRecruitmentlink, gostudyRecruitment, goHome };
  },
  // localStorage에 저장
  { persist: true }
);
