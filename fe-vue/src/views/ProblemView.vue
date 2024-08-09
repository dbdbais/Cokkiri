<script setup>
import { ref } from "vue";
import Logo from "@/components/common/Logo.vue";
import ProblemList from "@/components/problem/ProblemList.vue";
import ProblemSearch from "@/components/problem/ProblemSearch.vue";

const currentPage = ref(1);
const totalPage = ref(1);

const prevPage = function () {
  if (currentPage.value > 1) {
    currentPage.value -= 1;
  }
};

const nextPage = function () {
  if (currentPage.value < totalPage.value) {
    currentPage.value += 1;
  }
};

const setTotalPage = function (total) {
  totalPage.value = total;
};
</script>

<template>
  <Logo />
  <div class="main-con box-col">
    <div class="header">
      <span class="title">문제 목록</span>
    </div>
    <div class="problem-con">
      <ProblemSearch />
      <ProblemList :current-page="currentPage" @emit-total-page="setTotalPage" />
      <button @click="prevPage">&lt;</button>
      <button @click="nextPage">&gt;</button>
    </div>
  </div>
</template>

<style scoped>
.main-con {
  display: flex;
  width: 1920px;
  height: 1080px;
  justify-content: center;
  align-items: center;
}

.header {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 1920px;
  height: 130px;
  font-size: 60px;
  text-align: center;
}

.problem-con {
  width: calc(1920px - 250px);
  height: calc(1080px - 180px);
  background-color: #bed0fd;
  border: 10px solid #3b72ff;
  border-radius: 20px;
  padding: 50px 80px;
}
</style>
