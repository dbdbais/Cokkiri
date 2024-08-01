<script setup>
import ProblemItem from "@/components/problem/ProblemItem.vue";
import { ref, onMounted } from "vue";
import problem from "@/api/problem";


const problemList = ref([]);

function extractProblems(text) {
  const problems = [];
  const pattern = /(.*?)\}\}/gs;
  const matches = [...text.matchAll(pattern)];

  matches.map(match => {
    const noMatch = /no=(.*?),/s.exec(match[0]);
    const algoTypeMatch = /algoType=\[(.*?)\],/s.exec(match[0]);
    const titleMatch = /title='(.*?)',/s.exec(match[0]);
    const levelMatch = /level=(.*?),/s.exec(match[0]);
    const infoMatch = /info='(.*?)',/s.exec(match[0]);
    const algoPercentMatch = /algoPercent=(.*?),/s.exec(match[0]);
    const algoInputMatch = /algoInput=\{(.*?)\},/s.exec(match[0]);
    const algoOutputMatch = /algoOutput=\{(.*?)\}\}/s.exec(match[0]);

    const problem = {
      no: noMatch ? noMatch[1].trim() : null,
      algoType: algoTypeMatch ? algoTypeMatch[1].trim() : null,
      title: titleMatch ? titleMatch[1].trim() : null,
      level: levelMatch ? parseInt(levelMatch[1].trim()) : null,
      info: infoMatch ? infoMatch[1].trim() : null,
      algoPercent: algoPercentMatch ? algoPercentMatch[1].trim() : null,
      algoInput: algoInputMatch ? algoInputMatch[1].trim() : null,
      algoOutput: algoOutputMatch ? algoOutputMatch[1].trim() : null
    };

    problems.push(problem);
  });
  return problems;
}

async function getProblems(id) {
  const response = await problem.getProblems(id);
  problemList.value = extractProblems(response.data);
  return response;
}

onMounted(() => {
  getProblems().then((response) => {
    console.log(response);
  }).catch((error) => {
    console.log(error);
  });
});
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
        <ProblemItem v-for="problem in problemList" :key="problem.id" :problem-data="problem" />
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
