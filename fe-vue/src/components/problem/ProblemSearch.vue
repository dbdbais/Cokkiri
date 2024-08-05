<script setup>
import { ref } from "vue";
import { getProblems } from "@/api/problem";
import { problemStore } from "@/stores/problem";
import { extractProblems } from "@/utils/parse-problem";

const store = problemStore();

const keyword = ref("");

const search = () => {
  getProblems(parseInt(keyword.value))
    .then((response) => {
      console.log(response);
      store.setProblems(response.data);
    })
    .catch((error) => {
      console.log(error);
    });
};
</script>

<template>
  <div class="search-con box-row">
    <div class="input-con box-row">
      <span class="title">문제</span>
      <input type="text" class="input" v-model="keyword" />
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
    <span class="btn-search title" @click="search">검색</span>
  </div>
</template>

<style scoped>
/* Search COntainer */
.search-con {
  width: 1495px;
  height: 94px;
  justify-content: space-between;
  border: 5px solid #84a5f8;
  border-radius: 10px;
  background-color: #8220ff;
  padding: 25px 30px;
  -webkit-text-stroke: 3px black;
}

/* Font */

.input-con > span,
.filter-con > span {
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
  background-color: #fec346;
  border-color: #ac8531;
  color: #ac8531;
}

.filter-con label:nth-child(5) {
  background-color: #e7d0d0;
  border-color: #ab9999;
  color: #ab9999;
}

.filter-con label:nth-child(7) {
  background-color: #ffd700;
  border-color: #b69e21;
  color: #b69e21;
}

.filter-con label:nth-child(9) {
  background-color: #b8cee6;
  border-color: #a0b2c6;
  color: #8b9db0;
}

.filter-con label:nth-child(11) {
  background-color: #b9f2ff;
  border-color: #51c2dc;
  color: #51c2dc;
}

/* Search Button  */
.btn-search {
  background-color: #cfdbfb;
  border: 2px solid #5b89ff;
  width: 90px;
  text-align: center;
  border-radius: 4px;
  padding: 4px;
}

label {
  height: 40px;
}

.input {
  border: 3px solid black;
  border-radius: 10px;
  height: 40px;
  font-size: 30px;
  font-weight: lighter;
}

#app > div > div.problem-con > div.search-con.box-row > span {
  height: 40px;
}

.btn-search title {
  height: 40px;
}
</style>
