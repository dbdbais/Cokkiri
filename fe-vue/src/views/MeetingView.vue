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
import { ref, onMounted, onUnmounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getProblemList, getWaitingRoom } from "@/api/waitingroom";
import HintView from "@/components/meeting/modal/HintView.vue";
import { useModal } from "@/composables/useModal";

const user = userStore();
const problemList = ref([]);
const { isModalOpen, openModal, closeModal } = useModal();

onMounted(async () => {
  try {
    joinSession(route.params.roomId, user.user.nickname);
    const response = await getWaitingRoom();
    console.log("=====MeetingView Page=====")
    console.log("roomInfo")
    console.log(response);
  } catch (e) {
    console.error(e);
  }
  window.addEventListener("beforeunload", leaveSession);
  window.addEventListener("popstate", leaveSession);
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
    <!-- <button
      class="chat-btn set-btn"
      @click="
        () => {
          chatOnOff = true;
        }
      "
    >
      chat
    </button> -->
    <button class="set-btn hint" @click="openModal">
      <img v-if="video" src="/src/assets/meeting/video-on.svg" alt="비디오 on" />
      <img v-else src="/src/assets/meeting/video-off.svg" alt="비디오 off" />
    </button>
    <button id="myVideo" class="set-btn" @click="videoOnOff">
      <img v-if="video" src="/src/assets/meeting/video-on.svg" alt="비디오 on" />
      <img v-else src="/src/assets/meeting/video-off.svg" alt="비디오 off" />
    </button>
    <button id="myAudio" class="set-btn" @click="audioOnOff">
      <img v-if="audio" src="/src/assets/meeting/audio-on.svg" alt="마이크 on" />
      <img v-else src="/src/assets/meeting/audio-off.svg" alt="마이크 off" />
    </button>
    <div id="exit" class="room-exit bold-text md" @click="exitRoom">
      <img src="/src/assets/exit_room.svg" alt="방나가기" />
      나가기
    </div>
    <button id="video-share" class="set-btn" @click="publishScreenShare">
      <img src="/src/assets/meeting/share.svg" alt="화면공유" />
    </button>
    <div class="main box-main-con">
      <Main />
    </div>
  </div>
  <HintView v-if="isModalOpen" @close="closeModal" />
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
}

#video-share {
  top: 90px;
  right: 190px;
}

#myAudio {
  top: 90px;
  right: 20px;
}

#myVideo {
  top: 90px;
  right: 105px;
}

#exit {
  top: 20px;
  right: 20px;
}

.chat-btn {
  right: 200px;
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

.set-btn {
  width: 70px;
  height: 70px;
  border-radius: 10px;
  border-width: 5px;
  border-color: #5bb5d9;
  background-color: #a8dcf0;
}

.room-exit {
  width: 120px;
  height: 50px;
  background-color: #ff6b6b;
  border: 3px solid white;
  border-radius: 10px;
  padding-right: 7px;
  font-size: 22px;
}

.hint {
  top: 90px;
  right: 280px;
}
</style>
