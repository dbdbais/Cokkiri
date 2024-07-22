<template>
    <header class="header">
        <nav>
            <button class="box-p">바로가기</button>
            <button class="box-p">방만들기</button>
            <RouterLink :to="{ name: 'studyBoard' }">스터디 모집 게시판</RouterLink>
            <RouterLink :to="{}">문제 목록</RouterLink>
        </nav>
        <div style="display: flex;">
            <div class="search">
                <input type="text" placeholder="검색" />
                <button>🔍</button>
            </div>
            <div class="notifications" style="display: flex;">
                <div>일일미션</div>
                <div>우편함</div>
            </div>
        </div>
    </header>
</template>

<script setup>
</script>

<style scoped>
.header {
    display: flex;
    justify-content: space-between;
    flex-direction: column;
    align-items: center;
    background-color: #b3ccff;
    padding: 10px;
}

.logo {
    font-size: 24px;
    font-weight: bold;
}

nav button {
    margin: 0 5px;
}

.search {
    display: flex;
    align-items: center;
}

.search input {
    margin-right: 5px;
}

.notifications div {
    margin: 0 10px;
}
</style>
