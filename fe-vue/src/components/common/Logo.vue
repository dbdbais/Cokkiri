<script setup>
</script>

<template>
  <!-- 로고 클릭 시 메인페이지 이동 -->
  <RouterLink :to="{ name: 'home' }">
    <div class="logo-container" @click="goHomeLink">
      <img src="@/assets/logo.svg" alt="코끼리 로고" />
      <div>
        <span class="title logo-first-name">코</span>
        <span class="title logo-second-name">끼리</span>
      </div>
    </div>
  </RouterLink>
</template>

<style scoped>
.logo-container {
  position: absolute;
  top: 20px;
  left: 40px;
  opacity: 0.5;
}

img {
  width: 100px;
  height: 100px;
}

span {
  font-size: 50px;
}


.logo-first-name,
.logo-second-name {
  /* 위치 */
  position: absolute;
  top: 30px;
  -webkit-text-stroke-width: 1.5px;
}

/* 코 */
.logo-first-name {
  left: 110px;
  color: #9fbaff;
}

/* 끼리 */
.logo-second-name {
  left: 160px;
  color: white;
  width: 100px;
}
</style>
