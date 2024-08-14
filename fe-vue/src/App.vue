<script setup>
import { ref, watchEffect, onMounted } from "vue";
import { RouterView } from "vue-router";
import { useLodingStore } from "./stores/loading";
import Loading from "./components/common/Loading.vue";
import ViewportCheck from "@/components/common/ViewportCheck.vue";
import { checkViewport } from "@/composables/check-viewport";

const loadingStore = useLodingStore();
const isValidViewport = ref(false);
const { check, update } = checkViewport();

onMounted(() => {
  window.addEventListener("resize", update);
});

watchEffect(() => {
  isValidViewport.value = check();
});

</script>

<template>
  <ViewportCheck v-if="!isValidViewport.isValid" :msg="isValidViewport.msg" />
  <div v-else>
    <Loading v-if="loadingStore.isLoading" />
    <RouterView />
  </div>
</template>

<style></style>
