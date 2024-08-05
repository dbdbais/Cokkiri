<script setup>
import { ref, onMounted } from "vue";

// onMounted(() => {
//   selectedWeek.value = "mon";
//   startTime.value = "09:00";
//   endTime.value = "09:00";
// });

defineProps({
  timeType: String,
});

const emit = defineEmits(["selected-time"]);
const week = ref([
  { id: 1, weekName: "mon", ko: "월" },
  { id: 2, weekName: "tue", ko: "화" },
  { id: 3, weekName: "wed", ko: "수" },
  { id: 4, weekName: "thu", ko: "목" },
  { id: 5, weekName: "fri", ko: "금" },
  { id: 6, weekName: "sat", ko: "토" },
  { id: 7, weekName: "sun", ko: "일" },
]);
const selectedWeek = ref("");
const startTime = ref("");
const endTime = ref("");
const ko = ref("");

const selected = function () {
  emit("selected-time", {
    week: selectedWeek.value,
    start: startTime.value,
    end: endTime.value,
  });
};
</script>
<template>
  <div>
    <div class="flex-align time">
      <div>
        <div class="select" v-for="day in week" :key="day.id">
          <input
            type="radio"
            :id="day.weekName"
            name="week"
            :value="day.weekName"
            v-model="selectedWeek"
          /><label :for="day.weekName" class="nomal-text">{{ day.ko }}</label>
        </div>
        <div>
          <div class="start">
            <input
              type="time"
              class="nomal-text"
              v-model="startTime"
              :max="endTime"
            />
          </div>
          <div class="end">
            <input
              type="time"
              class="nomal-text"
              v-model="endTime"
              :min="startTime"
            />
          </div>
        </div>
      </div>
      <button class="time-plus bold-text" @click="selected">
        {{ timeType }}
      </button>
    </div>
  </div>
</template>

<style scoped>
.time {
  margin-top: 5px;
  justify-content: space-between;
}

.start,
.end,
.select {
  display: inline-block;
}
/* 시간 추가 버튼 */
.time-plus {
  width: 80px;
  height: 80px;
  background-color: #c191ff;
  border-width: 3px;
  border-radius: 10px;
  border-color: #3b72ff;
  font-size: 30px;
  -webkit-text-stroke: 2px black;
}

input[type="time"] {
  font-size: 18px;
  margin-right: 15px;
  margin-top: 10px;
  border-radius: 10px;
  color: #3b72ff;
  border-color: #3b72ff;
}

/* 요일 선택 부분 커스텀 */
.select input[type="radio"] {
  display: none;
}
.select input[type="radio"] + label {
  display: inline-block;
  width: 35px;
  height: 35px;
  border: 2px solid #3b72ff;
  border-radius: 10px;

  text-align: center;
  font-weight: bold;
  font-size: 20px;
  margin-right: 10px;
}
.select input[type="radio"] + label {
  background-color: #fff;
  color: #3b72ff;
}
.select input[type="radio"] + label:hover {
  background-color: #3b72ff;
  color: #fff;
}
.select input[type="radio"]:checked + label {
  background-color: #3b72ff;
  color: #fff;
}
</style>
