import { ref } from "vue";
import { defineStore } from "pinia";

export const useLodingStore = defineStore("loading", () => {
  const isLoading = ref(false);
  const loading = function () {
    isLoading.value = true;
  };
  const loadingSuccess = function () {
    isLoading.value = false;
  };

  return { isLoading, loading, loadingSuccess };
});
