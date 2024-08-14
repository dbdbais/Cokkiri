import { ref } from "vue";

export function checkViewport() {
  const currentWidth = ref(window.innerWidth);
  const currentHeight = ref(window.innerHeight);

  const check = () => {
    if (currentWidth.value > 1920 + 120 || currentHeight.value > 1080 + 120) {
      return {
        isValid: false,
        msg: "확대해주세요.",
      };
    } else if (
      currentWidth.value < 1920 - 120 ||
      currentHeight.value < 1080 - 120
    ) {
      return {
        isValid: false,
        msg: "축소해주세요",
      };
    } else {
      return { isValid: true, msg: "" };
    }
  };

  const update = () => {
    currentWidth.value = window.innerWidth;
    currentHeight.value = window.innerHeight;
    checkViewport();
  };

  return {
    currentWidth,
    currentHeight,
    check,
    update,
  };
}
