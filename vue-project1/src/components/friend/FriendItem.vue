<script setup>
import { ref } from "vue";

const categoryOpen = ref(false);

const categoryStyle = ref("category-close");

const categoryChange = function () {
  categoryOpen.value = !categoryOpen.value;

  if (categoryOpen.value) {
    categoryStyle.value = "category-open";
  } else {
    categoryStyle.value = "category-close";
  }
};
</script>

<template>
  <div class="category-header"></div>

  <div class="category-friend" :class="categoryStyle">
    <div>아아아아아ㅏ</div>
    <div>아아아아아ㅏ</div>
    <div>아아아아아ㅏ</div>
    <div>아아아아아ㅏ</div>
  </div>
  <button @click="categoryChange">></button>
</template>

<style scoped>
.category-close {
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.5s ease;
}
.category-open {
  max-height: 200px;
  border-top: 1px solid silver;
}
</style>
