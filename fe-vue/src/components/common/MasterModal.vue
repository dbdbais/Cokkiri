<script setup>
import { ref } from 'vue';
</script>

<template>
    <div class="master-con box-col">
        <div @click="$emit('close')">X</div>
        <div class="review-con">
            <H3>Review Feature</H3>
        </div>
    </div>
</template>

<style scoped>
.master-con {
    position: absolute;
    bottom: 0;
    width: 640px;
    height: 800px;
}
</style>