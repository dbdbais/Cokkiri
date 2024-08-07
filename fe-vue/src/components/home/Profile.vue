<template>
    <div id="profile-container" class="box-p box-col">
        <div id="name" class="title main-title">김종덕</div>
        <div id="rank-container" class="box-row box-wb">
            <img id="rank-img" :src="imageSrc" alt="rank" class="rank" />
            <Exp id="rank-exp" :grade="grade" :percentage="percentage"></Exp>
        </div>
    </div>
</template>

<script setup>
import { computed } from 'vue';
import Exp from "@/components/home/Exp.vue";
import myRank from '@/assets/data/myRank.json';

const grade = myRank.myRank.grade;
const percentage = myRank.myRank.percentage;


const imageSrc = computed(() => {
    switch (grade) {
        case 0:
            return new URL('@/assets/rank/0-seed.svg', import.meta.url).href;
        case 1:
            return new URL('@/assets/rank/1-kiwi.svg', import.meta.url).href;
        case 2:
            return new URL('@/assets/rank/2-apple.svg', import.meta.url).href;
        case 3:
            return new URL('@/assets/rank/3-orange.svg', import.meta.url).href;
        case 4:
            return new URL('@/assets/rank/4-koreamelon.svg', import.meta.url).href;
        case 5:
            return new URL('@/assets/rank/5-durian.svg', import.meta.url).href;
        default:
            return new URL('@/assets/rank/0-seed.svg', import.meta.url).href;
    }
})
</script>

<style scoped>
#name {
    margin-bottom: 5px;
    font-size: 27px
}

#rank-img {
    width: 30px;
    height: 30px;
    margin: 10px 10px;
}

#rank-exp {
    height: 30px;
    margin: 10px 10px;
}
</style>
