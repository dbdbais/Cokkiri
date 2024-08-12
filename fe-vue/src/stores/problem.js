import { ref } from "vue";
import { defineStore } from "pinia";

export const problemStore = defineStore(
  "problem",
  () => {
    const problems = ref([]);
    const selectedProblemList = ref([]);
    const currentProblemNum = ref(1);
    const beforeProblemNum = ref(0);
    const userCodeList = ref({});

    function changeProblem(num) {
      beforeProblemNum.value = currentProblemNum.value;
      currentProblemNum.value = num;
      console.log(
        "현재:",
        currentProblemNum.value,
        "이전",
        beforeProblemNum.value
      );
    }

    function setProblems(newProblems) {
      problems.value = newProblems;
      console.log(problems.value);
    }

    function select(problemList) {
      selectedProblemList.value = [];
      let num = 1;
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
      beforeProblemNum,
      currentProblemNum,
      userCodeList,
      setProblems,
      select,
      changeProblem,
      getProblem,
    };
  },
  {
    // persist: true,
  }
);
