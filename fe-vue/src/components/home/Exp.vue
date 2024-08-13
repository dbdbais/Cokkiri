<template>
    <div class="progress-bar" :style="{ backgroundColor: pgbarColor }">
        <span>{{ percentage }}%</span>
        <div class="progress" :style="{ width: percentage + '%', backgroundColor: pgColor }">
        </div>
    </div>
</template>

<script setup>
import { computed } from 'vue';
const props = defineProps({
    percentage: Number,
    tier: Number
});

const pgbarColor = computed(() => {
    switch (props.tier) {
        case 0:
            return '#ff9e3b';
        case 1:
            return '#ff9e3b';
        case 2:
            return '#ff9e3b';
        case 3:
            return '#FFC997';
        case 4:
            return '#ff9e3b';
        case 5:
            return '#ff9e3b';
        default:
            return '#ff9e3b';
    }
});


const pgColor = computed(() => {
    switch (props.tier) {
        case 0:
            return '#ff9e3b';
        case 1:
            return '#ff9e3b';
        case 2:
            return '#FFC997';
        case 3:
            return '#F9973E';
        case 4:
            return '#ff9e3b';
        case 5:
            return '#ff9e3b';
        default:
            return '#ff9e3b';
    }
});
</script>

<style scoped>
.progress-bar {
    position: relative;
    width: 100%;
    border-radius: 8px;
    overflow: hidden;
}

.progress {
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: bold;
    height: 100%;
    border-radius: 8px;
    transition: width 0.5s ease;
}

span {
    position: absolute;
    left: 90px;
    color: white;
    font-size: 20px;
    margin-top: 4px;
    border-radius: 5px;
    font-family: "RixInooAriDuriR";
    -webkit-text-stroke: 1px #F9973E;
}
</style>