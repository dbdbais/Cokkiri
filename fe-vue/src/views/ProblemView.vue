<script setup>
import { ref, watch } from "vue";
import Logo from "@/components/common/Logo.vue";
import ProblemList from "@/components/problem/ProblemList.vue";
import ProblemSearch from "@/components/problem/ProblemSearch.vue";
import { problemStore } from "@/stores/problem";

const currentPage = ref(1);
const totalPage = ref(1);
const pStore = problemStore();

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

const clearPage = function () {
  currentPage.value = 1;
};

watch(() => pStore.problems, (newValue, oldValue) => {
  setTotalPage(Math.ceil(newValue.length / 8));
});

</script>

<template>
  <Logo />
  <div class="main-con box-col">
    <div class="header">
      <span class="title">문제 목록</span>
    </div>
    <div class="problem-con box-col">
      <ProblemSearch @clear-page="clearPage" />
      <ProblemList :current-page="currentPage" />
      <div class="btn-con">
        <button class="btn-prev" @click="prevPage">◀</button>
        <button class="btn-next" @click="nextPage">▶</button>
      </div>
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
  position: relative;
  width: calc(1920px - 250px);
  height: calc(1080px - 180px);
  background-color: #bed0fd;
  border: 10px solid #3b72ff;
  border-radius: 20px;
  padding: 50px 80px;
}

.btn-con {
  position: absolute;
  bottom: 15px;
  margin-top: 20px;
  margin-left: 600px;

}

.btn-prev,
.btn-next {
  color: white;
  font-size: 25px;
  padding: 5px 50px;
  border: 3px solid #7397f5;
  border-radius: 10px;
  background-color: #5484fc;
}

.btn-next {
  margin-left: 25px;
}

.btn-prev:hover,
.btn-next:hover {
  border: 3px solid #3f5fae;
  background-color: #2d55ba;
}
</style>
