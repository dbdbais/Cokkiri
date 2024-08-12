<script setup>
import { RouterView } from "vue-router";
import { useLodingStore } from "./stores/loading";
import Loading from "./components/common/Loading.vue";

const loadingStore = useLodingStore();
</script>

<template>
  <Loading v-if="loadingStore.isLoading" />
  <RouterView />
</template>

<style></style>
