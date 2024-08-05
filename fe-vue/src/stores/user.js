import { ref } from "vue";
import { defineStore } from "pinia";

export const userStore = defineStore("user", () => {
  const tUser = ref({
    id: "ssafy",
    nickname: "ssafy",
    tier: "SEED",
    percent: 0,
  });

  const user = ref({
    id: "",
    nickname: "",
    tier: "",
    percent: 0,
  });

  function setUser(newUser) {
    user.value = newUser;
  }
  return {
    tUser,
    user,
    setUser,
  };
});
