<template>
    <header>
        <nav id="header-first" class="title main-title">
            <div id="quick-start" class="box-main-con box-in nav-btn">바로가기</div>
            <div id="create-room" class="box-main-con box-in nav-btn">방만들기</div>
            <RouterLink id="router-regular" class="header-router" :to="{ name: 'studyBoard' }">스터디 모집 게시판</RouterLink>
            <RouterLink id="router-problem" class="header-router" :to="{}">문제 목록</RouterLink>
        </nav>
        <div id="header-second" class="box-row">
            <div id="search-container" class="box-row box-main-con">
                <span class="title main-title">검색</span>
                <input type="text" />
                <button id="search-btn">🔍</button>
            </div>
            <div class="notifications box-row">
                <div class="box-main-noti noti-btn">
                    <img id="mission-icon" src="@/assets/elephant-circus.svg">
                    일일미션
                </div>
                <div class="box-main-noti noti-btn">
                    <img id="mail-icon" src="@/assets/mail.svg">
                    우편함
                </div>
            </div>
        </div>
    </header>
</template>

<script setup>
</script>

<style scoped>
/* header 첫 번째 영역 */
#header {
    justify-content: space-between;
    align-items: start;
}

.nav-btn {
    padding: 8px 20px;
    border-radius: 20px;
}

#quick-start {
    margin-right: 20px;
}

#create-room {
    margin-right: 40px;

}

#router-regular {
    margin-right: 30px;
}

.header-router {
    position: relative;
    display: inline-block;
}

.header-router::after {
    content: '';
    position: absolute;
    left: 0;
    right: 0;
    bottom: -1px;
    height: 1px;
    /* background: black; */
    /* transform: skewY(-10deg); */
    background: repeating-linear-gradient(to right,
            black,
            black 2px,
            transparent 2px,
            transparent 4px);
}

/* header 두 번째 영역 */

#search-container {
    position: relative;
    align-items: center;
    width: 500px;
    height: 52px;
    padding: 6px 20px;
    padding-right: 0;
    margin-right: 20px;
}

#search-container input {
    width: 400px;
    height: 28px;
    margin-right: 5px;
    border: 1px solid #ffffff;
    border-radius: 4px;
}

#search-container span {
    font-size: 26px;
    margin-right: 10px;
}

#search-btn {
    position: absolute;
    right: 20px;
    background: none;
    border: none;
}

.notifications div {
    margin-right: 10px;
}

.noti-btn {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 150px;
    border-radius: 25px;
    color: #5BB5D9;
    font-size: 20px;
    font-family: yg-jalnan;
}

#mission-icon {
    width: 30px;
    height: 30px;
    margin-right: 5px;
}

#mail-icon {
    width: 30px;
    height: 30px;
    margin-right: 5px;
}
</style>
