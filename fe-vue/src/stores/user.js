import { ref } from "vue";
import { defineStore } from "pinia";

export const userStore = defineStore(
  "user",
  () => {
    const user = ref({});
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
      user.value = {};
      userNickname.value = {};
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
