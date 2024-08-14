<template>
  <div class="main-content box-main-con">
    <div class="box-row">
      <div class="filter-btn box-main-noti" :class="{ active: categoryObj.all }" @click="$emit('is-game')">
        전체
      </div>
      <div class="filter-btn box-main-noti" :class="{ active: categoryObj.study }" @click="$emit('is-game', false)">
        공부방
      </div>
      <div class="filter-btn box-main-noti" :class="{ active: categoryObj.game }" @click="$emit('is-game', true)">
        게임방
      </div>
    </div>
    <div id="room-container" class="rooms">
      <Room v-for="room in rooms" :key="room.id" :room="room" @click="$emit('go-room', room.sessionId)" />
    </div>
    <Page class="page" :current-page="currentPage" @change-page="(motion) => $emit('change-page', motion)" />
  </div>
</template>

<script setup>
import Room from "./Room.vue";
import Page from "@/components/common/Page.vue";

defineProps({
  rooms: Object,
  currentPage: Number,
  categoryObj: Object,
});
</script>

<style scoped>
.main-content {
  position: relative;
}

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
  bottom: 10px;
  left: 620px;
}

.active {
  color: #eaf3f7;
  border-color: #eaf3f7;
  background-color: #5bb5d9;
}
</style>
