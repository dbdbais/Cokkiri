<script setup>
defineProps({
  member: Object,
});
</script>
<template>
  <div class="member md bold-text" :id="member.num">
    {{ member.num }}
  </div>
</template>

<style scoped>
.member {
  width: 240px;
  height: 150px;
  border-radius: 10px;
  background-color: #d9d9d9;
}
</style>
