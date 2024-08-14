import { ref } from "vue";
import { defineStore } from "pinia";

export const userStore = defineStore(
  "user",
  () => {
    const user = ref({
      id: "",
      nickname: "",
      tier: "",
      percent: 0,
    });

    const userNickname = ref({});

    function setUser(newUser) {
      user.value = newUser;
    }

    async function setUserNickName(allUser) {
      userNickname.value = {};
      await allUser.forEach((user) => {
        userNickname.value[user.id] = user.nickname;
      });
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
      userNickname,
      setUser,
      logout,
      setUserNickName,
    };
  },
  {
    persist: true,
  }
);
