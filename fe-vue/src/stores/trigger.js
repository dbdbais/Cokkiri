import { ref } from "vue";
import { defineStore } from "pinia";

export const useTriggerStore = defineStore("trigger", () => {
  const trigger = ref(false);

  function isTrigger() {
    console.log("친구목록 세팅 완료(5)");
    trigger.value = !trigger.value;
  }

  return { trigger, isTrigger };
});
