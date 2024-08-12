import { ref } from "vue";
import { defineStore } from "pinia";

export const problemStore = defineStore("problem", () => {
  const problems = ref([]);

  function setProblems(newProblems) {
    problems.value = newProblems;
  }

  return {
    problems,
    setProblems,
  };
});
