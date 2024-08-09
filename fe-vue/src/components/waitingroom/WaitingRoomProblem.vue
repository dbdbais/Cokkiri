<script setup>
import Problem from "../meeting/Problem.vue";

defineProps({
  problemList: Array,
});
</script>

<template>
  <div class="box problem-container bold-text">
    문제 목록
    <button class="problem-btn bold-text" @click="$emit('open')">선택</button>
    <div class="problem-box box box-col text-font">
      <div
        class="problem-item box bold-text flex-align"
        v-for="problem in problemList"
        :key="problem.no"
      >
        <div class="badge md">LEVEL {{ problem.level }}</div>
        <span class="no">NO.{{ String(problem.no).padStart(5, "0") }}</span
        ><span class="dash nomal-text">-</span>
        <span class="name">{{ problem.title }}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.problem-container {
  width: 660px;
  height: 320px;
  padding: 10px 20px;
  position: relative;
}
.problem-box {
  width: 610px;
  height: 230px;
  margin-top: 10px;
  padding: 10px 10px 0px 10px;
  align-items: center;
}
.problem-btn {
  width: 100px;
  height: 40px;
  position: absolute;
  right: 20px;

  border-radius: 5px;
  border-width: 4px;
  border-radius: 5px;
  border-color: #3b72ff;
  background-color: #c7d5f7;
}
.problem-item {
  width: 100%;
  height: 58px;
  position: relative;
  padding: 10px;
  margin-bottom: 10px;
}
.no,
.dash,
.name {
  position: absolute;
}
.no {
  left: 120px;
}
.dash {
  left: 230px;
}
.name {
  left: 260px;
}
.badge {
  width: 100px;
  height: 30px;
  background-color: #ff8e8e;
  border-radius: 5px;
  margin-right: 30px;
}
</style>
