<script setup>
import { ref } from "vue";
import { useModal } from "@/composables/useModal";
import ProblemDetail from "@/components/problem/modal/ProblemDetail.vue";
import { getAllReviews } from "@/api/review";

const {
  isModalOpen: isDetailModalOpen,
  openModal: openDetailModal,
  closeModal: closeDetailModal,
} = useModal();
const {
  isModalOpen: isReviewModalOpen,
  openModal: openReviewModal,
  closeModal: closeReviewModal,
} = useModal();

const reviewData = ref();
const props = defineProps({
  problemData: Object,
});

const imgTier = () => {
  const tier = tierImg();
  switch (tier) {
    case "bronze-1":
      return new URL("@/assets/tier/bronze-1.svg", import.meta.url).href;
    case "bronze-2":
      return new URL("@/assets/tier/bronze-2.svg", import.meta.url).href;
    case "bronze-3":
      return new URL("@/assets/tier/bronze-3.svg", import.meta.url).href;
    case "bronze-4":
      return new URL("@/assets/tier/bronze-4.svg", import.meta.url).href;
    case "bronze-5":
      return new URL("@/assets/tier/bronze-5.svg", import.meta.url).href;
    case "silver-1":
      return new URL("@/assets/tier/silver-1.svg", import.meta.url).href;
    case "silver-2":
      return new URL("@/assets/tier/silver-2.svg", import.meta.url).href;
    case "silver-3":
      return new URL("@/assets/tier/silver-3.svg", import.meta.url).href;
    case "silver-4":
      return new URL("@/assets/tier/silver-4.svg", import.meta.url).href;
    case "silver-5":
      return new URL("@/assets/tier/silver-5.svg", import.meta.url).href;
    case "gold-1":
      return new URL("@/assets/tier/gold-1.svg", import.meta.url).href;
    case "gold-2":
      return new URL("@/assets/tier/gold-2.svg", import.meta.url).href;
    case "gold-3":
      return new URL("@/assets/tier/gold-3.svg", import.meta.url).href;
    case "gold-4":
      return new URL("@/assets/tier/gold-4.svg", import.meta.url).href;
    case "gold-5":
      return new URL("@/assets/tier/gold-5.svg", import.meta.url).href;
  }
};

const tierImg = () => {
  var fileName = "";
  var tierNum = Math.floor((props.problemData.level - 1) / 5);

  if (tierNum == 0) fileName += "bronze";
  else if (tierNum == 1) fileName += "silver";
  else if (tierNum == 2) fileName += "gold";
  else if (tierNum == 3) fileName += "platinum";
  else if (tierNum == 4) fileName += "diamond";

  fileName += "-";

  var level = props.problemData.level - tierNum * 5;
  if (level == 1) fileName += "5";
  else if (level == 2) fileName += "4";
  else if (level == 3) fileName += "3";
  else if (level == 4) fileName += "2";
  else if (level == 5) fileName += "1";

  return fileName;
};

const reviews = async () => {
  // try {
  //   const response = await getAllReviews(props.problemData.no);
  //   console.log(response);
  //   reviewData.value = response.data;
  openReviewModal();
  // } catch (error) {
  //   console.log(error);
  // }
};
</script>

<template>
  <tr class="bold-text">
    <td>{{ props.problemData.no }}</td>
    <td @click="openDetailModal">
      {{ props.problemData.title }}
    </td>
    <td>
      <img :src="imgTier()" alt="티어" />
    </td>
    <td>{{ props.problemData.algoPercent.toFixed(2) }}%</td>
    <td>
      <!-- <span class="btn-review" @click="reviews"> -->
      <span class="btn-review" @click="openDetailModal"> 확인하기 </span>
    </td>
    <!-- <td v-if="problemData.review.result === 1">리뷰보기</td>
    <td v-else-if="problemData.review.try >= 3">리뷰보기</td>
    <td v-else>{{ problemData.review.try }} / 3 회 도전</td> -->
  </tr>
  <ProblemDetail
    v-if="isDetailModalOpen"
    @close="closeDetailModal"
    :problemData="problemData"
  />
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

.btn-review {
  background-color: #3b72ff;
  border-radius: 10px;
  padding: 5px 30px;
}
</style>
