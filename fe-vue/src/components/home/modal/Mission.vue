<script setup>
import { onMounted, ref } from "vue";
import { getUser } from "@/api/user";
import { userStore } from "@/stores/user";

const store = userStore();
const missionText = {
  SOLVE_PROBLEM: "문제 1개 맞추기",
  PLAY_GAME: "게임 1회 플레이하기",
  ATTEND_STUDY: "스터디 1회 참가하기",
  INVITE_FRIEND: "친구 1명 초대하기",
  CREATE_REVIEW: "리뷰 1개 작성하기",
};
const clearCnt = ref(0);
const missionList = ref([]);
onMounted(() => {
  getUser(store.user.id)
    .then((res) => {
      Object.keys(res.data.mission).forEach((missionKey) => {
        missionList.value.push({
          mission: missionText[missionKey],
          clear: res.data.mission[missionKey],
        });
        if (res.data.mission[missionKey]) {
          clearCnt.value += 1;
        }
      });
    })
    .catch((err) => {
      console.log(err);
    });
});
</script>

<template>
  <div class="mission-con box slideLeft">
    <div class="bold-text mission-text flex-align">
      <img src="/src/assets/elephant-circus.svg" alt="일일미션" style="margin-right: 15px" />
      일일미션
      <img src="@/assets/mail-close.svg" alt="close-icon" class="close-icon" @click="$emit('close')" />
    </div>
    <div class="mission-box bold-text">
      <div class="badge md nomal-text">
        오늘의 미션 완료 : {{ clearCnt }} / 3
      </div>
      <div class="box box-col mission-content">
        <div class="box flex-align mission-item" v-for="mission in missionList" :key="mission.mission">
          {{ mission.mission }}
          <input type="checkbox" class="mission-clear" :checked="mission.clear" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.mission-con {
  width: 500px;
  height: 550px;
  position: absolute;
  padding: 20px;
  background-color: #cadcff;
  right: 20px;
  bottom: 20px;
  z-index: 5;
  border-width: 3px;
}

.mission-text {
  font-size: 35px;
  margin-bottom: 20px;
}

.mission-box {
  position: relative;
  font-size: 20px;
}

.badge {
  border: 2px solid #5bb5d9;
  background-color: #f6f6f6;
  padding: 10px;
  border-radius: 10px;
  color: #5bb5d9;
  font-size: 22px;
  -webkit-text-stroke: 0px;
}

.mission-content {
  height: 360px;
  margin-top: 20px;
  background-color: #c191ff;
  justify-content: space-between;
  border-width: 2px;
  align-items: center;
  padding: 20px;
}

.close-icon {
  position: absolute;
  top: 15px;
  right: 20px;
}

.mission-item {
  width: 420px;
  height: 90px;
  padding: 10px 20px;
  font-size: 23px;
  border-width: 2px;
  justify-content: space-between;
  background-color: #cadcff;
}

.mission-clear {
  width: 40px;
  height: 40px;
  pointer-events: none;
}
</style>
