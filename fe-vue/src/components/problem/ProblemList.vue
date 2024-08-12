<script setup>
import ProblemItem from "@/components/problem/ProblemItem.vue";
import { onMounted, ref, computed } from "vue";
import { getAllProblems } from "@/api/problem";
import { problemStore } from "@/stores/problem";

const props = defineProps({
  currentPage: Number,
});
const emit = defineEmits(["emitTotalPage"]);

const store = problemStore();
const currentPageProblems = computed(() => {
  const start = (props.currentPage - 1) * 8;
  const end = props.currentPage * 8;
  return store.problems.slice(start, end);
});

onMounted(() => {
  getAllProblems().then((response) => {
    console.log(response);
    store.setProblems(response.data);
    emit("emitTotalPage", Math.ceil(store.problems.length / 8));
  }).catch((error) => {
    console.log(error);
  });
});
</script>

<template>
  <div class="table-con">
    <table>
      <thead class="bold-text">
        <tr>
          <th>No</th>
          <th>문제</th>
          <th>난이도</th>
          <th>정답률</th>
          <th>문제상세 및 리뷰</th>
        </tr>
      </thead>
      <tbody>
        <ProblemItem v-for="problem in currentPageProblems" :key="problem.no" :problem-data="problem" />
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.table-con {
  height: 652px;
}

table {
  border: 1px #a39485 solid;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.25);
  width: 100%;
  border-collapse: collapse;
  /* border-radius: 10px; */
  overflow: hidden;
  margin-top: 20px;
}

th {
  font-size: 32px;
  padding: 32px 16px;
  /* vertical-align: middle; */
  /* text-align: center; */
  border: 3px solid #215cf4;
  /* border-right: 3px solid white; */
}

thead {
  font-size: 25px;
  color: #fff;
  background: #5b89ff;
}

a {
  color: #73685d;
}

th:nth-child(1) {
  width: 150px;
}

th:nth-child(2) {
  width: 380px;
}

th:nth-child(3) {
  width: 100px;
}

th:nth-child(4) {
  width: 250px;
}

th:nth-child(5) {
  width: 250px;
}
</style>
