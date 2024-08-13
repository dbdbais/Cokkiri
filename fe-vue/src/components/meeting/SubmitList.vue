<script setup>
import { useSubmitStore } from "@/stores/submit";
import { onMounted, ref } from "vue";
defineProps({
  roomData: Object,
});
const submitStore = useSubmitStore();
const shareData = ref([]);

onMounted(() => {
  shareData.value = JSON.parse(localStorage.getItem("shareData"));
});
</script>

<template>
  <div>
    <div class="box-w share-con" v-if="!roomData.isGame">
      <div class="share-list">
        <div v-for="(data, index) in shareData" :key="index">
          <button
            class="btn bold-text"
            @click="
              () => {
                submitStore.setCodeData(data);
              }
            "
          >
            {{ data.user }}
            <div class="sub-cnt md">{{ data.userCnt }}</div>
          </button>
        </div>
      </div>
    </div>

    <div class="box-w submit-con box-col" v-else>
      <div
        class="box submit-item flex-align"
        v-for="(submit, index) in submitStore.submitCodeList"
        :key="index"
      >
        <div class="box-row">
          <div class="problem-num bold-text box md">
            {{ submit.problemNum }}번 문제
          </div>
          <span class="bold-text name">{{ submit.nickname }}</span>
        </div>
        <span
          class="correct bold-text"
          :class="submit.correct ? 'success' : 'fail'"
          >{{ submit.correct ? "성공" : "실패" }}</span
        >
      </div>
    </div>
  </div>
</template>

<style scoped>
.submit-con,
.share-con {
  height: 700px;
  margin-top: 50px;
  border-radius: 0px 10px 10px 10px;
  padding: 20px;
  overflow-y: auto;
}

.share-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}
.submit-item {
  height: 80px;
  margin-bottom: 10px;
  position: relative;
  justify-content: space-between;
  padding: 20px;
}
.name {
  margin-left: 10px;
  font-size: 35px;
}
.correct {
  font-size: 35px;
}
.problem-num {
  padding: 5px 10px;
  background-color: #dbe7ff;
}
.fail {
  color: #ff020288;
}
.success {
  color: #70a40185;
}
.btn {
  background-color: #dbe7ff;
  padding: 5px 10px;
  border-radius: 10px;
  border-width: 5px;
  border-color: #3b72ff;
  margin: 10px;
  position: relative;
}
.sub-cnt {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: blue;
  top: -10px;
  left: -10px;
  position: absolute;
  font-size: 20px;
}
</style>
