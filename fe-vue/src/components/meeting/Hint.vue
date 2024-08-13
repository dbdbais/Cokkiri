<script setup>
import { useMeetingStore } from "@/stores/meeting";

const mStore = useMeetingStore();
</script>

<template>
  <div>
    <div class="box-w hint-list">
      <div class="hint-box nomal-text">
        <div v-for="hint in mStore.hints" :key="hint.no">
          <div class="box-row" style="justify-content: end">
            <div class="question md">{{ hint.no }}번 문제의 힌트를 줘</div>
          </div>
          <div class="answer">{{ hint.content }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.hint-list {
  height: 700px;
  margin-top: 50px;
  border-radius: 0px 10px 10px 10px;
  background-color: #cadcff;
  overflow-y: auto;
  padding-bottom: 20px;
}
.hint-box {
  font-size: 25px;
}

.question {
  height: 70px;
  padding: 20px;
  margin: 20px 40px;
  position: relative;
  background: #c191ff;
  border-radius: 0.4em;
}

.question:after {
  content: "";
  position: absolute;
  right: 0;
  top: 50%;
  width: 0;
  height: 0;
  border: 27px solid transparent;
  border-left-color: #c191ff;
  border-right: 0;
  border-bottom: 0;
  margin-top: -13.5px;
  margin-right: -27px;
}

.answer {
  width: 90%;
  margin-left: 40px;
  position: relative;
  background: #cfb0f8;
  padding: 20px;
  line-height: 1.5;
  border-radius: 0.4em;
}

.answer:after {
  content: "";
  position: absolute;
  left: 0;
  top: 50%;
  width: 0;
  height: 0;
  border: 28px solid transparent;
  border-right-color: #cfb0f8;
  border-left: 0;
  border-bottom: 0;
  margin-top: -13.5px;
  margin-left: -27px;
}
</style>
