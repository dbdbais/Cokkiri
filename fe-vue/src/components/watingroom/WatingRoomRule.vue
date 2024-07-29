<script setup>
defineProps({
  rules: Array,
});
</script>

<template>
  <div class="rule-container box-col bold-text box">
    방 규칙
    <div class="rule-box box">
      <div class="rule box" v-for="rule in rules" :key="rule.index">
        {{ rule }}
      </div>
    </div>
  </div>
</template>

<style scoped>
.rule-container {
  width: 500px;
  height: 600px;
}
.rule-box {
  width: 450px;
  height: 500px;
}
.rule {
  width: 400px;
  height: 80px;
  -webkit-text-stroke: 2px black;
  background-color: #dbe7ff;
}
</style>
