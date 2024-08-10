import { ref } from "vue";
import { defineStore } from "pinia";

export const useTriggerStore = defineStore("trigger", () => {
  const trigger = ref(false);

  function isTrigger() {
    trigger.value = !trigger.value;
  }

  return { trigger, isTrigger };
});
