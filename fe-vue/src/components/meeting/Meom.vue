<template>
  <div ref="container" :style="containerStyle">
    <!-- 첫 번째 조절 가능한 div -->
    <div
      :style="{
        width: firstWidth + 'px',
        height: '50%',
        background: 'lightblue',
      }"
    ></div>
    <!-- 크기 조절기 -->
    <div ref="resizer" :style="resizerStyle" @mousedown="initResize"></div>
    <!-- 두 번째 조절 가능한 div -->
    <div :style="{ flex: 1, height: '50%', background: 'lightgreen' }"></div>
  </div>
</template>

<script>
import { ref, onMounted, onBeforeUnmount } from "vue";

export default {
  setup() {
    const container = ref(null);
    const resizer = ref(null);
    const firstWidth = ref(200);

    const containerStyle = {
      display: "flex",
      width: "100%",
      height: "50vh",
      border: "1px solid black",
    };

    const resizerStyle = {
      width: "10px",
      background: "gray",
      cursor: "col-resize",
    };

    const initResize = (e) => {
      window.addEventListener("mousemove", startResize);
      window.addEventListener("mouseup", stopResize);
    };

    const startResize = (e) => {
      const containerRect = container.value.getBoundingClientRect();
      firstWidth.value = e.clientX - containerRect.left;
    };

    const stopResize = () => {
      window.removeEventListener("mousemove", startResize);
      window.removeEventListener("mouseup", stopResize);
    };

    onMounted(() => {
      resizer.value.addEventListener("mousedown", initResize);
    });

    onBeforeUnmount(() => {
      resizer.value.removeEventListener("mousedown", initResize);
    });

    return {
      container,
      resizer,
      firstWidth,
      containerStyle,
      resizerStyle,
      initResize,
    };
  },
};
</script>

<style scoped></style>
