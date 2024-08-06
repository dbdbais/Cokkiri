import { ref } from "vue";
import { defineStore } from "pinia";

export const userStore = defineStore("user", () => {
  const user = ref({
    id: "ssafy",
    nickname: "ssafy",
    tier: "SEED",
    percent: 0,
  });

  function setUser(newUser) {
    user.value = newUser;
  }
  return {
    user,
    setUser,
  };
});
