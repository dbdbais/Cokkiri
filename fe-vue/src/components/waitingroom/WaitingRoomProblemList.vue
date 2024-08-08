<script setup>
import { onMounted, ref } from "vue";
import { getAllProblems } from "@/api/problem";

const problemList = ref([]);
const selectProblem = ref([]);

onMounted(() => {
  getAllProblems()
    .then((res) => {
      console.log(res.data);
      problemList.value = res.data;
    })
    .catch((err) => {
      console.log(err);
    });
});
</script>

<template>
  <div class="problem-list box">
    <div class="problem-list-box box">
      <div
        class="problem-item box bold-text"
        v-for="problem in problemList"
        :key="problem.no"
      >
        {{ problem.no }} : {{ problem.title }} - {{ problem.level }}
        <button @click="selectProblem.push(problem.no)">선택</button>
      </div>
    </div>
    <button @click="$emit('problem-select', selectProblem)">선택 완료</button>
  </div>
</template>

<style scoped>
.problem-list {
  width: 500px;
  height: 500px;
  position: absolute;
  left: 500px;
  right: 500px;
  z-index: 3;
}
.problem-list-box {
  width: 400px;
  height: 400px;
}
</style>
