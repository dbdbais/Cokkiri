<script setup>
import { useChatStore } from "@/stores/chat";
import { ref } from "vue";

defineProps({
  chatList: Array,
});

const emit = defineEmits(["chat"]);
const chatStore = useChatStore();

const user = ref("어지민");
const chatText = ref("");

const chatBox = ref(null);

const chatSubmit = function () {
  emit("chat", chatText.value);
  chatText.value = "";
  chatStore.insertChatBox(chatBox.value);
};
</script>

<template>
  <div class="chat-container box box-col">
    <div class="chat-list chat-box box" ref="chatBox">
      <p
        v-for="chat in chatList"
        :key="chat.index"
        class="chat-item nomal-text"
      >
        {{ chat }}
      </p>
    </div>
    <div class="chat-input">
      <input
        type="text"
        class="nomal-text"
        v-model="chatText"
        @keyup.enter="chatSubmit"
      />
    </div>
  </div>
</template>

<style scoped>
.chat-container {
  width: 840px;
  height: 320px;
  align-items: center;
  margin-left: 20px;
}
.chat-box {
  width: 98%;
  height: 240px;
  margin: 10px;
  overflow-y: auto;
}
.chat-item {
  margin: 0px 20px;
  font-size: 35px;
}
.chat-input {
  width: 98%;
  height: 40px;
}
.chat-input input {
  width: 100%;
  height: 40px;
  border: 2px solid black;
  border-radius: 5px;
  padding-left: 15px;
}
</style>
