<template>
  <div class="title">
    <h2>Timer</h2>
    <hr />
    <h1>{{ minutes }}:{{ seconds }}</h1>
    <!-- <input v-model.number="inputMinutes" placeholder="분 설정" type="number" />
    <button @click="setTimer">Set Time</button>
    <button @click="startTimer" :disabled="!time || isActive">Start</button>
    <button @click="pauseTimer" :disabled="!isActive">Pause</button>
    <button @click="resetTimer">Reset</button> -->
  </div>
</template>

<script>
import { ref, computed, onBeforeUnmount, onMounted } from "vue";

export default {
  name: "GameTimer",
  setup() {
    const timer = ref(null);
    const time = ref(900);
    const inputMinutes = ref(0);
    const isActive = ref(false);

    const minutes = computed(() =>
      Math.floor(time.value / 60)
        .toString()
        .padStart(2, "0")
    );

    const seconds = computed(() =>
      (time.value % 60).toString().padStart(2, "0")
    );

    const setTimer = () => {
      time.value = inputMinutes.value * 60;
    };

    const startTimer = () => {
      if (time.value > 0 && !isActive.value) {
        isActive.value = true;
        timer.value = setInterval(() => {
          if (time.value > 0) {
            time.value--;
          } else {
            pauseTimer();
          }
        }, 1000);
      }
    };

    const pauseTimer = () => {
      isActive.value = false;
      clearInterval(timer.value);
    };

    const resetTimer = () => {
      pauseTimer();
      time.value = 10;
      inputMinutes.value = 10;
    };

    onBeforeUnmount(() => {
      clearInterval(timer.value);
    });
    onMounted(() => {
      startTimer();
    });
    return {
      timer,
      time,
      inputMinutes,
      isActive,
      minutes,
      seconds,
      setTimer,
      startTimer,
      pauseTimer,
      resetTimer,
    };
  },
};
</script>

<style scoped>
div {
  text-align: center;
  padding: 14px;
  border-radius: 10px;
  border: 5px solid #3b72ff;
}

button {
  margin: 5px;
}
input {
  margin-right: 10px;
}
</style>
