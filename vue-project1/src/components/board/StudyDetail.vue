<script setup>
import RecruitmentBadge from "./RecruitmentBadge.vue";

defineProps({
  detailData: Object,
});
</script>

<template>
  <div class="study-detail down-modal">
    <RecruitmentBadge />
    {{ detailData }}
  </div>
</template>

<style scoped>
.study-detail {
  width: 600px;
  height: 600px;
  background-color: red;
  z-index: 1;
  position: absolute;
}
</style>
