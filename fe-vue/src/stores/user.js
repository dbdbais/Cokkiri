import { ref } from "vue";
import { defineStore } from "pinia";

export const userStore = defineStore(
  "user",
  () => {
    const user = ref({
      id: "",
      nickname: "",
      tier: "",
      percent: 
    });

    function setUser(newUser) {
      user.value = newUser;
    }

    function logout() {
      user.value = {
        id: "",
        nickname: "",
        tier: "",
        percent: 0,
      };
    }

    return {
      user,
      setUser,
      logout,
    };
  },
  {
    persist: true,
  }
);
