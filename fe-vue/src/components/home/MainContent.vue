<template>
  <div class="main-content box-main-con">
    <div class="box-row">
      <div class="filter-btn box-main-noti" @click="$emit('is-game')">전체</div>
      <div class="filter-btn box-main-noti" @click="$emit('is-game', false)">공부방</div>
      <div class="filter-btn box-main-noti" @click="$emit('is-game', true)">게임방</div>
    </div>
    <div id="room-container" class="rooms">
      <Room
        v-for="room in rooms"
        :key="room.id"
        :room="room"
        @click="goRoom(room.sessionId)"
      />
    </div>
    <Page
      class="page"
      :current-page="currentPage"
      @change-page="(motion) => $emit('change-page', motion)"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { goWaitingRoom } from "@/api/waitingroom";
import Room from "./Room.vue";
import Page from "@/components/common/Page.vue";
// import roomList from "@/assets/data/roomList.json";
import roomList from "@/assets/data/roomListAxios.json";

defineProps({
  rooms: Object,
  currentPage: Number,
});

const router = useRouter();
const user = ref("김종덕");

rooms: [
  { id: 1, name: "알고리즘 고수들방", members: 116 },
  { id: 2, name: "초보. 매너방요", members: 206 },
  // Add more rooms here
];

const goRoom = function (id) {
  console.log(id);
  const success = (res) => {
    console.log(res.data);
    router.push({ name: "waitingRoom", params: { roomId: id } });
  };
  const fail = (err) => {
    console.log(err);
  };
  goWaitingRoom({ sessionId: id, userName: user.value }, success, fail);
};
</script>

<style scoped>
.filter-btn {
  padding: 10px 30px;
  margin-right: 15px;
  font-size: 30px;
  color: #5bb5d9;
  -webkit-text-stroke: #089eda;
}

.filter-btn:hover {
  color: #eaf3f7;
  border-color: #eaf3f7;
  background-color: #5bb5d9;
}
#room-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr 1fr;
  margin-top: 20px;
  gap: 35px;
}
.page {
  bottom: -110px;
  left: 1040px;
}
</style>
