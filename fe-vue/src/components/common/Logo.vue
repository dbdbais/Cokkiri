<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const goHomeLink = function () {
  router.push({ name: "home" });
};
</script>

<template>
  <!-- 로고 클릭 시 메인페이지 이동 -->
  <div class="logo-container" @click="goHomeLink">
    <img src="@/assets/logo.svg" alt="코끼리 로고" />
    <div class="title logo-first-name">코</div>
    <div class="title logo-second-name">끼리</div>
  </div>
</template>

<style scoped>
.logo-container {
  position: relative;
  opacity: 0.5;
}

.logo-first-name,
.logo-second-name {
  /* 위치 */
  position: absolute;
  top: 20px;
}

/* 코 */
.logo-first-name {
  left: 30px;
  color: #9fbaff;
}

/* 끼리 */
.logo-second-name {
  width: 50px;
  left: 45px;
  color: white;
}
</style>
