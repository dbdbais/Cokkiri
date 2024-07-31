<script setup>
import "@/assets/css/meeting.css";
import { joinSession } from "@/api/webRTC"
import Member from "@/components/meeting/Member.vue";
import Meom from "@/components/meeting/Meom.vue";
import Main from "@/components/meeting/Main.vue";
import { ref, onMounted } from "vue";

const members = ref([
  { num: "member_1" },
  { num: "member_2" },
  { num: "member_3" },
  { num: "member_4" },
  { num: "member_5" },
  { num: "member_6" }
]);

onMounted(() => {
  joinSession();
})

</script>
<template>
  <div class="meeting-room">
    <div class="members box-main-con flex-align" id="members">
     
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
video {
  border-radius: 20%;
}
</style>
