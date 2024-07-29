<script setup>
import "animate.css";
defineProps({
  member: Array,
});

const imgUrl = "/src/assets/rank/";

const animateDelay = "animate__delay-";
const animamteSpeed = [
  "animate__slow",
  "animate__slower",
  "animate__fast",
  "animate__faster",
  "",
];
</script>

<template>
  <div class="member-container box">
    <div class="member box md-col" v-for="user in member" :key="user.index">
      <div class="bold-text name" :class="user.rank">
        {{ user.name }}
      </div>
      <img
        :src="imgUrl + user.rank + '.svg'"
        alt="티어"
        class="rank animate__animated animate__bounce animate__infinite"
        :class="[
          animateDelay + (user.id % 4) + 's',
          animamteSpeed[user.id % 5],
        ]"
      />
    </div>
  </div>
</template>

<style scoped>
.member-container {
  width: 1200px;
  height: 600px;
  margin-left: 30px;

  display: grid;
  grid-template-columns: 250px 250px 250px;
  grid-template-rows: 250px 250px;
  grid-gap: 20px;
}

.member {
  background-color: white;
  flex-direction: column;
  padding: 10px;
}
.name {
  font-size: 30px;
}
.rank {
  margin-top: 50px;
  width: 50%;
}

.seed {
  color: #a4c400;
  -webkit-text-stroke: 1px #5a461e;
}
.orange {
  color: #f9973e;
  -webkit-text-stroke: 1px #ad5c26;
}
.kiwi {
  color: #cf8c5d;
  -webkit-text-stroke: 1px #5a461e;
}
.durian {
  color: #29a780;
  -webkit-text-stroke: 1px #175b45;
}
.koreamelon {
  color: #e8e290;
  -webkit-text-stroke: 1px #8b753d;
}
.apple {
  color: #ff8e9e;
  -webkit-text-stroke: 1px #aa4252;
}
</style>
