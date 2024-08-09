import { ref } from "vue";
import { defineStore } from "pinia";

export const useChatStore = defineStore("chat", () => {
  const chatBox = ref("");
  const chatList = ref([]);

  const insertChatBox = (box) => {
    chatBox.value = box;
  };

  const sendChat = (message) => {
    chatList.value.push(message);
    setTimeout(autoScroll, 1);
  };

  const autoScroll = () => {
    chatBox.value.scrollTop = chatBox.value.scrollHeight;
  };

  return { chatBox, chatList, sendChat, insertChatBox };
});
