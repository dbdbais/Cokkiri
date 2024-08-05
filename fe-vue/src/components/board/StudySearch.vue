<script setup>
import StudyTime from "@/components/board/StudyTime.vue";
import { ref } from "vue";

const studyName = ref("");

const emit = defineEmits(["search"]);

const selected = function (timeData) {
  const params = ref({});
  const searchData = {
    regularName: studyName.value,
    weekday: timeData.week,
    start:
      timeData.start[0] +
      timeData.start[1] +
      timeData.start[3] +
      timeData.start[4],
    end: timeData.end[0] + timeData.end[1] + timeData.end[3] + timeData.end[4],
  };
  for (const key in searchData) {
    if (searchData[key]) {
      params.value[key] = searchData[key];
    }
  }
  console.log(params.value);
  emit("search", params.value);
};
</script>

<template>
  <div class="search flex-align">
    <div class="name-con flex-align">
      <label for="study-name" class="bold-text">이름</label>
      <input
        type="text"
        id="study-name"
        class="nomal-text"
        v-model="studyName"
      />
    </div>
    <div class="time">
      <StudyTime timeType="검색" @selectedTime="selected" />
    </div>
  </div>
</template>

<style scoped>
/* 검색창 */
.search {
  /* 크기, 위치 */
  width: 1180px;
  height: 120px;
  margin-top: 20px;
  position: relative;
  background-color: white;
  border: 5px solid #3b72ff;
  border-radius: 10px;
}
.time {
  width: 580px;
  margin-left: 20px;
}
.bold-text {
  font-size: 55px;
  -webkit-text-stroke: 1.5px #3b72ff;
}
.nomal-text {
  font-size: 40px;
}
.name-con {
  margin: 10px 20px;
}
#study-name {
  width: 400px;
  height: 80px;
  margin-left: 20px;
  padding-left: 10px;
  border: 3px solid #3b72ff;
  color: #3b72ff;
  border-radius: 5px;
}
</style>
