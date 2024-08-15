<script setup>
import { useCorrectStore } from "@/stores/correct";
import { problemStore } from "@/stores/problem";
import { onMounted, onUnmounted, ref } from "vue";
import { useRouter } from "vue-router";

const cStore = useCorrectStore();
const ranking = ref(cStore.userRankList);
const router = useRouter();
const pStore = problemStore();

let problemPoint = 0;

pStore.selectedProblemList.forEach((problem) => {
  problemPoint += problem.level * 3;
});

function rankCheck(rank) {
  switch (rank) {
    case "1":
      return "one";
    case "2":
      return "two";
    case "3":
      return "three";
  }
}

function closeRoom() {
  router.replace({ name: "home" });
}

onMounted(() => {
  console.log(ranking);
});
</script>

<template>
  <div class="modal-overlay">
    <div class="result-con box box-col slideDown">
      <span class="bold-text result-title">게임 결과</span>
      <div class="result-list box">
        <div
          class="result-item box flex-align"
          v-for="user in ranking"
          :key="user.userRank"
        >
          <span class="rank bold-text" :class="rankCheck(user.userRank)">
            {{ user.userRank }}
          </span>
          <div class="name bold-text">
            {{ user.username }}
            <span class="exp">+EXP</span>
            <span class="score">{{ (4 - user.userRank) * problemPoint }}</span>
          </div>
          <div class="time bold-text md box">
            {{
              String(
                Math.floor(Math.floor(user.userTime * 0.001) / 60)
              ).padStart(2, "0")
            }}
            :
            {{
              String(Math.floor(user.userTime * 0.001) % 60).padStart(2, "0")
            }}
          </div>
        </div>
      </div>
      <button class="exit-btn bold-text" @click="closeRoom">나가기</button>
    </div>
  </div>
</template>

<style scoped>
.result-con {
  width: 700px;
  height: 600px;
  position: absolute;
  align-items: center;
  background-color: #d9bbff;
  z-index: 10;
}

.result-title {
  margin: 25px;
  font-size: 40px;
}
.result-list {
  width: 600px;
  height: 350px;
  position: relative;
  background-color: #cadcff;
}
.exit-btn {
  width: 600px;
  height: 80px;
  margin-top: 20px;
  font-size: 35px;

  background-color: #ff8e8e;
  border-radius: 10px;
  border-width: 7px;
  border-color: #ff4d4d;
}
.result-item {
  height: 82px;
  margin: 23px;
  background-color: white;
}
.rank {
  font-size: 35px;
  position: absolute;
}
.name {
  position: absolute;
  font-size: 35px;
  left: 120px;
}
.time {
  position: absolute;
  width: 120px;
  right: 50px;
  padding: 5px 10px;
  background-color: #dbe7ff;
}
.exp {
  font-size: 15px;
}
.score {
  font-size: 15px;
  margin-left: 5px;
}
.one {
  font-size: 55px;
  left: 53px;
  color: #eaf833;
  -webkit-text-stroke-color: #74a705;
}
.two {
  font-size: 40px;
  left: 50px;
  color: #d8d8d8;
  -webkit-text-stroke-color: #989595;
}
.three {
  font-size: 35px;
  left: 53px;
  color: #e4624c;
  -webkit-text-stroke-color: #982a17;
}
</style>
