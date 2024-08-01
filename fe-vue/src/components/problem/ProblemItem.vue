<script setup>
defineProps({
  problemData: Object,
});

const tierImg = "/src/assets/tier/";
</script>

<template>
  <tr class="bold-text">
    <td>{{ problemData.no }}</td>
    <td>
      {{ problemData.problemName }}
    </td>
    <td>
      <img
        :src="
          tierImg +
          problemData.difficulty.tier +
          '-' +
          problemData.difficulty.level +
          '.svg'
        "
        alt="티어"
      />
    </td>
    <td>{{ problemData.answerPercentage }}</td>
    <td v-if="problemData.review.result === 1">리뷰보기</td>
    <td v-else-if="problemData.review.try >= 3">리뷰보기</td>
    <td v-else>{{ problemData.review.try }} / 3 회 도전</td>
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
