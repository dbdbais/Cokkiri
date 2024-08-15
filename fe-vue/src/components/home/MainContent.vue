<template>
  <div class="main-content box-main-con">
    <div class="box-row">
      <div
        class="filter-btn box-main-noti"
        :class="{ active: categoryObj.all }"
        @click="$emit('is-game')"
      >
        전체
      </div>
      <div
        class="filter-btn box-main-noti"
        :class="{ active: categoryObj.study }"
        @click="$emit('is-game', false)"
      >
        공부방
      </div>
      <div
        class="filter-btn box-main-noti"
        :class="{ active: categoryObj.game }"
        @click="$emit('is-game', true)"
      >
        게임방
      </div>
      <button
        class="refresh md"
        @click="$emit('refresh')"
        @mouseenter="refreshHover = true"
        @mouseleave="refreshHover = false"
      >
        <img :src="imageSrc()" alt="새로고침" style="width: 40px" />
      </button>
    </div>
    <div id="room-container" class="rooms">
      <Room
        v-for="room in rooms"
        :key="room.id"
        :room="room"
        @click="$emit('go-room', room.sessionId)"
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
import Room from "./Room.vue";
import Page from "@/components/common/Page.vue";

const refreshHover = ref(false);

defineProps({
  rooms: Object,
  currentPage: Number,
  categoryObj: Object,
});

function imageSrc() {
  let img = "refresh";
  if (refreshHover.value) {
    img = "hover";
  }
  switch (img) {
    case "refresh":
      return new URL("@/assets/refresh.svg", import.meta.url).href;
    case "hover":
      return new URL("@/assets/refresh-hover.svg", import.meta.url).href;
    default:
      return new URL("@/assets/refresh.svg", import.meta.url).href;
  }
}
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
.refresh {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  border-width: 5px;
  border-color: #5bb5d9;
  background-color: #eaf3f7;
}
.filter-btn:hover,
.refresh:hover {
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
