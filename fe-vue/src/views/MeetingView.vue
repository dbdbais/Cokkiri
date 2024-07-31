<script setup>
import "@/assets/css/meeting.css";
import { changeAudio, changeVideo, joinSession } from "@/api/webRTC";
import Member from "@/components/meeting/Member.vue";
import Main from "@/components/meeting/Main.vue";
import Chat from "@/components/meeting/Chat.vue";
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";

onMounted(() => {
  joinSession(route.params.roomId);
});

const route = useRoute();

const members = ref([
  { num: "member_1" },
  { num: "member_2" },
  { num: "member_3" },
  { num: "member_4" },
  { num: "member_5" },
  { num: "member_6" },
]);

const chatOnOff = ref(false);
</script>
<template>
  <div class="meeting-room">
    <Chat v-if="chatOnOff" />
    <div class="members box-main-con flex-align" id="members"></div>
    <button
      class="chat-btn"
      @click="
        () => {
          chatOnOff = true;
        }
      "
    >
      chat
    </button>
    <button id="myVideo" data-flag="true" @click="changeVideo">video</button>
    <button id="myAudio" data-flag="true" @click="changeAudio">audio</button>
    <div class="main box-main-con">
      <Main />
    </div>
  </div>
</template>

<style scoped>
.meeting-room {
  position: relative;
  overflow: hidden;
}

#myAudio,
#myVideo,
.chat-btn {
  position: absolute;
  top: 0;
}
#myAudio {
  right: 40px;
}
.chat-btn {
  right: 80px;
}
.members {
  width: 1500px;
  height: 180px;

  margin: 20px;
  justify-content: space-around;
}
.main {
  width: 1880px;
  height: 840px;
  margin: 20px;
}

button {
  height: 100px;

  position: absolute;
  right: 0px;
  cursor: move;
}
video {
  border-radius: 20%;
}
</style>
