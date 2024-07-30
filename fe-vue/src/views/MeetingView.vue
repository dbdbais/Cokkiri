<script setup>
import "@/assets/css/meeting.css";
import Member from "@/components/meeting/Member.vue";
import Meom from "@/components/meeting/Meom.vue";
import Main from "@/components/meeting/Main.vue";
import { ref } from "vue";

const members = ref([
  { nickname: "user_1231" },
  { nickname: "user_1235" },
  { nickname: "user_1331" },
  { nickname: "user_1251" },
  { nickname: "user_2231" },
]);

const sizeInfo = ref({
  x: 0,
  y: 0,
  width: 0,
  height: 0,
});

const mousePoint = function (e) {
  sizeInfo.value.x = e.clientX;
  sizeInfo.value.y = e.clientY;
  sizeInfo.value.width = parseInt(styles.width);
  sizeInfo.value.height = parseInt(styles.height);
};
</script>
<template>
  <div class="meeting-room">
    <div class="members box-main-con flex-align">
      <Member
        v-for="member in members"
        :key="member.nickname"
        :member="member"
      />
    </div>
    <div class="main box-main-con">
      <Main />
    </div>
  </div>
</template>

<style scoped>
.members {
  width: 1500px;
  height: 180px;

  margin: 20px;
  justify-content: space-around;
}
.main {
  width: 1880px;
  height: 840px;
  margin: 20px;
}

button {
  height: 100px;

  position: absolute;
  right: 0px;
  cursor: move;
}
</style>
