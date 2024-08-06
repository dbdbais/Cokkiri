<script setup>
defineProps({
  currentPage: Number,
});

const emit = defineEmits(["change-page"]);

const pageChange = function (motion) {
  emit("change-page", motion);
};
</script>

<template>
  <div class="page box-row">
    <button class="nomal-text" @click="pageChange(0)"><</button>
    <div class="nomal-text page-num">{{ currentPage }}</div>
    <button class="nomal-text" @click="pageChange(1)">></button>
  </div>
</template>

<style scoped>
.page {
  width: 200px;
  justify-content: space-between;
  align-items: center;

  position: absolute;
}
.page-num {
  margin: 0px 20px;
}
.page > button {
  width: 60px;
  height: 60px;

  border-radius: 5px;
  border-width: 5px;
  border-color: #3b72ff;

  background-color: #c191ff;
  color: aqua;
}
</style>
