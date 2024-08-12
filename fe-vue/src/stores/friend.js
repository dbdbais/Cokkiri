import { ref } from "vue";
import { defineStore } from "pinia";

export const friendStore = defineStore("friend", () => {
  const allRelationships = ref([]);

  function setAllRelationships(newFriends) {
    allRelationships.value = newFriends;
  }

  return {
    allRelationships,
    setAllRelationships,
  };
});
