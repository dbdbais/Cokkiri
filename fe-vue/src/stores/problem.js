import { ref } from "vue";
import { defineStore } from "pinia";

export const problemStore = defineStore(
  "problem",
  () => {
    const problems = ref([]);
    const selectedProblemList = ref([]);
    const selectedProblem = ref();

    function setProblems(newProblems) {
      problems.value = newProblems;
      console.log(problems.value);
    }

    function setSelectedProblem(problem) {
      selectedProblem.value = problem;
    }

    function select(problemList) {
      selectedProblemList.value = [];
      let num = 1;
      problemList.forEach((element) => {
        element["num"] = num;
        selectedProblemList.value.push(element);
        num += 1;
      });
      console.log(selectedProblemList.value);
    }

    const getProblem = (idx) => {
      if (selectedProblemList.value.length === 0) {
        return { info: "" };
      }
      return selectedProblemList.value[idx];
    };

    return {
      problems,
      selectedProblemList,
      selectedProblem,
      setProblems,
      setSelectedProblem,
      select,
      getProblem,
    };
  },
  {
    persist: true,
  }
);
