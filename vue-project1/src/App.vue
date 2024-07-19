<script setup>
import Logo from "@/components/Logo.vue";
import { ref } from "vue";

import { RouterLink, RouterView, useRouter } from "vue-router";

// router 사용
const router = useRouter();
</script>

<template>
  <RouterView />
</template>

<style scoped>
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
