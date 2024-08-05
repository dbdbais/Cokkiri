import { ref } from "vue";
import { defineStore } from "pinia";

export const userStore = defineStore("user", () => {
  const user = ref({ id: "jimin", name: "어지민" });
  return {
    user,
  };
});
