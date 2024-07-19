<script setup>
import Logo from "@/components/Logo.vue";
import { ref } from "vue";

import { RouterLink, RouterView, useRouter } from "vue-router";
import { useLinkStore } from "./stores/link";

// router 사용
const router = useRouter();
// link 이동과 관련된 상태를 관리할 store
const linkStore = useLinkStore();
</script>

<template>
  <div style="display: flex;">
    <Logo class="logo" />
    <!-- 스터디 모집 게시판 클릭 시 이동(이동 후 스터디 모집 게시판 글자 삭제) -->
    <RouterLink :to="{ name: 'studyRecruitment' }" v-if="linkStore.studyRecruitmentlink"
      @click="linkStore.gostudyRecruitment">스터디 모집 게시판</RouterLink>
  </div>
  <RouterView />
</template>

<style>
.logo {
  margin-top: 15px;
  margin-left: 30px;
}

/* 링크 (스터디 모집 게시판, 문제 목록) */
a {
  color: white;
  text-decoration: none;
  /* 폰트 */
  font-size: 30px;
  font-family: "RixInooAriDuriR";
  -webkit-text-stroke: 1px black;
}
</style>
