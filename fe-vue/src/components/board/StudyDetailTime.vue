<template>
  <div class="box-main-group time nomal-text md" v-if="studyTime.time">
    {{ week[studyTime.week] }}:{{ studyTime.time }}
  </div>
</template>

<script setup>
defineProps({
  studyTime: Object,
});

const week = {
  mon: "월요일",
  tue: "화요일",
  web: "수요일",
  thu: "목요일",
  fri: "금요일",
  sat: "토요일",
  sun: "일요일",
};
</script>

<style scoped>
.time {
  width: 90%;
  height: 50px;

  margin-top: 5px;
  font-size: 20px;
  color: white;
  -webkit-text-stroke: 1px black;
}
</style>
