<template>
    <header class="header">
        <div class="logo">쿠키리</div>
        <nav>
            <button>바로가기</button>
            <button>방만들기</button>
            <button>스터디 모집 게시판</button>
            <button>문제 목록</button>
        </nav>
        <div class="search">
            <input type="text" placeholder="검색" />
            <button>🔍</button>
        </div>
        <div class="notifications">
            <div>일일미션</div>
            <div>우편함</div>
        </div>
    </header>
</template>

<script>
export default {
    name: 'Header'
}
</script>

<style scoped>
.header {
    display: flex;
    justify-content: space-between;
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
