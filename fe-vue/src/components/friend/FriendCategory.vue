<script setup>
import FriendItem from "@/components/friend/FriendItem.vue";
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
  <div>
    <div class="category-header">친구 목록</div>
    <button @click="categoryChange">+</button>
  </div>
  <div class="category-friend" :class="categoryStyle">
    <FriendItem v-for="i in 6" :key="i" :my-friend="i" />
  </div>
</template>

<style scoped>
* {
  list-style: none;
}
.category-close {
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.5s ease;
}
.category-open {
  animation: open 0.5s ease-in-out;
}

@keyframes open {
  from {
    max-height: 0;
  }
  to {
    max-height: 200px;
  }
}
</style>
