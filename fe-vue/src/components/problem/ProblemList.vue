<script setup>
import ProblemItem from "@/components/problem/ProblemItem.vue";
import { onMounted, ref, watch, computed } from "vue";
import { getAllProblems, getProblems } from "@/api/problem";
import { problemStore } from "@/stores/problem";

const props = defineProps({
  currentPage: Number,
});
const emit = defineEmits(["emitTotalPage"]);

const store = problemStore();
const totalPage = ref(1);
const currentPageProblems = computed(() => {
  const start = (props.currentPage - 1) * 5;
  const end = props.currentPage * 5;
  return store.problems.slice(start, end);
});

const test = function () {
  console.log(currentPageProblems.value);
};

// watch(() => props.currentPage, (newPage) => {
//   currentPageProblems.value = store.problems.slice((newPage - 1) * 5, newPage * 5);
// });

onMounted(() => {
  getAllProblems().then((response) => {
    console.log(response);
    store.setProblems(response.data);
    emit("emitTotalPage", Math.ceil(store.problems.length / 5));
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
        <ProblemItem v-for="problem in currentPageProblems" :key="problem.no" :problem-data="problem" />
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
  border-radius: 10px;
  overflow: hidden;
  margin-top: 20px;
}

th {
  font-size: 3vh;
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
