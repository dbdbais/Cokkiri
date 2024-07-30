<template>
  <div ref="main" class="flex-align">
    <div
      :style="{
        width: problemWidth + 'px',
      }"
      class="problem"
    ></div>
    <button ref="resizer" class="resizer" @mousedown="initResize"></button>
    <div
      class="compiler"
      :style="{
        flex: 1,
      }"
    >
      <div class="editor"></div>
      <div class="input"></div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const main = ref(null);
const resizer = ref(null);
const problemWidth = ref(1300);

const initResize = (e) => {
  window.addEventListener("mousemove", startResize);
  window.addEventListener("mouseup", stopResize);
};

const startResize = (e) => {
  const mainRect = main.value.getBoundingClientRect();
  problemWidth.value = e.clientX - mainRect.left;
};

const stopResize = () => {
  window.removeEventListener("mousemove", startResize);
  window.removeEventListener("mouseup", stopResize);
};
</script>

<style scoped>
.problem {
  height: 800px;

  background-color: red;
}
.compiler {
  height: 800px;
  background-color: blue;
}
.input {
  width: 100%;
  background-color: gold;
  height: 200px;
}
.editor {
  width: 100%;
  background-color: aqua;
  height: 500px;
}
.resizer {
  width: 10px;
  height: 800px;
  background-color: skyblue;
  border-color: white;
  cursor: pointer;
}
</style>
