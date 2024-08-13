import { ref } from "vue";
import { defineStore } from "pinia";

export const useItemStore = defineStore("item", () => {
  const currentFontSize = ref(16);

  function setFontSize(size) {
    currentFontSize.value = size;
  }

  return { currentFontSize, setFontSize };
});
