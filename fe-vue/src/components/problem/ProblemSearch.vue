<script setup>
</script>

<template>
    <div class="search-con box-row">
        <div class="input-con box-row">
            <span class="title">문제</span>
            <input type="text">
        </div>
        <div class="filter-con box-row">
            <span class="title">필터</span>
            <input id="bronze" type="radio" name="tier" />
            <label class="title" for="bronze">브론즈</label>
            <input id="silver" type="radio" name="tier" />
            <label class="title" for="silver">실버</label>
            <input id="gold" type="radio" name="tier" />
            <label class="title" for="gold">골드</label>
            <input id="platinum" type="radio" name="tier" />
            <label class="title" for="platinum">플래티넘</label>
            <input id="diamond" type="radio" name="tier" />
            <label class="title" for="diamond">다이아몬드</label>
        </div>
        <span class="btn-search title">검색</span>
    </div>
</template>

<style scoped>
/* Search COntainer */
.search-con {
    width: 1495px;
    height: 94px;
    justify-content: space-between;
    border: 2px solid #84A5F8;
    border-radius: 6px;
    background-color: #8220FF;
    padding: 25px 30px;
    -webkit-text-stroke: 3px black;
}

/* Font */

.input-con>span,
.filter-con>span {
    font-size: 35px;
}

.filter-con label {
    font-size: 28px;
    -webkit-text-stroke-color: none;
}

.btn-search {
    font-size: 32px;
}

/* Input Container */
.input-con input {
    width: 300px;
    margin-left: 10px;
}

/* Filter Container */

.filter-con input {
    appearance: none;
}

.filter-con label {
    margin-left: 12px;
    padding: 2px 4px;
    border-radius: 5px;
    border-width: 4px;
    border-style: solid;
}

.filter-con label:nth-child(3) {
    background-color: #FEC346;
    border-color: #AC8531;
    color: #AC8531;
}

.filter-con label:nth-child(5) {
    background-color: #E7D0D0;
    border-color: #AB9999;
    color: #AB9999;
}

.filter-con label:nth-child(7) {
    background-color: #FFD700;
    border-color: #B69E21;
    color: #B69E21;
}

.filter-con label:nth-child(9) {
    background-color: #B8CEE6;
    border-color: #A0B2C6;
    color: #8B9DB0;
}

.filter-con label:nth-child(11) {
    background-color: #B9F2FF;
    border-color: #51C2DC;
    color: #51C2DC;
}


/* Search Button  */
.btn-search {
    background-color: #CFDBFB;
    border: 2px solid #5B89FF;
    width: 90px;
    text-align: center;
    border-radius: 4px;
    padding: 4px;
}
</style>