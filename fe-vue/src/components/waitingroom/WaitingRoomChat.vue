<script setup>
import { ref } from "vue";

const user = ref("어지민");
const chatText = ref("");
const chatList = ref([
  "어지민: 하하하하ㅏ하하하ㅏ하하",
  "김종덕: ㅇㄴㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ",
]);

const chatSubmit = function () {
  chatList.value.push(user.value + ": " + chatText.value);
  chatText.value = "";
};
</script>

<template>
  <div class="chat-container box box-col">
    <div class="chat-list chat-box box">
      <p
        v-for="chat in chatList"
        :key="chat.index"
        class="chat-item nomal-text"
      >
        {{ chat }}
      </p>
    </div>
    <form action="" class="chat-input" @submit.prevent="chatSubmit">
      <input type="text" class="nomal-text" v-model="chatText" />
    </form>
  </div>
</template>

<style scoped>
.chat-container {
  width: 1400px;
  height: 320px;
  align-items: center;
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
