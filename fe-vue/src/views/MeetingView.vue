<script setup>
import "@/assets/css/meeting.css";
import { userStore } from "@/stores/user";
import {
  changeAudio,
  changeVideo,
  joinSession,
  leaveSession,
  removeUserRequest,
  publishScreenShare,
} from "@/api/webRTC";

import Member from "@/components/meeting/Member.vue";
import Main from "@/components/meeting/Main.vue";
import Chat from "@/components/meeting/Chat.vue";
import ShareCodeDetail from "@/components/meeting/modal/ShareCodeDetail.vue";
import { ref, onMounted, onUnmounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getProblemList, getWaitingRoom } from "@/api/waitingroom";
import HintView from "@/components/meeting/modal/HintView.vue";
import { useModal } from "@/composables/useModal";
import { useSubmitStore } from "@/stores/submit";

const user = userStore();
const problemList = ref([]);
const roomData = ref([]);
const shareCode = ref(true);
const roomUser = ref({});

const { isModalOpen, openModal, closeModal } = useModal();

onMounted(async () => {
  try {
    joinSession(route.params.roomId, user.user.nickname);
    getWaitingRoom(route.params.roomId, (res) => {
      roomData.value = res.data;
      submitStore.setRoomUser(res.data);

      console.log(roomData.value);
    });
  } catch (e) {
    console.error(e);
  }
  window.addEventListener("beforeunload", leaveSession);
  window.addEventListener("popstate", leaveSession);
});

const route = useRoute();
const router = useRouter();
const submitStore = useSubmitStore();
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
  router.replace({ name: "home" });
};

const member = ref(null);
console.log(member.value);

onUnmounted(() => {
  console.log("미팅 종료");
  removeUserRequest();
  leaveSession();
});
</script>
<template>
  <div class="meeting-room">
    <Chat v-if="chatOnOff" />
    <ShareCodeDetail v-if="submitStore.showDetail" />
    <div
      ref="member"
      class="members box-main-con flex-align"
      id="members"
    ></div>

    <button class="set-btn hint" @click="openModal">
      <img
        v-if="video"
        src="/src/assets/meeting/video-on.svg"
        alt="비디오 on"
      />
      <img v-else src="/src/assets/meeting/video-off.svg" alt="비디오 off" />
    </button>
    <button id="myVideo" class="set-btn" @click="videoOnOff">
      <img
        v-if="video"
        src="/src/assets/meeting/video-on.svg"
        alt="비디오 on"
      />
      <img v-else src="/src/assets/meeting/video-off.svg" alt="비디오 off" />
    </button>
    <button id="myAudio" class="set-btn" @click="audioOnOff">
      <img
        v-if="audio"
        src="/src/assets/meeting/audio-on.svg"
        alt="마이크 on"
      />
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
      <Main :room-data="roomData" />
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
  width: 1460px;
  height: 180px;

  margin: 20px;
  /* justify-content: space-around; */
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
  margin-left: 20px;
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
