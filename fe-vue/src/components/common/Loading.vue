<script setup></script>

<template>
  <div class="loading-wrap box-col">
    <div class="box-col loading-con">
      <img src="@/assets/loading.gif" alt="로딩중" class="loading" />
      <div class="flex-align loading-text">
        <div class="bold-text">로딩중</div>
        <span class="bold-text">.</span><span class="bold-text">.</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.loading-wrap {
  width: 1920px;
  height: 1080px;
  justify-content: center;
  align-items: center;
  background-color: #dbe7ff;
  position: fixed;
  z-index: 10;
}

.loading-con {
  justify-content: center;
  align-items: center;
}

.loading {
  width: 200px;
  top: 220px;
}

.loading-text {
  margin-top: 40px;
  margin-bottom: 40px;
}

.bold-text {
  font-size: 50px;
  -webkit-text-stroke: 2px black;
}


.loading-text span {
  animation: bounce 1.2s cubic-bezier(0.3, 0.01, 0.4, 1) infinite;
}

.loading-text span:nth-child(1) {
  animation-delay: -0.4s;
}

.loading-text span:nth-child(2) {
  animation-delay: -0.2s;
}

@keyframes bounce {

  0%,
  100% {
    transform: translateY(0);
  }

  50% {
    transform: translateY(-20px);
  }
}
</style>
