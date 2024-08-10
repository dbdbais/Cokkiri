<script setup>
import "@/assets/css/meeting.css";
import { userStore } from "@/stores/user";
import {
  changeAudio,
  changeVideo,
  joinSession,
  leaveSession,
  publishScreenShare,
} from "@/api/webRTC";
import Member from "@/components/meeting/Member.vue";
import Main from "@/components/meeting/Main.vue";
import Chat from "@/components/meeting/Chat.vue";
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getProblemList } from "@/api/waitingroom";

const user = userStore();
const problemList = ref([]);

onMounted(() => {
  joinSession(route.params.roomId, user.user.nickname);
});

const route = useRoute();
const router = useRouter();
const audio = ref(true);
const video = ref(true);

const chatOnOff = ref(false);

const audioOnOff = function () {
  audio.value = !audio.value;
  changeAudio(audio.value);
};
const videoOnOff = function () {
  video.value = !video.value;
  changeVideo(video.value);
};

const exitRoom = function () {
  leaveSession();
  router.replace({ name: "home" });
};
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
    <button id="myVideo" @click="videoOnOff">
      video [{{ video ? "ON" : "OFF" }}]
    </button>
    <button id="myAudio" @click="audioOnOff">
      audio [{{ audio ? "ON" : "OFF" }}]
    </button>
    <button id="exit" @click="exitRoom">exit</button>
    <button id="video-share" @click="publishScreenShare">화면 공유</button>
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
#exit,
#video-share,
.chat-btn {
  position: absolute;
  top: 0;
}
#video-share {
  right: 350px;
}
#myAudio {
  right: 180px;
}
#exit {
  right: 300px;
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
  right: 20px;
  cursor: move;
}

video {
  border-radius: 20%;
}
</style>
