<script setup>
import { useRoute, useRouter } from "vue-router";
import { getWaitingRoom } from "@/api/waitingroom";
import { getUser } from "@/api/user";
import "@/assets/css/waitingroom.css";

import WaitingRoomRule from "@/components/waitingroom/WaitingRoomRule.vue";
import WaitingRoomMember from "@/components/waitingroom/WaitingRoomMember.vue";
import WaitingRoomChat from "@/components/waitingroom/WaitingRoomChat.vue";
import WaitingRoomFriend from "@/components/waitingroom/WaitingRoomFriend.vue";

import { ref, onMounted } from "vue";
import { useLodingStore } from "@/stores/loading";

const loadingStore = useLodingStore();
const router = useRouter();
const route = useRoute();
const roomData = ref([]);
const roomUsers = ref([]);
const chatList = ref([]);
const friendInvite = ref(false);

const ws = new WebSocket(`ws://localhost:8080/room/${route.params.roomId}/abc`);

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
    }
  }
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
          // roomUsers.value.push(res.data);
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

const goMeetingRoom = function () {
  router.push({ name: "meeting", params: { roomId: route.params.roomId } });
};

const exitRoom = function () {
  ws.close();
  router.replace({ name: "home" });
};
</script>

<template>
  <div>
    <div class="waiting-room">
      <WaitingRoomFriend
        id="test"
        v-if="friendInvite"
        :room-id="route.params.roomId"
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
            <div class="flex-align">
              <div class="category md bold-text">
                {{ roomData.isGame ? "게임방" : "공부방" }}
              </div>
              <p class="bold-text title">
                {{ roomData.roomName }}
              </p>
            </div>
            <WaitingRoomMember :users="roomUsers" />
          </div>
        </div>
      </div>
      <div class="bottom flex-align">
        <WaitingRoomChat
          @chat="
            (chatData) => {
              ws.send(chatData);
            }
          "
          :chat-list="chatList"
        />
        <div class="box-col button-con">
          <button class="bold-text btn friend" @click="friendInvite = true">
            <img
              src="/src/assets/friend.svg"
              alt="친구초대"
              style="margin-right: 20px; width: 90px"
            />친구초대
          </button>
          <button class="bold-text btn start" @click="goMeetingRoom">
            <img
              src="/src/assets/start.svg"
              alt="시작하기"
              style="margin-right: 20px; width: 90px"
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
  width: 400px;
  height: 140px;
  border-width: 10px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 55px;
  -webkit-text-stroke: 2px black;
}
</style>
