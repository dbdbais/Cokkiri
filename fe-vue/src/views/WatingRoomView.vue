<script setup>
import { useRoute } from "vue-router";
import "@/assets/css/watingroom.css";
import roomData from "@/assets/data/room/2156.json";
import WatingRoomRule from "@/components/watingroom/WatingRoomRule.vue";
import WatingRoomMember from "@/components/watingroom/WatingRoomMember.vue";

const route = useRoute();

console.log(roomData);

const { title, isGame, rules, member } = roomData;
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
          <WatingRoomRule :rules="rules" />
        </div>
        <div class="box-col">
          <div class="member-data">
            <div class="flex-align">
              <div class="category md bold-text">
                {{ isGame ? "게임방" : "공부방" }}
              </div>
              <p class="bold-text title">
                {{ title }}
              </p>
            </div>
            <WatingRoomMember :member="member" />
          </div>
          <div class="btn"></div>
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
  width: 700px;
  height: 300px;
  background-color: yellow;
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
