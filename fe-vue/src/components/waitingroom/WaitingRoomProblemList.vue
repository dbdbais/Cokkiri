<script setup>
import { onMounted, ref, watch } from "vue";
import { getAllProblems } from "@/api/problem";

const problemList = ref([]);
const selectProblem = ref([]);
const maxLevel = ref(9);
const minLevel = ref(1);
const selectedProblemList = ref({});

onMounted(() => {
  getProblemsList();
});

const getProblemsList = () => {
  getAllProblems()
    .then((res) => {
      problemList.value = res.data.filter((problem) => {
        return (
          problem.level >= minLevel.value && problem.level <= maxLevel.value
        );
      });
    })
    .catch((err) => {
      console.log(err);
    });
};

const selectedProblem = (no) => {
  if (selectedProblemList.value[no]) {
    selectProblem.value = selectProblem.value.filter((problemNo) => {
      return problemNo !== no;
    });
    selectedProblemList.value[no] = false;
  } else {
    if (selectProblem.value.length > 2) {
      Swal.fire({
        icon: "warning",
        title: "문제는 3개 이하로 골라주세요!",
      });
      return;
    }
    selectProblem.value.push(no);
    selectedProblemList.value[no] = true;
  }
};

watch([minLevel, maxLevel], () => {
  getProblemsList();
});
</script>

<template>
  <div class="problem-list box box-col slideDown">
    <img src="/src/assets/exit.svg" alt="나가기" class="exit" @click="$emit('close')" />
    <div class="filter flex-align bold-text">
      <div class="flex-align">
        <label for="">LEVEL</label>
        <input type="number" class="nomal-text" min="1" :max="maxLevel" v-model="minLevel" />
      </div>
      <span class="nomal-text"> ~ </span>
      <div class="flex-align">
        <label for="">LEVEL</label>
        <input type="number" class="nomal-text" :min="minLevel" max="9" v-model="maxLevel" />
      </div>
    </div>
    <div class="problem-list-box box box-col">
      <div class="problem-item box bold-text flex-align" v-for="problem in problemList" :key="problem.no">
        <div class="badge md">LEVEL {{ problem.level }}</div>
        <span class="no">NO.{{ String(problem.no).padStart(5, "0") }}</span><span class="dash nomal-text">-</span>
        <span class="name">{{ problem.title }}</span>
        <button class="select-btn bold-text" :class="{ selected: selectedProblemList[problem.no] }"
          @click="selectedProblem(problem.no)">
          {{ selectedProblemList[problem.no] ? "취소" : "선택" }}
        </button>
      </div>
    </div>
    <button class="selected-btn bold-text" @click="$emit('problem-select', selectProblem)">
      선택 완료
    </button>
  </div>
</template>

<style scoped>
.problem-list {
  width: 750px;
  height: 510px;
  padding: 20px;
  position: absolute;
  top: 300px;
  left: 570px;
  align-items: center;
  z-index: 3;
}

.problem-list-box {
  width: 700px;
  height: 350px;
  align-items: center;
  padding: 10px;
  overflow-y: auto;
}

.problem-item {
  width: 650px;
  height: 70px;
  margin-bottom: 10px;
  padding: 5px 10px;
  position: relative;
}

.problem-item span {
  margin: 0 20px;
}

.badge {
  width: 100px;
  height: 30px;
  background-color: #ff8e8e;
  border-radius: 5px;
  margin-right: 20px;
}

.no,
.dash,
.name {
  position: absolute;
}

.no {
  left: 100px;
}

.dash {
  left: 210px;
}

.name {
  left: 240px;
}

.filter {
  width: 400px;
  justify-content: space-around;
  font-size: 30px;
}

.filter input[type="number"] {
  width: 50px;
  height: 30px;
  margin-left: 20px;
  margin-bottom: 2px;
  border: 2px solid black;
  border-radius: 5px;
  font-size: 22px;
  padding-left: 8px;
}

.select-btn {
  position: absolute;
  right: 10px;

  width: 60px;
  height: 30px;
  border-width: 3px;
  border-radius: 5px;
  border-color: #3b72ff;
  background-color: #9fbaff;
}

.selected-btn {
  width: 100%;
  height: 60px;
  margin-top: 10px;
  font-size: 30px;
  padding: 5px;
  border-radius: 5px;
  border-width: 5px;
  border-color: #ff8e8e;
  background-color: #ff4d4d;
}

.selected {
  background-color: #7e7e7e;
  border-color: #929191;
  color: #b5b5b5;
}

input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  -webkit-appearance: inner-spin-button;
  opacity: 1;
}
</style>
