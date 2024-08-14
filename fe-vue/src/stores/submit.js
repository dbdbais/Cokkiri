import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useSubmitStore = defineStore(
  "submit",
  () => {
    const roomUser = ref({});
    const codeData = ref("");
    const submitCodeList = ref([]);
    const showDetail = ref(false);
    const setRoomUser = (roomData) => {
      roomData.users.forEach((element) => {
        console.log(element);
        roomUser.value[element] = 1;
      });
    };

    const submit = (data) => {
      submitCodeList.value.push(data);
    };
    const close = () => {
      showDetail.value = false;
    };

    const resetSubmitList = () => {
      submitCodeList.value = [];
    };
    const setCodeData = (data) => {
      console.log("보여라!");
      codeData.value = data;
      showDetail.value = true;
    };

    return {
      roomUser,
      codeData,
      showDetail,
      submitCodeList,
      submit,
      resetSubmitList,
      setRoomUser,
      setCodeData,
      close,
    };
  },
  { persist: true }
);
