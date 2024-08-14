import { ref } from "vue";
import { defineStore } from "pinia";

export const useGameStore = defineStore("game", () => {
  const bigFont = ref(false);
  const smallFont = ref(false);

  const bigFontFun = () => {
    bigFont.value = true;
    setTimeout(() => {
      bigFont.value = false;
    }, 1200);
  };

  const smallFontFun = () => {
    smallFont.value = true;
    setTimeout(() => {
      smallFont.value = false;
    }, 1200);
  };

  return { bigFont, smallFont, bigFontFun, smallFontFun };
});
