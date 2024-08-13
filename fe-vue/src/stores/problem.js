import { ref } from "vue";
import { defineStore } from "pinia";

export const problemStore = defineStore(
  "problem",
  () => {
    const problems = ref([]);
    const selectedProblemList = ref([]);
    const selectedProblem = ref();
    const userCodeList = ref({});

    function setProblems(newProblems) {
      problems.value = newProblems;
      console.log(problems.value);
    }

    function setSelectedProblem(problem) {
      selectedProblem.value = problem;
    }

    function select(problemList) {
      selectedProblemList.value = [];
      userCodeList.value = {};
      let num = 1;
      console.log(problemList.length);
      if (problemList.length === 0) {
        userCodeList.value[num] = {
          no: "문제 없음",
          code: 'print("Hello, World!")',
          language: "python",
          ipt: "",
        };
      }
      problemList.forEach((element) => {
        element["num"] = num;
        selectedProblemList.value.push(element);
        userCodeList.value[element.num] = {
          no: element.no,
          code: 'print("Hello, World!")',
          language: "python",
          ipt: "",
        };
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
      userCodeList,
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
