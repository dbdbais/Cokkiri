import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useSubmitStore = defineStore("submit", () => {
  const roomUser = ref({});
  const codeData = ref("");
  const showDetail = ref(false);
  const setRoomUser = (roomData) => {
    roomData.users.forEach((element) => {
      console.log(element);
      roomUser.value[element] = 1;
    });
  };

  const close = () => {
    showDetail.value = false;
  };

  const setCodeData = (data) => {
    console.log("보여라!");
    codeData.value = data;
    showDetail.value = true;
  };

  const upSubmit = (user) => {
    console.log(roomUser.value);
    roomUser.value[user] += 1;
    console.log(roomUser.value);
  };

  return {
    roomUser,
    codeData,
    showDetail,
    setRoomUser,
    upSubmit,
    setCodeData,
    close,
  };
});
