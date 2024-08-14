import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useCorrectStore = defineStore(
  "correct",
  () => {
    const correctCnt = ref({
      1: false,
      2: false,
    });

    const userRankList = ref([]);
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

    function userCorrect(data) {
      console.log(data);
      const userRank = data[0];
      const username = data[1];
      const userTime = data[2];
      userRankList.value.push({
        userRank,
        username,
        userTime,
      });
    }

    function resetCorrect() {
      correctCnt.value = {
        1: false,
        2: false,
      };
      userRankList.value = [];
      isCorrect.value = false;
    }

    return { userRankList, correct, correctCheck, resetCorrect, userCorrect };
  },
  { persist: true }
);
