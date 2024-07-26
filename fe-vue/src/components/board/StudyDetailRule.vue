<template>
  <div class="box-main-group rule nomal-text md">
    {{ studyRule.rule }}
  </div>
</template>

<script setup>
defineProps({
  studyRule: Object,
});
</script>

<style scoped>
.rule {
  width: 90%;
  height: 40px;

  margin-top: 5px;
  font-size: 20px;
  color: white;
  -webkit-text-stroke: 1px black;
}
</style>
