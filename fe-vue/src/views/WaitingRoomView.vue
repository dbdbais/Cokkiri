<script setup>
import { useRoute, useRouter } from "vue-router";
import {
  getWaitingRoom,
  exitWaitingRoom,
  getGameProblem,
  getProblemList,
  selectedProblem,
} from "@/api/waitingroom";
import { getUser, getUserName } from "@/api/user";
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
import { getProblem } from "@/api/problem";

import { problemStore } from "@/stores/problem";

const store = userStore();
const chatStore = useChatStore();
const loadingStore = useLodingStore();
const router = useRouter();
const route = useRoute();
const roomData = ref([]);
const roomUsers = ref([]);

const useProblemStore = problemStore();
const friendInvite = ref(false);
const problemModal = ref(false);
const problemList = ref([]);
const problemListNum = ref([]);

const ws = new WebSocket(
  `${process.env.VITE_VUE_SOCKET_URL}room/${route.params.roomId}/${store.user.nickname}`
);

ws.onmessage = function (event) {
  let data = event.data.split("|!|");
  if (data.length === 2) {
    let username = data[0];
    let message = data[1];
    chatStore.sendChat(`${username} : ${message}`);
  } else {
    let event = data[2];
    let param = data[3];
    switch (event) {
      case "ENTER":
        if (store.user.nickname !== param) {
          getUserName(param).then((res) => {
            console.log(res.data);
            res.data["num"] = getRandomIntInclusive(1, 5);
            roomUsers.value.push(res.data);
          });
        }
        chatStore.sendChat(`${param}님이 입장하였습니다.`);
        break;
      case "QUIT":
        roomUsers.value = roomUsers.value.filter((user) => {
          return user.nickname !== param;
        });
        chatStore.sendChat(`${param}님이 퇴장하였습니다.`);
        break;
      case "START":
        let time;
        if (store.user.nickname !== roomData.value.hostName) {
          time = 300;
        } else {
          time = 0;
        }
        setTimeout(() => {
          selectedProblem(problemListNum.value, route.params.roomId)
            .then((res) => {
              console.log(res.data);
            })
            .catch((err) => {
              console.log(err);
            });
          ws.close();
          useProblemStore.select(problemList.value);
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
        }, time);

        break;
      case "ADD":
        problemList.value = [];
        problemListNum.value = [];

        for (let i = 3; i < data.length; i++) {
          problemListNum.value.push(Number(data[i]));
          getProblem(data[i])
            .then((res) => {
              problemList.value.push(res.data);
            })
            .catch((err) => {
              console.log(err);
            });
        }
    }
  }
};

function getRandomIntInclusive(min, max) {
  const minCeiled = Math.ceil(min);
  const maxFloored = Math.floor(max);
  return Math.floor(Math.random() * (maxFloored - minCeiled + 1) + minCeiled); // 최댓값도 포함, 최솟값도 포함
}

onMounted(async () => {
  loadingStore.loading();
  chatStore.resetChatList();
  setTimeout(() => {
    loadingStore.loadingSuccess();
    console.log(roomData.value);
    roomData.value.users.forEach((user) => {
      getUserName(user)
        .then((res) => {
          res.data["num"] = getRandomIntInclusive(1, 6);
          roomUsers.value.push(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    });
  }, 1000);

  const route = useRoute();

  const success = (res) => {
    roomData.value = res.data;
  };

  const fail = (err) => {
    console.log(err);
  };
  getWaitingRoom(route.params.roomId, success, fail);
  // getUser();
});

const startStudy = function () {
  if (roomData.value.isGame) {
    if (problemList.value.length === 0) {
      Swal.fire({
        icon: "error",
        title: "문제를 선택해주세요!",
      });
      return;
    }
  }
  ws.send("|@|");
};

const selectProblem = (problems, minLevel, maxLevel) => {
  problemList.value = problems;
  if (roomData.value.isGame) {
    getGameProblem({
      sessionId: route.params.roomId,
      minDifficulty: minLevel,
      maxDifficulty: maxLevel,
    })
      .then((res) => {
        console.log(res.data);
        getProblemList(route.params.roomId).then((res) => {
          console.log(res.data);
          problemList.value = res.data;
        });
      })
      .catch((err) => {
        console.log(err);
      });
  }

  console.log(problemList.value);
  // send 보내야함
  let sendData = "|%|";
  problemList.value.forEach((problem) => {
    sendData += "|!|" + problem;
  });
  ws.send(sendData);
  problemModal.value = false;
};

const sendChat = (chatData) => {
  ws.send(chatData);
};

const exitRoom = async function () {
  ws.close();
  loadingStore.loading();
  await exitWaitingRoom({
    sessionId: route.params.roomId,
    userName: store.user.nickname,
  });

  setTimeout(() => {
    loadingStore.loadingSuccess();
  }, 1000);
  router.replace({ name: "home" });
};
</script>

<template>
  <div>
    <div class="waiting-room">
      <WaitingRoomSetting v-if="false" />
      <WaitingRoomProblemList
        v-if="problemModal"
        :room-data="roomData"
        @problem-select="selectProblem"
        @close="problemModal = false"
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
        <WaitingRoomProblem
          @open="problemModal = true"
          :problem-list="problemList"
          :room-data="roomData"
        />
        <WaitingRoomChat @chat="sendChat" />
        <div class="box-col button-con">
          <button class="bold-text btn friend" @click="friendInvite = true">
            <img
              src="/src/assets/friend.svg"
              alt="친구초대"
              style="margin-right: 30px; width: 70px"
            />친구초대
          </button>
          <button
            class="bold-text btn start"
            @click="startStudy"
            :class="{ 'no-host': store.user.nickname !== roomData.hostName }"
            :disabled="store.user.nickname !== roomData.hostName"
          >
            <img
              src="/src/assets/start.svg"
              alt="시작하기"
              style="margin-right: 30px; width: 70px"
              v-if="store.user.nickname === roomData.hostName"
            />{{
              store.user.nickname === roomData.hostName ? "시작하기" : "대기중"
            }}
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

.no-host {
  background-color: grey;
  border-color: rgb(183, 181, 181);
  color: gray;
}
</style>
