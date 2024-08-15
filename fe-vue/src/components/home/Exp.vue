<template>
  <div class="progress-bar" :style="{ backgroundColor: pgbarColor(tier) }">
    <span
      :style="{
        '-webkit-text-stroke': `1.5px ${pgColor(tier)}`,
      }"
      >{{ score }}%</span
    >
    <div
      class="progress"
      :style="{ width: score + '%', backgroundColor: pgColor(tier) }"
    ></div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import { userStore } from "@/stores/user";
const props = defineProps({
  percentage: Number,
  tier: String,
});

const uStore = userStore();

const score = computed(() => {
  return uStore.user.score % 100;
});

function pgbarColor(tier) {
  console.log(tier);
  switch (tier) {
    case "SEED":
      return "#A4C400";
    case "KIWI":
      return "#9CCD6D";
    case "APPLE":
      return "#FF8E9E";
    case "ORANGE":
      return "#F9973E";
    case "KOREAMELON":
      return "#F5DEB6";
    case "DURIAN":
      return "#18C08B";
    default:
      return "#ff9e3b";
  }
}

function pgColor(tier) {
  console.log(tier);
  switch (tier) {
    case "SEED":
      return "#91562D";
    case "KIWI":
      return "#C97C48";
    case "APPLE":
      return "#EA5B70";
    case "ORANGE":
      return "#F4731C";
    case "KOREAMELON":
      return "#F0C863";
    case "DURIAN":
      return "#FFB357";
    default:
      return "#ff9e3b";
  }
}
</script>

<style scoped>
.progress-bar {
  position: relative;
  width: 100%;
  border-radius: 8px;
  overflow: hidden;
}

.progress {
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  height: 100%;
  border-radius: 8px;
  transition: width 0.5s ease;
}

span {
  position: absolute;
  left: 90px;
  color: white;
  font-size: 20px;
  margin-top: 4px;
  border-radius: 5px;
  font-family: "RixInooAriDuriR";
  -webkit-text-stroke: 1px #f9973e;
}
</style>
