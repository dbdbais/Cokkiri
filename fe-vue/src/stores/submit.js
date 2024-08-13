import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useSubmitStore = defineStore("submit", () => {
  const roomUser = ref({});
  const codeData = ref("");
  const showDetail = ref(false);
  const submitCodeList = ref([]);

  const close = () => {
    showDetail.value = false;
  };

  const setCodeData = (data) => {
    console.log("보여라!");
    codeData.value = data;
    showDetail.value = true;
  };

  const submit = (submitData) => {
    submitCodeList.value.push(submitData);
  };

  return {
    roomUser,
    codeData,
    showDetail,
    submitCodeList,
    setCodeData,
    submit,
    close,
  };
});
