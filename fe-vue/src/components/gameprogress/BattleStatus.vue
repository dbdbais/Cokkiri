<script setup>
import { ref, onMounted } from 'vue';

const props = defineProps({
    url: String
})


</script>

<template>
    <div class="container">
        <div class="circle start"></div>
        <div class="line"></div>
        <div class="circle end"></div>
        <div :style="{ backgroundImage: `url(${url})` }" class="character"></div>
    </div>
</template>

<style scoped>
.container {
    position: relative;
    display: flex;
    align-items: center;
}

.circle {
    width: 100px;
    height: 100px;
    background-color: white;
    border-radius: 50%;
    z-index: 1;
}

.line {
    width: 1000px;
    height: 50px;
    background-color: white;
    margin: 0 10px;
}

.character {
    position: absolute;
    left: 0;
    width: 50px;
    height: 50px;
    /* background-image: url("game-character.svg"); */
    /* 여기에 캐릭터 이미지를 사용합니다 */
    background-size: cover;
    z-index: 2;
    animation: move 5s linear infinite;
}

@keyframes move {
    0% {
        transform: translateX(0);
    }

    100% {
        transform: translateX(1000px);
        /* 시작 원과 끝 원 사이 거리 */
    }
}
</style>