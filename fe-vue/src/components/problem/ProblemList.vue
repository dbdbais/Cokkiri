<script setup>
import ProblemItem from "@/components/problem/ProblemItem.vue";
import { ref } from "vue";

const problemList = ref([
  {
    id: 1,
    no: 21,
    problemName: "단어 뒤집기",
    answerPercentage: "24%",
    difficulty: { tier: "gold", level: 4 },
    review: { try: 2, result: 1 },
  },
  {
    id: 2,
    no: 23,
    problemName: "종덕 뒤집기",
    answerPercentage: "33%",
    difficulty: { tier: "gold", level: 4 },
    review: { try: 3, result: 0 },
  },
  {
    id: 3,
    no: 27,
    problemName: "뒤집기",
    answerPercentage: "64%",
    difficulty: { tier: "silver", level: 4 },
    review: { try: 1, result: 0 },
  },
]);
</script>

<template>
  <div>
    <table>
      <thead class="bold-text">
        <tr>
          <th>No</th>
          <th>문제</th>
          <th>난이도</th>
          <th>정답률</th>
          <th>리뷰 보기</th>
        </tr>
      </thead>
      <tbody>
        <ProblemItem
          v-for="problem in problemList"
          :key="problem.id"
          :problem-data="problem"
        />
      </tbody>
    </table>
  </div>
</template>

<style scoped>
body {
  padding: 1.5em;
  background: #f5f5f5;
}

table {
  border: 1px #a39485 solid;
  font-size: 0.9em;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.25);
  width: 100%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;

  margin-top: 20px;
}
th {
  padding: 1em 0.5em;
  vertical-align: middle;
  text-align: center;
  border-right: 3px solid white;
}

thead {
  font-size: 25px;
  color: #fff;
  background: #5b89ff;
}

a {
  color: #73685d;
}
</style>
