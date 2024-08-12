import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useTriggerStore = defineStore("trigger", () => {
  const currentProblemNum = ref(1);
  const beforeProblemNum = ref(1);

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
  return { currentProblemNum, beforeProblemNum, changeProblem };
});
