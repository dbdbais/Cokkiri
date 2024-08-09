<script setup>
import { problemStore } from "@/stores/problem";
import { onMounted, ref } from "vue";

defineProps({
  blind: Number,
});

const useProblemStore = problemStore();
const problemList = useProblemStore.selectedProblemList;
const currentProblem = ref(1);
const getProblem = (no) => {
  return useProblemStore.getProblem(no);
};
const showProblem = (no) => {
  currentProblem.value = no;
};
</script>

<template>
  <div class="md">
    <div class="problem-content box-w">
      <div class="flex-align problem-category">
        <div
          class="bold-text box-sb md problem"
          v-for="problem in problemList"
          :key="problem.num"
          :class="{ active: currentProblem === problem.num }"
          @click="showProblem(problem.num)"
        >
          문제 {{ problem.num }}
        </div>
      </div>
      <div class="blind" :style="{ height: blind + 'px' }"></div>
      <div class="nomal-text problem-box">
        <!-- <div>
          {{ getProblem(currentProblem - 1) }}
        </div> -->
        <div v-html="getProblem(currentProblem - 1).info"></div>
      </div>
    </div>
  </div>
</template>

<style>
.problem-content {
  width: 95%;
  height: 700px;
  position: relative;
  border-radius: 0px 10px 10px 10px;
  margin-top: 70px;
}
.problem-box {
  width: 100%;
  height: 680px;
  padding: 0px 35px;
  font-size: 20px;
  overflow-y: auto;
}
.blind {
  width: 99%;

  background-color: black;
  border-radius: 5px;
  position: absolute;
  top: 0px;
  left: 0;
  z-index: 1;
  transition: 0.8s ease-in-out;
  opacity: 0.9;
}
.problem {
  width: 100px;
  height: 50px;

  border-radius: 10px 10px 0px 0px;
  font-size: 25px;
}

.nomal-text {
  font-size: 30px;
}
.active {
  background-color: blue;
}
.sampledata {
  background-color: #dbe7ff;
  border: 5px solid #3b72ff;
  overflow-y: scroll;
  border-radius: 10px;
  height: 300px;
  padding: 10px;
  white-space: pre-wrap;
  font-family: "yg-jalnan";
}
.problem-box li {
  margin-left: 20px;
}
.problem-box table {
  width: 100%;
  display: flex;
  justify-content: space-around;
  margin: 30px 0;
}
.problem-box table img {
  margin: 0 20px;
}

.problem-box tr {
  display: flex;
  justify-content: space-around;
  align-items: center;
  border: 1px solid black;
}

.problem-box td {
  width: 100%;
  text-align: center;
  font-size: 20px;
  border: 1px solid black;
}
.problem-category {
  position: absolute;
  top: -50px;
  left: -5px;
}
.active {
  background-color: blue;
}
</style>
