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
    <div class="box-w submit-list">
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
</template>

<style scoped>
.submit-list {
  height: 700px;
  margin-top: 50px;
  border-radius: 0px 10px 10px 10px;
  padding: 20px;
  display: flex;
  flex-wrap: wrap;
}

.btn {
  background-color: #dbe7ff;
  width: 120px;
  height: 50px;
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
