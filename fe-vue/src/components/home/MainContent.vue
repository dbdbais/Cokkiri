<template>
  <div class="main-content box-main-con">
    <div class="box-row">
      <div class="filter-btn box-main-noti">
        <span class="title filter-title">전체</span>
      </div>
      <div class="filter-btn box-main-noti">
        <span class="title filter-title">공부방</span>
      </div>
      <div class="filter-btn box-main-noti">
        <span class="title filter-title">게임방</span>
      </div>
    </div>
    <div id="room-container" class="rooms">
      <Room
        v-for="room in rooms"
        :key="room.id"
        :room="room"
        @click="goRoom(room.id)"
      />
    </div>
    <Pagination id="pagi-container" />
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

import Room from "./Room.vue";
import Pagination from "@/components/common/Pagination.vue";
import roomList from "@/assets/data/roomList.json";

const router = useRouter();

const rooms = ref(roomList.roomList.room);
rooms: [
  { id: 1, name: "알고리즘 고수들방", members: 116 },
  { id: 2, name: "초보. 매너방요", members: 206 },
  // Add more rooms here
];

const goRoom = function (id) {
  router.push({ name: "watingRoom", params: { roomId: id } });
};
</script>

<style scoped>
.filter-btn {
  padding: 10px 30px;
  margin-right: 15px;
}

.filter-title {
  font-size: 20px;
  color: #5bb5d9;
  -webkit-text-stroke: #f6f6f6;
}

#room-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr 1fr;
  margin-top: 20px;
  gap: 35px;
}
</style>
