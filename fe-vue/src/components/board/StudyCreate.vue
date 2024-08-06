<script setup>
import StudyTime from "@/components/board/StudyTime.vue";
import { createStudy } from "@/api/board";
import { ref } from "vue";

const emit = defineEmits(["get-regular-list"]);

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

const dataText = {
  regularName: "스터디 명",
  times: "시간",
  regularComment: "스터디 소개",
};

// 생성할 스터디 정보들 (이름, 인원, 고른 시간들, 소개)
const studyName = ref("");
const studyMember = ref(1);
const selectedTime = ref([]);
const selectedTimeId = ref(0);
const studyIntro = ref("");
const studyCreateDate = ref("");

//StudyCreateTime에서 받은 이벤트 처리
const selected = function (timeData) {
  timeData.id = selectedTimeId.value++;
  console.log(timeData);

  // 만약 종료시간이 시작시간보다 이르다면 다시 고르게 해야함
  if (timeData.start < timeData.end) {
    selectedTime.value.push(timeData);
  } else {
    alert("종료시간이 시작시간보다 더 길게 해주세요!");
  }
};
// 고른 시간 제거
const timeRemove = function (time) {
  console.log(time);
  console.log(selectedTime.value);
  selectedTime.value = selectedTime.value.filter(
    (element) => element.id !== time.id
  );
};
// 스터디 소개에서 엔터를 눌렀을 때
const introEnter = function (value) {
  // 빈칸이면 자동 소개글 넣기
  if (value === "\n") {
    studyIntro.value = "잘 부탁드립니다~\n";
  }
};
// 스터디 만들기
const studyCreate = function () {
  const success = (res) => {
    console.log(res.data);
    emit("get-regular-list");
  };
  const fail = (err) => {
    console.log(err);
  };

  const times = selectedTime.value.map((time) => {
    return `${time.week}|${
      time.start[0] + time.start[1] + time.start[3] + time.start[4]
    }|${time.end[0] + time.end[1] + time.end[3] + time.end[4]}`;
  });

  const studyData = {
    regularName: studyName.value,
    maxNum: studyMember.value,
    times: times,
    userName: "김종덕",
    regularComment: studyIntro.value,
  };

  const nullData = ref({});

  for (const key in studyData) {
    if (studyData[key].length === 0) {
      nullData.value[key] = key;
    }
  }
  let text = "";

  Object.keys(nullData.value).forEach((key) => {
    text += dataText[key] + ", ";
  });

  if (Object.keys(nullData.value).length > 0) {
    Swal.fire({
      icon: "error",
      title: "데이터를 입력해주세요!",
      text: text.slice(0, -2) + "칸이 비어있습니다",
    });
  } else {
    createStudy(studyData, success, fail);
  }
};
</script>

<template>
  <div class="board-modal slideDown">
    <div class="bold-text" style="margin: 10px 0 0 30px">스터디 만들기</div>
    <img
      src="@/assets/exit.svg"
      alt="나가기"
      class="exit"
      @click="emit('get-regular-list')"
    />
    <div class="md-col">
      <div class="create-box">
        <div class="flex-align top">
          <div class="study-name box-col">
            <label for="name" class="bold-text">스터디 명</label>
            <input
              type="text"
              name="name"
              id="name"
              class="nomal-text"
              v-model="studyName"
            />
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
              v-model="studyMember"
            />
          </div>
        </div>
        <!--스터디 요일 및 시간 선택  -->
        <div class="bold-text study-time">스터디 시간</div>
        <StudyTime @selectedTime="selected" time-type="시간추가" />
        <div class="selected-time md-col">
          <div
            class="time-item bold-text"
            v-for="time in selectedTime"
            :key="time.index"
            @click="timeRemove(time)"
          >
            {{ weekKorea[time.week] }} {{ time.start }} ~
            {{ time.end }}
            <button class="time-remove nomal-text">삭제</button>
          </div>
        </div>

        <div class="study-intro box-col">
          <label for="intro" class="bold-text">스터디 소개</label>
          <textarea
            name=""
            id="intro"
            class="nomal-text"
            rows="3"
            placeholder="잘 부탁드립니다~"
            v-model="studyIntro"
            @keyup.enter="introEnter(studyIntro)"
          ></textarea>
        </div>
      </div>
      <button class="board-btn nomal-text" @click="studyCreate">
        스터디 만들기
      </button>
    </div>
  </div>
</template>

<style scoped>
.bold-text {
  font-size: 30px;
}
.create-box,
.board-btn,
.study-intro,
.study-time,
#intro {
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
  padding-right: 10px;
  border-radius: 10px;
  border: 3px solid #3b72ff;
  overflow-y: scroll;
}

/* 선택된 스터디 시간 1개 스타일 */
.time-item {
  width: 90%;
  height: 40px;
  background-color: #dbe7ff;
  border: 3px solid #3b72ff;
  border-radius: 10px;
  margin: 3px;
  padding: 5px 10px;
  font-size: 20px;

  position: relative;
}
.time-remove {
  width: 50px;
  height: 25px;
  border-radius: 5px;
  border-width: 3px;
  border-color: #ff8888;
  background-color: #ff0000;
  color: white;
  font-size: 15px;
  position: absolute;
  right: 10px;
}
/* 인원 선택 박스 방향키 항상 표시 */
input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  -webkit-appearance: inner-spin-button;
  opacity: 1;
}
.top {
  justify-content: space-between;
}

/* 입력 부분 폰트 사이즈 */
.create-box input,
textarea {
  font-size: 25px;
  padding-left: 10px;
}
#name,
#member,
#intro {
  border: 2px solid black;

  border-radius: 10px;
}

#name {
  width: 400px;
  height: 40px;
}
#member {
  width: 70px;
  height: 40px;
}
#intro {
  width: 100%;
}
</style>
