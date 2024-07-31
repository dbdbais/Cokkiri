<script setup>
import { useRoute, useRouter } from "vue-router";
import { getWatingRoom } from "@/api/watingroom";
import { getUser } from "@/api/user";
import "@/assets/css/watingroom.css";

import WatingRoomRule from "@/components/watingroom/WatingRoomRule.vue";
import WatingRoomMember from "@/components/watingroom/WatingRoomMember.vue";
import { ref, onMounted } from "vue";
import { useLodingStore } from "@/stores/loading";

const loadingStore = useLodingStore();
const router = useRouter();
const route = useRoute();
const roomData = ref([]);
const roomUsers = ref([]);

onMounted(async () => {
  loadingStore.loading();
  setTimeout(() => {
    // console.log(roomData.value);
    loadingStore.loadingSuccess();
    roomData.value.users.forEach((user) => {
      // console.log(user);
      getUser(user, getUserData, fail);
    });
  }, 1000);
  const route = useRoute();
  const success = (res) => {
    roomData.value = res.data;
  };
  const getUserData = (res) => {
    roomUsers.value.push(res.data);
    // console.log(roomUsers.value);
  };

  const fail = (err) => {
    console.log(err);
  };

  getWatingRoom(route.params.roomId, success, fail);
  getUser();
});

const roomName = ref(null);
const hostName = ref(null);
const isGame = ref(null);
const isOpen = ref(null);
const rules = ref(null);
const users = ref(null);
const roomComent = ref(null);

const goMeetingRoom = function () {
  router.push({ name: "meeting", params: { roomId: route.params.roomId } });
};
</script>

<template>
  <div>
    <div class="wating-room">
      <div class="box-row">
        <div class="box-col">
          <div class="room-exit bold-text md">
            <img src="/src/assets/exit_room.svg" alt="방나가기" />
            나가기
          </div>
          <WatingRoomRule :rules="roomData.rules" />
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
            <WatingRoomMember :users="roomUsers" />
          </div>
          <div>
            <button class="bold-text btn" @click="goMeetingRoom">
              시작하기
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.wating-room {
  padding: 30px;
}
.room-exit,
.category {
  width: 160px;
  height: 60px;
}
.category {
  margin-left: 30px;
}
.title {
  margin-left: 30px;
  -webkit-text-stroke: 2px black;
}
.btn {
  width: 200px;
  height: 200px;
  background-color: yellow;
  border-width: 20px;
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
</style>
