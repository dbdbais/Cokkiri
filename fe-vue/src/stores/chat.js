import { ref } from "vue";
import { defineStore } from "pinia";

export const useChatStore = defineStore("chat", () => {
  const chatBox = ref("");
  const chatList = ref([]);

  const insertChatBox = (box) => {
    chatBox.value = box;
  };

  const resetChatBox = () => {
    chatList.value = [];
  };

  const sendChat = (message) => {
    chatList.value.push(message);
    setTimeout(autoScroll, 1);
  };
  const resetChatList = () => {
    chatList.value = [];
  };

  const autoScroll = () => {
    chatBox.value.scrollTop = chatBox.value.scrollHeight;
  };

  return { chatBox, chatList, sendChat, insertChatBox, resetChatList };
});
