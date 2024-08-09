<script setup>
import { useRoute, useRouter } from "vue-router";
import { getWaitingRoom, exitWaitingRoom } from "@/api/waitingroom";
import { getUser } from "@/api/user";
import "@/assets/css/waitingroom.css";

import WaitingRoomRule from "@/components/waitingroom/WaitingRoomRule.vue";
import WaitingRoomMember from "@/components/waitingroom/WaitingRoomMember.vue";
import WaitingRoomChat from "@/components/waitingroom/WaitingRoomChat.vue";
import WaitingRoomFriend from "@/components/waitingroom/WaitingRoomFriend.vue";
import WaitingRoomSetting from "@/components/waitingroom/WaitingRoomSetting.vue";
import WaitingRoomProblem from "@/components/waitingroom/WaitingRoomProblem.vue";
import WaitingRoomProblemList from "@/components/waitingroom/WaitingRoomProblemList.vue";
import { userStore } from "@/stores/user";

import { ref, onMounted } from "vue";
import { useLodingStore } from "@/stores/loading";
import { useChatStore } from "@/stores/chat";

const store = userStore();
const chatStore = useChatStore();
const loadingStore = useLodingStore();
const router = useRouter();
const route = useRoute();
const roomData = ref([]);
const roomUsers = ref([]);
const chatList = ref([]);
const friendInvite = ref(false);
const problemModal = ref(false);

const ws = new WebSocket(
  `${process.env.VITE_VUE_SOCKET_URL}room/${route.params.roomId}/${store.user.nickname}`
);

ws.onmessage = function (event) {
  let data = event.data.split("|!|");
  console.log(data);
  if (data.length === 2) {
    let username = data[0];
    let message = data[1];
    chatList.value.push(`${username} : ${message}`);
    console.log(chatList.value);
  } else {
    let event = data[2];
    let param = data[3];
    switch (event) {
      case "ENTER":
        chatList.value.push(`${param}님이 입장하였습니다.`);
        break;
      case "QUIT":
        chatList.value.push(`${param}님이 퇴장하였습니다.`);
        break;
      case "START":
        ws.close();
        console.log("입장!");

        console.log(roomData.value);
        if (roomData.value.isGame) {
          router.push({
            name: "gameProgress",
            params: { gameId: route.params.roomId },
          });
        } else {
          router.push({
            name: "meeting",
            params: { roomId: route.params.roomId },
          });
        }
    }
  }
  chatStore.autoScroll();
};

onMounted(async () => {
  loadingStore.loading();
  setTimeout(() => {
    console.log(roomData.value);
    loadingStore.loadingSuccess();
    roomData.value.users.forEach((user) => {
      getUser(user)
        .then((res) => {
          console.log(res.data);
          roomUsers.value.push(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    });
  }, 1000);
  const route = useRoute();
  const success = (res) => {
    // console.log(res.data)
    roomData.value = res.data;
  };

  const fail = (err) => {
    console.log(err);
  };

  getWaitingRoom(route.params.roomId, success, fail);
  // getUser();
});

const startStudy = function () {
  ws.send("|@|");
};
const selectProblem = (problemList) => {
  console.log(problemList);
  // send 보내야함
  problemModal.value = false;
};

const sendChat = (chatData) => {
  ws.send(chatData);
};

const exitRoom = function () {
  ws.close();

  const success = (res) => {
    console.log(res.data);
  };
  const fail = (err) => {
    console.log(err);
  };

  exitWaitingRoom({
    sessionId: route.params.roomId,
    userName: store.user.nickname,
  });
  router.replace({ name: "home" });
};
</script>

<template>
  <div>
    <div class="waiting-room">
      <WaitingRoomSetting v-if="false" />
      <WaitingRoomProblemList
        v-if="problemModal"
        @problem-select="selectProblem"
      />
      <WaitingRoomFriend
        v-if="friendInvite"
        :room-id="route.params.roomId"
        @close="friendInvite = false"
      />
      <div class="box-row">
        <div class="box-col">
          <div class="rule-data">
            <div class="room-exit bold-text md" @click="exitRoom">
              <img src="/src/assets/exit_room.svg" alt="방나가기" />
              나가기
            </div>
            <WaitingRoomRule :rules="roomData.rules" />
          </div>
        </div>
        <div class="box-col">
          <div class="member-data">
            <div class="flex-align" style="justify-content: space-between">
              <div class="flex-align">
                <div class="category md bold-text">
                  {{ roomData.isGame ? "게임방" : "공부방" }}
                </div>
                <p class="bold-text title">
                  {{ roomData.roomName }}
                </p>
              </div>
              <button class="setting bold-text">설정</button>
            </div>
            <WaitingRoomMember :users="roomUsers" />
          </div>
        </div>
      </div>
      <div class="bottom flex-align">
        <WaitingRoomProblem @open="problemModal = true" />
        <WaitingRoomChat @chat="sendChat" :chat-list="chatList" />
        <div class="box-col button-con">
          <button class="bold-text btn friend" @click="friendInvite = true">
            <img
              src="/src/assets/friend.svg"
              alt="친구초대"
              style="margin-right: 30px; width: 70px"
            />친구초대
          </button>
          <button class="bold-text btn start" @click="startStudy">
            <img
              src="/src/assets/start.svg"
              alt="시작하기"
              style="margin-right: 30px; width: 70px"
            />시작하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.waiting-room {
  padding: 30px 30px 0px 30px;
  position: relative;
}

.room-exit,
.category {
  width: 160px;
  height: 60px;
}

.category {
  margin-left: 40px;
}

.title {
  margin-left: 40px;
  -webkit-text-stroke: 2px black;
}

.bottom {
  justify-content: space-between;
}

.button-con {
  justify-content: space-between;
  align-items: center;
  height: 320px;
}

.start {
  background-color: #00f6ac;
  border-color: #2ec5a1;
}

.friend {
  background-color: #c191ff;
  border-color: #3b72ff;
}

.btn button {
  width: 200px;
  height: 200px;
  background-color: gold;
  color: white;
  border-width: 10px;
  border-radius: 10px;
}

.room-exit {
  background-color: #ff6b6b;
  border: 5px solid white;
  border-radius: 10px;
  padding-right: 10px;
}

.bold-text {
  font-size: 35px;
}

.btn {
  width: 300px;
  height: 140px;
  border-width: 10px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 35px;
  -webkit-text-stroke: 2px black;
}

.setting {
  width: 100px;
  height: 50px;
  font-size: 25px;
  background-color: #c191ff;

  border-radius: 10px;
  border-width: 5px;
  border-color: #3b72ff;
}
</style>
