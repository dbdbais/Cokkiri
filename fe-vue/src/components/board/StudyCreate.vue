<script setup>
import StudyCreateTime from "@/components/board/StudyCreateTime.vue";
import { ref } from "vue";

// 사용자가 고른 스터디 시간들 {요일, 시작시간, 종료시간}
const selectedTime = ref([]);
// 영어로 받은 요일을 한글로 바꿔줄 객체
const weekKorea = {
  mon: "월",
  tue: "화",
  wed: "수",
  thu: "목",
  fri: "금",
  sat: "토",
  sun: "일",
};
//StudyCreateTime에서 받은 이벤트 처리
const selected = function (timeData) {
  console.log(timeData);
  // 만약 종료시간이 시작시간보다 이르다면 다시 고르게 해야함
  if (timeData.start < timeData.end) {
    selectedTime.value.push(timeData);
  } else {
    alert("종료시간이 시작시간보다 더 길게 해주세요!");
  }
};
</script>

<template>
  <div class="board-modal">
    <div class="bold-text" style="margin: 10px 0 0 30px">스터디 만들기</div>
    <div class="md-col">
      <div class="create-box">
        <div class="flex-align top">
          <div class="study-name box-col">
            <label for="name" class="bold-text">스터디 명</label>
            <input type="text" name="name" id="name" class="nomal-text" />
          </div>
          <div class="member md-col">
            <label for="member" class="bold-text">인원</label>
            <!-- 스터디 인원 6명 제한 -->
            <input
              type="number"
              id="member"
              class="nomal-text"
              min="1"
              max="6"
              value="1"
            />
          </div>
        </div>
        <!--스터디 요일 및 시간 선택  -->
        <StudyCreateTime @selectedTime="selected" />
        <div class="selected-time md-col">
          <div
            class="time-item bold-text"
            v-for="time in selectedTime"
            :key="time.index"
          >
            {{ weekKorea[time.week] }} {{ time.start }} ~
            {{ time.end }}
          </div>
        </div>
      </div>

      <button class="board-btn nomal-text">스터디 만들기</button>
    </div>
  </div>
</template>

<style scoped>
.bold-text {
  font-size: 30px;
}
.create-box,
.board-btn {
  margin-top: 10px;
}

.study-name {
  width: 450px;
}
/* 선택된 스터디 시간들의 목록이 나올 부분 */
.selected-time {
  width: 100%;
  height: 100px;
  margin-top: 10px;
  background-color: #c191ff;

  border-radius: 10px;
  border: 3px solid #3b72ff;
}
/* 선택된 스터디 시간 1개 스타일 */
.time-item {
  width: 90%;
  height: 40px;
  background-color: #dbe7ff;
  border: 3px solid #3b72ff;
  border-radius: 10px;
}
.top {
  justify-content: space-between;
}
/* 입력 부분 폰트 사이즈 */
.create-box input {
  font-size: 25px;
  padding-left: 10px;
}
#name {
  width: 400px;
  height: 40px;
  border: 3px solid black;

  border-radius: 10px;
}
#member {
  width: 70px;
  height: 40px;
  border: 3px solid black;
  border-radius: 10px;
}
</style>
