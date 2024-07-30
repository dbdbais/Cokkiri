<script setup>
import { RouterView } from "vue-router";
import Loading from "./components/common/Loading.vue";
import { useLodingStore } from "./stores/loading";

const loadingStore = useLodingStore();
</script>

<template>
  <Loading v-if="loadingStore.isLoading" />
  <RouterView />
</template>

<style></style>
