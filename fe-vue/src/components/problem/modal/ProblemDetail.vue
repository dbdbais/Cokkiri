<script setup>
import { ref, onMounted, computed } from "vue";
import { getAllReviews } from "@/api/review";
import ReviewItem from "@/components/problem/ReviewItem.vue";

const props = defineProps({
    problemData: Object
});
const reviewData = ref();
const currentPage = ref(1);

onMounted(async () => {
    try {
        const response = await getAllReviews(props.problemData.no);
        console.log(response);
        reviewData.value = response.data;
    } catch (e) {
        console.error(e);
    }

    // tmpReviewData.value = tmpReviewData.value.map((item) => {
    //     return {
    //         ...item,
    //         code: "문제를 해결하거나 3회 이상 코드를 제출해야 확인할 수 있습니다.",
    //         content: "문제를 해결하거나 3회 이상 코드를 제출해야 확인할 수 있습니다."
    //     }
    // });
})

const prevPage = function () {
    if (currentPage.value > 1) {
        currentPage.value -= 1;
    }
};

const nextPage = function () {
    if (currentPage.value < reviewData.value.length) {
        currentPage.value += 1;
    }
};

const currentReview = computed(() => {
    if (!reviewData.value || reviewData.value.length <= 0) return null;
    return reviewData.value[currentPage.value - 1];
});

const changeIndex = (index) => {
    currentPage.value = index;
};
</script>

<template>
    <div class="modal-overlay">
        <div class="modal-con">
            <div class="modal-header box-row">
                <div>
                    <span class="title header-span">{{ problemData.no + ". " }}</span>
                    <span class="title header-span">{{ problemData.title }}</span>
                </div>
                <div class="box-row">
                    <img src="@/assets/exit.svg" alt="close" class="icon-close" @click="$emit('close')" />
                </div>
            </div>
            <div class="modal-content box-grid">
                <div class="left-con">
                    <span v-html="problemData.info"></span>
                </div>
                <!-- <div class="btn-con">
                        <div class="btn-review" @click="fetchSubmit">
                            <span class="title main-title">리뷰하기</span>
                        </div>
                    </div> -->
                <div class="right-con">
                    <!-- <div class="overlay">
                        <span>문제를 해결하거나 3회 이상 제출해야합니다.</span>
                    </div> -->
                    <!-- <div v-if="!tmpReviewData" class="">
                        <span>아직 등록된 리뷰가 없습니다.</span>
                    </div> -->
                    <!-- <div v-for="review in tmpReviewData" :key="review.id"> -->
                    <ReviewItem v-if="currentReview" :review="currentReview" />
                    <!-- </div> -->
                    <div class="btn-pagi box-row">
                        <div class="btn-prev" @click="prevPage">◀</div>
                        <span v-if="reviewData" v-for="i in reviewData.length" style="font-size: 35px; font-weight: 800"
                            :class="{ currentIndex: i === currentPage }" @click="changeIndex(i)"> {{
                                i
                            }} </span>
                        <div class="btn-next" @click="nextPage">▶</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.title {
    color: white;
    font-family: "RixInooAriDuriR";
    -webkit-text-stroke: 1px black;
    font-size: 35px;
}

.modal-con {
    position: absolute;
    top: 60px;
    left: 180px;
    width: 1600px;
    height: 980px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: #DBE7FF;
    padding: 20px 30px;
}

.modal-header {
    justify-content: space-between;
    align-items: center;
}

.header-span {
    font-size: 55px;
    text-align: center;
}

.icon-close {
    width: 60px;
    margin-left: 20px;
}

.modal-content {
    margin-top: 30px;
    width: 100%;
    height: 100%;
}

.box-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-rows: 1fr;
    gap: 20px;
}

.left-con {
    width: 100%;
    height: 800px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: white;
    padding: 10px;
    overflow: auto;
}

.right-con {
    position: relative;
    width: 100%;
    height: 800px;
}

.overlay {
    position: absolute;
    color: white;
    top: 0;
    left: 00px;
    width: 755px;
    height: 800px;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 1000;
}

.overlay>span {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    text-align: center;

}

.currentIndex {
    color: #3B72FF;
}

.btn-pagi {
    position: absolute;
    bottom: 0;
    width: 100%;
    justify-content: space-between;
}

.btn-prev,
.btn-next {
    color: white;
    font-size: 25px;
    padding: 5px 50px;
    border: 3px solid #7397f5;
    border-radius: 10px;
    background-color: #5484fc;
}

.btn-next {
    margin-left: 25px;
}

.btn-prev:hover,
.btn-next:hover {
    border: 3px solid #3f5fae;
    background-color: #2d55ba;
}
</style>