import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useCorrectStore = defineStore(
  "correct",
  () => {
    const correctCnt = ref({
      1: false,
      2: false,
    });

    const isCorrect = ref(false);

    function correct(no) {
      correctCnt.value[no] = true;
      console.log(`${no}번 문제 맞힘`);
      console.log(correctCnt.value);
    }

    function correctCheck() {
      if (isCorrect.value) {
        return;
      }
      let cnt = 0;
      Object.keys(correctCnt.value).forEach((key) => {
        if (correctCnt.value[key]) {
          cnt += 1;
        }
      });

      if (cnt === 2) {
        isCorrect.value = true;
        return true;
      } else {
        return false;
      }
    }

    function resetCorrect() {
      correctCnt.value = {
        1: false,
        2: false,
      };
      isCorrect.value = false;
    }

    return { correct, correctCheck, resetCorrect };
  },
  { persist: true }
);
