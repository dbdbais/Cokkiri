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
        @click="goRoom(room.sessionId)"
      />
    </div>
    <Pagination id="pagi-container" />
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { goWaitingRoom, getWaitingRoomList } from "@/api/waitingroom";
import Room from "./Room.vue";
import Pagination from "@/components/common/Pagination.vue";
// import roomList from "@/assets/data/roomList.json";
import roomList from "@/assets/data/roomListAxios.json";

// onMounted(() => {
//   const success = (res) => {
//     console.log(res.data.waitingRoomList);
//     rooms.value = res.data.waitingRoomList;
//   };
//   const fail = (err) => {
//     console.log(err);
//   };

//   getWaitingRoomList(success, fail);
// });

const router = useRouter();
const user = ref("어지민");
// 더미 데이터
// const rooms = ref(roomList.roomList.room);
// 더미 데이터 (Axios 용)
const rooms = ref(roomList.waitingRoomList);
// Axios 용
// const rooms = ref("");

rooms: [
  { id: 1, name: "알고리즘 고수들방", members: 116 },
  { id: 2, name: "초보. 매너방요", members: 206 },
  // Add more rooms here
];

// const goRoom = function (id) {
//   const success = (res) => {
//     console.log(res.data);
//     router.push({ name: "waitingRoom", params: { roomId: id } });
//   };
//   const fail = (err) => {
//     console.log(err);
//   };
//   goWaitingRoom({ sessionId: id, userName: user.value }, success, fail);
// };

const goRoom = function (id) {
  router.push({ name: "waitingRoom", params: { roomId: id } });
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
