<script setup>
import { infoSplit } from "@/utils/parse-problem";
import { useModal } from "@/composables/useModal";
import WriteReview from "@/components/problem/modal/WriteReview.vue";

const { isModalOpen, openModal, closeModal } = useModal();
defineProps({
    problemData: Object
});
</script>

<template>
    <div class="modal-overlay">
        <div class="main-con box-col">
            <div class="modal-header box-row">
                <div>
                    <span class="title header-span">{{ problemData.no + ". " }}</span>
                    <span class="title header-span">{{ problemData.title }}</span>
                </div>
                <div class="box-row">
                    <div>
                    </div>
                    <img src="@/assets/exit.svg" alt="close" class="icon-close" @click="$emit('close')" />
                </div>
            </div>
            <div class="modal-content">
                <div class="left-content">
                    <div class="problem-con">
                        <span v-html="problemData.info"></span>
                        <!-- <span class="content">{{ infoSplit(problemData.info) }}</span> -->
                    </div>
                </div>
            </div>
            <div class="btn-con">
                <div class="btn-review" @click="openModal">
                    <span class="title main-title">리뷰하기</span>
                </div>
            </div>
        </div>
    </div>
    <WriteReview v-if="isModalOpen" @close="closeModal" :problem-id="problemData.no" />
</template>

<style scoped>
.title {
    color: white;
    font-family: "RixInooAriDuriR";
    -webkit-text-stroke: 1px black;
    font-size: 35px;
}

.content {
    color: black;
    font-family: "RixInooAriDuriR";
    -webkit-text-stroke: 1px white;
}

.main-con {
    position: absolute;
    top: 150px;
    left: 20px;
    width: 850px;
    height: 800px;
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
}

.modal-content {
    margin-top: 30px;
    width: 100%;
    height: 100%;
}

.left-content {
    width: 100%;
    height: 100%;
}

.right-content {
    width: 100%;
    height: 100%;
}

.right-grid {
    display: grid;
    grid-template-rows: 2fr 2fr 1.5fr 1.25fr 1fr;
    gap: 10px;
}

.problem-con,
.input-con,
.output-con,
.restrict-con,
.category-con {
    width: 100%;
    border: 5px solid #3B72FF;
    /* border-radius: 10px; */
    background-color: white;
    padding: 10px;
    overflow: auto;
}

.problem-con {
    height: 560px;
}

.btn-con {
    text-align: center;
}

.btn-review {
    border: 5px solid #1959FF;
    border-radius: 10px;
    background-color: #C191FF;
    padding: 10px;
}

.btn-like {
    border: 5px solid #7FB115;
    border-radius: 10px;
    background-color: #CCFF61;
    padding: 10px;
}

.btn-review img {
    width: 50px;
    height: 50px;
}

.btn-like img {
    width: 50px;
    height: 50px;
}

.icon-close {
    width: 60px;
    margin-left: 20px;
}
</style>