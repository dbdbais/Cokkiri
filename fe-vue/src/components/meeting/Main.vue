<script setup>
import Problem from "@/components/meeting/Problem.vue";
import Compiler from "@/components/meeting/Compiler.vue";
import SubmitList from "@/components/meeting/SubmitList.vue";

import { ref } from "vue";
import { RouterView } from "vue-router";

const main = ref(null);
const resizer = ref(null);
const problemWidth = ref(1300);
const compiler = ref(0);

const compilerText = ["에디터", "제출 목록"];

const changCompilerView = function () {
  if (compiler.value === 0) {
    compiler.value = 1;
  } else {
    compiler.value = 0;
  }
};

const initResize = (e) => {
  window.addEventListener("mousemove", startResize);
  window.addEventListener("mouseup", stopResize);
};

const startResize = (e) => {
  const mainRect = main.value.getBoundingClientRect();
  problemWidth.value = e.clientX - mainRect.left;
  if (problemWidth.value < 500) {
    problemWidth.value = 500;
  } else if (problemWidth.value > 1500) {
    problemWidth.value = 1500;
  }
};

const stopResize = () => {
  window.removeEventListener("mousemove", startResize);
  window.removeEventListener("mouseup", stopResize);
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
      <Problem />
    </div>
    <button ref="resizer" class="resizer" @mousedown="initResize"></button>
    <div
      class="compiler box-sb"
      :style="{
        flex: 1,
      }"
    >
      <button class="btn bold-text change" @click="changCompilerView">
        {{ compilerText[compiler] }}
      </button>
      <Compiler v-if="compiler === 0" />
      <SubmitList v-else />
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
.compiler {
  height: 800px;
  padding: 70px 20px 20px 20px;
  position: relative;
}

.resizer {
  width: 20px;
  height: 800px;
  background-color: skyblue;
  border-color: white;
  cursor: url(/src/assets/drag.svg) 20 20, pointer;
}
</style>
