import { ref } from "vue";
import { defineStore } from "pinia";

export const problemStore = defineStore("problem", () => {
  const problems = ref([]);

  function setProblems(newProblems) {
    var tempArr = [];
    tempArr.push(newProblems);
    problems.value = tempArr;
  }

  return {
    problems,
    setProblems,
  };
});
