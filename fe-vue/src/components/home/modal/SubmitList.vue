<script setup>
import { ref, onBeforeMount } from "vue";
import { getFilteredSubmit } from "@/api/submit";
import { userStore } from "@/stores/user";
import WriteReview from "@/components/home/WriteReview.vue";

const props = defineProps({
    problemId: Number,
});
const submitData = ref();
const uStore = userStore();
const currentReviewIndex = ref(0);

onBeforeMount(async () => {
    try {
        const response = await getFilteredSubmit(uStore.user.id, props.problemId);
        console.log(response);
        submitData.value = response.data;
    } catch (error) {
        console.error(error);
    }
})

const movePrev = () => {
    if (currentReviewIndex.value > 0) {
        currentReviewIndex.value--;
    }
}

const moveNext = () => {
    if (currentReviewIndex.value < submitData.value.length - 1) {
        currentReviewIndex.value++;
    }
}
</script>

<template>
    <div class="modal-overlay">
        <div class="modal-con">
            <div class="modal-header box-row">
                <span class="title main-title header-span">리뷰작성</span>
                <img src="@/assets/exit.svg" alt="close" class="icon-close" @click="$emit('close')" />
            </div>
            <div class="box-row modal-content">
                <WriteReview v-if="submitData" :problem-id="problemId" :submit="submitData[currentReviewIndex]"
                    :index="currentReviewIndex + 1" class="code-list" />
                <!-- <WriteReview v-for="(submit, index) in submitData" :problem-id="problemId" :submit="submit"
                    :index="index + 1" class="code-list" /> -->
            </div>
            <button v-if="currentReviewIndex > 0" class="prev-btn" @click="movePrev">
                <img src="@/assets/arrow_left_alt.svg" alt="prev" />
            </button>
            <button v-if="currentReviewIndex < submitData.length - 1" class="next-btn" @click="moveNext">
                <img src="@/assets/arrow_right_alt.svg" alt="next" />
            </button>
        </div>
    </div>
</template>

<style scoped>
.modal-overlay {
    background-color: rgba(0, 0, 0, 0.4) !important;
    top: 0 !important;
    left: 0 !important;
    width: 1920px !important;
    height: 1080px !important;
}

.modal-con {
    position: absolute;
    top: 525px;
    right: 125px;
    width: 650px;
    height: 550px;
    border: 3px solid #3B72FF;
    border-radius: 10px;
    background-color: #DBE7FF;
    padding: 20px 30px 0 20px;
}

.modal-header {
    justify-content: space-between;
    align-items: center;
}

.header-span {
    font-size: 40px;
}

.modal-content {
    justify-content: center;
    margin-top: 10px;
    width: 600px;
    height: 400px;
    overflow-y: auto;
    overflow-x: hidden;
}

.icon-close {
    width: 40px;
}

.input-con span {
    font-size: 30px;
}

.code-con {
    height: 210px;
}

.code {
    overflow: auto;
    height: 190px;
}

.review-con {
    height: 250px;
    margin-top: 20px;
}

textarea {
    width: 100%;
    height: 200px;
}

.btn-con {
    justify-content: space-between;
}

.btn {
    width: 120px;
    padding: 10px 0;
    text-align: center;
    border-radius: 10px;
}

.btn:nth-child(1) {
    background-color: #69f0aa;
}

.btn:nth-child(1):hover {
    background-color: #5be09c;
}

.btn:nth-child(2) {
    background-color: #6969f0;
}

.btn:nth-child(2):hover {
    background-color: #5b5be0;
}

.btn span {
    font-size: 25px;
}

.code-list {
    margin-bottom: 5px;
}

.prev-btn {
    position: absolute;
    bottom: 5px;
    left: 50px;
    background-color: transparent;
    color: #3B72FF;
    border: none;
}

.next-btn {
    position: absolute;
    bottom: 5px;
    right: 50px;
    background-color: transparent;
    color: #3B72FF;
    border: none;
}

.prev-btn>img,
.next-btn>img {
    width: 50px;
}
</style>