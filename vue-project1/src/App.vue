<script setup>
import { ref } from "vue";
import { RouterView } from "vue-router";
</script>

<template>
  <RouterView />
</template>

<style>
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
