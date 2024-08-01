<script setup>
const props = defineProps({
  problemData: Object,
});

const tierImg = () => {
  const path = "/src/assets/tier/";
  var fileName = "";
  var tierNum = Math.floor((props.problemData.level - 1) / 5)

  if (tierNum == 0) fileName += "bronze";
  else if (tierNum == 1) fileName += "silver";
  else if (tierNum == 2) fileName += "gold";
  else if (tierNum == 3) fileName += "platinum";
  else if (tierNum == 4) fileName += "diamond";

  fileName += "-"

  var level = props.problemData.level - tierNum * 5;
  if (level == 1) fileName += "5";
  else if (level == 2) fileName += "4";
  else if (level == 3) fileName += "3";
  else if (level == 4) fileName += "2";
  else if (level == 5) fileName += "1";

  return path + fileName + ".svg";
};
</script>

<template>
  <tr class="bold-text">
    <td>{{ problemData.no }}</td>
    <td>
      {{ problemData.title }}
    </td>
    <td>
      <img :src="tierImg()" alt="티어" />
    </td>
    <td>{{ problemData.algoPercent }}</td>
    <td>리뷰보기</td>
    <!-- <td v-if="problemData.review.result === 1">리뷰보기</td>
    <td v-else-if="problemData.review.try >= 3">리뷰보기</td>
    <td v-else>{{ problemData.review.try }} / 3 회 도전</td> -->
  </tr>
</template>

<style scoped>
.bold-text {
  font-size: 25px;
  -webkit-text-stroke: 1.5px black;
}

td {
  /* padding: 1em 0.5em; */
  padding: 10px;
  vertical-align: middle;
}

td {
  text-align: center;
  border: 3px solid #3b72ff;
  background: #bed0fd;
}
</style>
