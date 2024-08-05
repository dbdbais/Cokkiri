<script setup>
import Logo from "@/components/common/Logo.vue";
import ProblemList from "@/components/problem/ProblemList.vue";
import ProblemSearch from "@/components/problem/ProblemSearch.vue";
import NonNumberPagination from "@/components/common/NonNumberPagination.vue";
</script>

<template>
    <Logo />
    <div class="main-con box-col">
        <div class="header">
            <span class="title">문제 목록</span>
        </div>
        <div class="problem-con">
            <ProblemSearch />
            <ProblemList />
            <NonNumberPagination />
        </div>
    </div>
</template>

<style scoped>
.main-con {
    display: flex;
    width: 1920px;
    height: 1080px;
    justify-content: center;
    align-items: center;
}

.header {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 1920px;
    height: 130px;
    font-size: 60px;
    text-align: center;
}

.problem-con {
    width: calc(1920px - 250px);
    height: calc(1080px - 180px);
    background-color: #BED0FD;
    border: 8px solid #3B72FF;
    border-radius: 10px;
    padding: 50px 80px;
}
</style>