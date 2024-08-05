import { ref } from "vue";
import { defineStore } from "pinia";

export const userStore = defineStore("user", () => {
  const user = ref({
    id: "",
    nickname: "",
    tier: "",
    percentage: "",
  });

  function setUser(newUser) {
    user.value = newUser;
  }
  return {
    user,
    setUser,
  };
});
