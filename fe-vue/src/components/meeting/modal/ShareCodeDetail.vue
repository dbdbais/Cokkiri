<script setup>
import { useSubmitStore } from "@/stores/submit";

defineProps();

const submitStore = useSubmitStore();
</script>

<template>
  <div class="box share-box box-col slideDown">
    <img
      src="/src/assets/exit.svg"
      alt="닫기"
      class="exit"
      @click="submitStore.close()"
    />
    <div class="user-name bold-text">
      {{ submitStore.codeData.user }}
    </div>
    <div class="box share-content box-col">
      <div class="box-row top">
        <div class="problem-no box md bold-text">
          {{
            submitStore.codeData.problemNo === "문제 없음"
              ? "문제 없음"
              : `문제 ${submitStore.codeData.problemNo}번`
          }}
        </div>
        <div class="lang box md bold-text">
          {{ submitStore.codeData.language }}
        </div>
      </div>
      <div class="box code">
        <highlightjs
          :language="submitStore.codeData.language"
          :code="submitStore.codeData.code"
        ></highlightjs>
      </div>
    </div>
  </div>
</template>

<style scoped>
.share-box {
  width: 900px;
  height: 650px;
  z-index: 5;
  background: #c191ff;
  position: absolute;
  left: 500px;
  top: 200px;
}
.share-content {
  width: 850px;
  height: 540px;
  margin-left: 20px;
  background-color: #dbe7ff;
}
.user-name {
  font-size: 35px;
  margin: 20px;
}
.top {
  align-items: center;
  margin-left: 20px;
}
.problem-no {
  width: 100px;
  height: 50px;
  background-color: #ff8e8e;
  border-color: #ff4d4d;
}
.lang {
  width: 120px;
  height: 50px;
  margin: 20px;
  font-size: 25px;
  background-color: #c191ff;
}
.code {
  width: 810px;
  height: 420px;
  margin-left: 15px;
  overflow-y: auto;
}
pre {
  border: 1px;
}
.hljs span {
}
</style>
