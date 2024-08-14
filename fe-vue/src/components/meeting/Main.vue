<script setup>
import Problem from "@/components/meeting/Problem.vue";
import Compiler from "@/components/meeting/Compiler.vue";
import SubmitList from "@/components/meeting/SubmitList.vue";
import Hint from "@/components/meeting/Hint.vue";

import { ref } from "vue";

defineProps({
  blind: Number,
  minimum: Number,
  prevent: Boolean,
  bigfont: Boolean,
  roomData: Object,
});

const emit = defineEmits(["submit-code"]);

const main = ref(null);
const resizer = ref(null);
const problemWidth = ref(1000);
const selectedButton = ref("compiler");
const userCnt = ref(1);
const compiler = ref(true);

const initResize = (e) => {
  window.addEventListener("mousemove", startResize);
  window.addEventListener("mouseup", stopResize);
};
const submitCode = (data) => {
  console.log(data);
  emit("submit-code", data);
};
const startResize = (e) => {
  const mainRect = main.value.getBoundingClientRect();
  problemWidth.value = e.clientX - mainRect.left;
  if (problemWidth.value < 800) {
    problemWidth.value = 800;
  } else if (problemWidth.value > 1100) {
    problemWidth.value = 1100;
  }
};

const stopResize = () => {
  window.removeEventListener("mousemove", startResize);
  window.removeEventListener("mouseup", stopResize);
};

const selectButton = (button) => {
  selectedButton.value = button;
};
</script>

<template>
  <div ref="main" class="flex-align main-content">
    <div
      :style="{
        width: problemWidth + 'px',
      }"
      class="problem box-sb"
    >
      <Problem :blind="blind" />
    </div>
    <button ref="resizer" class="resizer" @mousedown="initResize"></button>
    <div
      class="compiler box-sb"
      :style="{
        flex: 1,
      }"
    >
      <div class="flex-align btn-box">
        <div
          class="compiler-btn bold-text md"
          :class="{ active: selectedButton === 'compiler' }"
          @click="selectButton('compiler')"
        >
          에디터
        </div>
        <div
          v-if="roomData.isGame"
          class="submit-btn bold-text md"
          :class="{ active: selectedButton === 'submit' }"
          @click="selectButton('submit')"
        >
          제출 목록
        </div>
        <div
          v-else
          class="submit-btn bold-text md"
          :class="{ active: selectedButton === 'submit' }"
          @click="selectButton('submit')"
        >
          공유 목록
        </div>
        <div
          class="hint-btn bold-text md"
          :class="{ active: selectedButton === 'hint' }"
          @click="selectButton('hint')"
        >
          힌트
        </div>
      </div>
      <Compiler
        v-if="selectedButton === 'compiler'"
        :bigfont="bigfont"
        :minimum="minimum"
        :prevent="prevent"
        :room-data="roomData"
        :user-cnt="userCnt"
        @submit-code="submitCode"
      />
      <SubmitList
        v-else-if="selectedButton === 'submit'"
        :room-data="roomData"
      />
      <Hint v-else-if="selectedButton === 'hint'" />
    </div>
  </div>
</template>

<style scoped>
.main-content {
  margin: 15px;
}

.problem {
  height: 800px;
}

.change {
  border-width: 5px;
  border-radius: 10px;
  border-color: #3b72ff;
  background-color: #c191ff;
  position: absolute;
  top: 10px;
  right: 20px;
}

.editor-box {
  position: relative;
}

.compiler-btn,
.submit-btn,
.hint-btn {
  width: 120px;
  height: 50px;
  border: 5px solid #3b72ff;
  font-size: 25px;
  border-radius: 10px 10px 0 0;
}

.submit {
  width: 120px;
  height: 60px;
  position: absolute;
  top: 10px;
  transition: all 0.8s ease-in-out;

  border-radius: 10px;
  border-width: 5px;
  background-color: #c191ff;
  border-color: #3b72ff;
}

.compiler {
  height: 800px;
  padding: 20px;
  position: relative;
}

.btn-box {
  position: absolute;
  top: 25px;
}

.active {
  background-color: blue;
}

.resizer {
  width: 20px;
  height: 800px;
  background-color: skyblue;
  border-color: white;
  cursor: url(/src/assets/drag.svg) 20 20, pointer;
}
</style>
