<script setup>
import ProblemItem from "@/components/problem/ProblemItem.vue";
import { ref, onMounted } from "vue";
import problem from "@/api/problem";


const problemList = ref([]);

function extractProblems(text) {
  const problemRegex = /Problem\{.*?\}/gs;
  const problems = [];
  let match;

  while ((match = problemRegex.exec(text)) !== null) {
    console.log(match)
    const problemBlock = match[0];

    const noMatch = /no=(.*?),/.exec(problemBlock);
    const algoTypeMatch = /algoType=\[(.*?)\]/.exec(problemBlock);
    const titleMatch = /title='(.*?)'/.exec(problemBlock);
    const levelMatch = /level=(.*?),/.exec(problemBlock);
    const infoMatch = /info='(.*?)'/.exec(problemBlock);
    const algoPercentMatch = /algoPercent=(.*?),/.exec(problemBlock);
    const algoInputMatch = /algoInput=\{(.*?)\}/.exec(problemBlock);
    const algoOutputMatch = /algoOutput=\{(.*?)\}/.exec(problemBlock);

    // console.log(noMatch)
    // console.log(algoTypeMatch)
    // console.log(titleMatch)
    // console.log(levelMatch)
    console.log(infoMatch);
    // console.log(algoPercentMatch)
    // console.log(algoInputMatch)
    // console.log(algoOutputMatch)

    // 추출한 값들을 객체에 저장
    const problem = {
      no: noMatch ? noMatch[1].trim() : null,
      algoType: algoTypeMatch ? algoTypeMatch[1].trim() : null,
      title: titleMatch ? titleMatch[1].trim() : null,
      level: levelMatch ? levelMatch[1].trim() : null,
      info: infoMatch ? infoMatch[1].trim() : null,
      algoPercent: algoPercentMatch ? algoPercentMatch[1].trim() : null,
      algoInput: algoInputMatch ? algoInputMatch[1].trim() : null,
      algoOutput: algoOutputMatch ? algoOutputMatch[1].trim() : null
    };

    // 문제 객체를 문제 배열에 추가
    problems.push(problem);
  }

  return problems;
}


function test(text) {
  const problems = [];
  const pattern = /Problem\{(.*?)\}/gs;
  const matches = [...text.matchAll(pattern)];

  matches.map(match => {
    const noMatch = /no=(.*?),/.exec(match);
    const algoTypeMatch = /algoType=\[(.*?)\]/.exec(match);
    const titleMatch = /title='(.*?)'/.exec(match);
    const levelMatch = /level=(.*?),/.exec(match);
    const infoMatch = /info='(.*?)'/.exec(match);
    const algoPercentMatch = /algoPercent=(.*?),/.exec(match);
    const algoInputMatch = /algoInput=\{(.*?)\}/.exec(match);
    const algoOutputMatch = /algoOutput=\{(.*?)\}/.exec(match);

    const problem = {
      no: noMatch ? noMatch[1].trim() : null,
      algoType: algoTypeMatch ? algoTypeMatch[1].trim() : null,
      title: titleMatch ? titleMatch[1].trim() : null,
      level: levelMatch ? levelMatch[1].trim() : null,
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
  problemList.value = response.data;
  return response;
}

onMounted(() => {
  getProblems().then((response) => {
    // console.log(response);
    // console.log(extractProblems(response.data));
    console.log(test(response.data));
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
