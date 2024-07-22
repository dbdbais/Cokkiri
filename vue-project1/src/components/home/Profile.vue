<template>
    <div id="profile-container" class="box-p box-col">
        <div id="name" class="title main-title">김종덕</div>
        <div id="rank-container" class="box-row box-wb">
            <img id="rank-img" src="@/assets/rank/orange.svg" alt="rank" class="rank" />
            <Exp id="rank-exp"></Exp>
        </div>
    </div>
</template>

<script setup>
import Exp from "@/components/home/Exp.vue";
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
