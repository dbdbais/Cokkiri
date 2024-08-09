import { ref } from "vue";
import { defineStore } from "pinia";

export const useChatStore = defineStore("chat", () => {
  const chatBox = ref("");

  const insertChatBox = (box) => {
    chatBox.value = box;
  };

  const autoScroll = () => {
    chatBox.value.scrollTop = chatBox.value.scrollHeight + 57;
  };

  return { chatBox, insertChatBox, autoScroll };
});
