<template>
    <div class="progress-bar">
        <div class="progress" :style="{ width: percentage + '%' }">
            <span>{{ percentage }}%</span>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';

const percentage = ref(0);
</script>

<style scoped>
.progress-bar {
    width: 100%;
    background-color: #f0f8ff;
    /* Light blue background */
    border-radius: 8px;
    overflow: hidden;
}

.progress {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #ff9e3b;
    /* Orange background */
    color: white;
    font-weight: bold;
    border-radius: 8px;
    transition: width 0.5s ease;
}

span {
    padding: 10px;
    background-color: #ffbd73;
    /* Lighter orange for the percentage background */
    border-radius: 5px;
}
</style>