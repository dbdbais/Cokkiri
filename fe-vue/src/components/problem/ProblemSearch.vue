<script setup>
import { ref, watch } from "vue";
import { getAllProblems, filterSearch, conditionSearch } from "@/api/problem";
import { problemStore } from "@/stores/problem";

const store = problemStore();
const emit = defineEmits(["clear-page"]);
const keyword = ref("");
const selectedTier = ref(0);

watch(selectedTier, (newTier, oldTier) => {
  if (selectedTier.value !== 0) {
    callFilterSearch();
  }
  console.log(newTier, oldTier);
});

const search = async () => {
  try {
    const response = await conditionSearch(keyword.value);
    store.setProblems(response.data);
    console.log(response);
  } catch (error) {
    console.log(error);
  }
};

const handleSelection = (tier) => {
  if (selectedTier.value === tier) {
    selectedTier.value = 0;
    callFilterSearch();
  } else {
    selectedTier.value = tier;
  }
  console.log(selectedTier.value);
};

const callFilterSearch = async () => {
  try {
    if (selectedTier.value === 0) {
      const response = await getAllProblems();
      store.setProblems(response.data);
    } else {
      const response = await filterSearch(selectedTier.value);
      store.setProblems(response.data);
    }
    emit("clear-page");
    console.log(response);
  } catch (error) {
    console.log(error);
  }
}
</script>

<template>
  <div class="search-con box-row">
    <div class="input-con box-row">
      <span class="title">문제</span>
      <input type="text" class="input" v-model="keyword" />
      <span class="title btn-search" @click="search">검색</span>
    </div>
    <div class="filter-con box-row">
      <span class="title">필터</span>
      <input id="bronze" type="radio" name="tier" :checked="selectedTier === 1" @click="handleSelection(1)" />
      <label class="title" for="bronze">브론즈</label>
      <input id="silver" type="radio" name="tier" :checked="selectedTier === 2" @click="handleSelection(2)" />
      <label class="title" for="silver">실버</label>
      <input id="gold" type="radio" name="tier" :checked="selectedTier === 3" @click="handleSelection(3)" />
      <label class="title" for="gold">골드</label>
      <input id="platinum" type="radio" name="tier" :checked="selectedTier === 4" @click="handleSelection(4)" />
      <label class="title" for="platinum">플래티넘</label>
      <input id="diamond" type="radio" name="tier" :checked="selectedTier === 5" @click="handleSelection(5)" />
      <label class="title" for="diamond">다이아몬드</label>
    </div>
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
  /* -webkit-text-stroke: 3px black; */
}

/* Font */
.input-con>span:nth-child(1),
.filter-con>span {
  font-size: 35px;
}

.filter-con label {
  font-size: 28px;
  -webkit-text-stroke: unset;
}


/* Input Container */
.input-con input {
  width: 300px;
  margin-left: 10px;
  border: 3px solid black;
  border-radius: 10px;
  height: 40px;
  font-size: 30px;
  font-weight: lighter;
}

/* Search Button  */
.btn-search {
  width: 80px;
  height: 40px;
  padding: 3px 0 0 0;
  margin-left: 10px;
  text-align: center;
  border: 2px solid #5b89ff;
  border-radius: 4px;
  background-color: #cfdbfb;
  font-size: 28px;
}

.btn-search:hover {
  background-color: #5b89ff;
  color: #fff;
}

/* Filter Container */

input[type="radio"]+label {
  border-width: 2px;
  transition: all 0.3s ease;
}

input[type="radio"]+label:hover {
  background-color: rgba(177, 219, 122, 0.2);
}

input[type="radio"]:checked+label {
  background-color: #000000;
  color: rgb(255, 255, 255);
  border-color: rgb(255, 255, 255);
}


.filter-con input {
  appearance: none;
}

.filter-con label {
  height: 40px;
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
</style>