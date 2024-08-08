<script setup>
defineProps({
  rules: Array,
});
</script>

<template>
  <div class="rule-container box-col bold-text box">
    방 규칙
    <div class="rule-box box box-col text-font">
      <div class="rule box" v-for="rule in rules" :key="rule.index">
        {{ rule }}
      </div>
    </div>
  </div>
</template>

<style scoped>
.rule-container {
  width: 660px;
  height: 600px;
  padding: 10px 20px;
}
.rule-box {
  width: 610px;
  height: 600px;
  margin-top: 10px;
  align-items: center;
}
.rule {
  width: 560px;
  height: 80px;
  display: flex;
  align-items: center;
  padding-left: 20px;
  margin-top: 20px;
  background-color: #dbe7ff;
}
</style>
