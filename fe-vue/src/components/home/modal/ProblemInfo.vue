<script setup>
import { ref, onBeforeMount } from "vue";
import { userStore } from "@/stores/user";
import { useModal } from "@/composables/useModal";
import { getProblem } from "@/api/problem";
import SubmitList from "@/components/home/modal/SubmitList.vue"

const props = defineProps({
    problemId: Number
});
const uStore = userStore();
const problemData = ref({});
const problemText = ref("");
const { isModalOpen: isSubmitModalOpen, openModal: openSubmitModal, closeModal: closeSubmitModal } = useModal();

onBeforeMount(async () => {
    try {
        const response = await getProblem(props.problemId);
        console.log(response);
        problemData.value = response.data;
        problemText.value = getProblemText(response.data.info);
    } catch (e) {
        console.error(e);
    }
});

const getProblemText = (htmlText) => {

    var divContentMatch = htmlText.match(/<div id="problem_description"[^>]*>([\s\S]*?)<\/div>/);

    if (divContentMatch && divContentMatch[1]) {
        var divContent = divContentMatch[1];

        return divContent;
    }
};

</script>

<template>
    <div class="modal-overlay">
        <div class="modal-con" :class="{ 'top-shift': isSubmitModalOpen, 'shift-close': !isSubmitModalOpen }">
            <div class="modal-header box-row">
                <div>
                    <span class="title header-span">{{ problemData.title }}</span>
                </div>
                <div class="box-row">
                    <img src="@/assets/exit.svg" alt="close" class="icon-close" @click="$emit('close')" />
                </div>
            </div>
            <div class="modal-content">
                <div class="left-con">
                    <span class="problem-title">문제</span>
                    <span v-html="problemText"></span>
                </div>
                <div class="btn-con">
                    <div class="btn-review" @click="openSubmitModal">
                        <span class="title main-title btn-review-text">리뷰하기</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <SubmitList v-if="isSubmitModalOpen" @close="closeSubmitModal" :problem-id="problemId" />
</template>

<style scoped>
.modal-overlay {
    background-color: rgba(0, 0, 0, 0);
}

.title {
    color: white;
    font-family: "RixInooAriDuriR";
    font-size: 35px;
}

.top-shift {
    transition: transform 0.4s ease-in-out;
    transform: translate(0, -55%);
}

.shift-close {
    transition: transform 0.4s ease-in-out;
    transform: translate(0, 0);
}

.modal-con {
    position: absolute;
    right: 130px;
    width: 650px;
    height: 500px;
    border: 3px solid #3B72FF;
    border-radius: 10px;
    background-color: #DBE7FF;
    padding: 20px;
}

.modal-header {
    justify-content: space-between;
    align-items: center;
}

.header-span {
    font-size: 40px;
    text-align: center;
}

.icon-close {
    width: 40px;
    margin-left: 20px;
}

.modal-content {
    margin-top: 30px;
    width: 580px;
    height: 340px;
}

.left-con {
    width: 580px;
    height: 340px;
    border: 2px solid #3B72FF;
    border-radius: 10px;
    background-color: white;
    padding: 10px;
    overflow: auto;
}

.problem-title {
    font-size: 20px;
    font-weight: 800;
}

.btn-review {
    margin-top: 5px;
    text-align: right;
}

.btn-review-text {
    color: rgb(230, 137, 137);
}
</style>