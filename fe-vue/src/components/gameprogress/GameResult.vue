<script setup>
import { ref } from "vue";

const ranking = ref([
  { rank: "1", username: "어지민", resultTime: "12:12" },
  { rank: "2", username: "어지민", resultTime: "12:12" },
  { rank: "3", username: "어지민", resultTime: "12:12" },
]);

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
</script>

<template>
  <div class="result-con box box-col slideDown">
    <span class="bold-text result-title">게임 결과</span>
    <div class="result-list box">
      <div
        class="result-item box flex-align"
        v-for="user in ranking"
        :key="user.rank"
      >
        <span class="rank bold-text" :class="rankCheck(user.rank)">
          {{ user.rank }}
        </span>
        <span class="name bold-text">
          {{ user.username }}
        </span>
        <div class="time bold-text md box">
          {{ user.resultTime }}
        </div>
      </div>
    </div>
    <button class="exit-btn bold-text">나가기</button>
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
  right: 50px;
  padding: 5px 10px;
  background-color: #dbe7ff;
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
