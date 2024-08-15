<script setup>
import { ref, watch } from "vue";
import { create } from "@/api/review";
import { userStore } from "@/stores/user";
import { auto } from "@/api/review"

const props = defineProps({
    problemId: Number,
    submit: Object,
    index: Number
});

const codeSelected = ref(true);
const reviewContent = ref("");
const uStore = userStore();
const isUseAi = ref(false);

const writeReview = async () => {
    try {
        const response = await create({
            user: uStore.user.id,
            problem: props.problemId,
            code: props.submit.code,
            content: reviewContent.value
        });
        console.log(response);
        if (response.data === 1) {
            Swal.fire({
                icon: "success",
                title: "리뷰 작성이 완료되었습니다.",
            });
            reviewContent.value = "";
            isUseAi.value = false;
        }
    } catch (error) {
        console.error(error);
        Swal.fire({
            icon: "error",
            title: "리뷰 작성에 실패했습니다.",
        });
    }
};

const fetchAuto = async () => {
    if (isUseAi.value === true) return;
    try {
        console.log(props.submit.code);
        const response = await auto(props.submit.code);
        console.log(response);
        if (response.status === 200) {
            isUseAi.value = true;
        }
        reviewContent.value = response.data;
    } catch (e) {
        console.error(e);
    }
};

const toggle = () => {
    codeSelected.value = !codeSelected.value;
};

watch(() => props.submit, () => {
    reviewContent.value = "";
    isUseAi.value = false;
});
</script>

<template>
    <div>
        <div>
            <div class="code-header box-row" @click="toggle">
                <span class="title main-title">{{ props.index }}번 코드</span>
                <span class="title main-title">{{ props.submit.createdTime.split("T").join(" ").slice(0, -3) }}</span>
            </div>
        </div>
        <div v-if="codeSelected" class="code-content">
            <div class="input-con code-con">
                <span class="title">코드</span><br />
                <div class="code">
                    <highlightjs language="python" :code="props.submit.code"></highlightjs>
                </div>
            </div>
            <div class=" input-con review-con">
                <span class="title">리뷰내용</span><br />
                <textarea v-model="reviewContent"></textarea>
            </div>
            <div class="box-row btn-con">
                <div class="btn" @click="fetchAuto" :class="{ disabled: isUseAi }">
                    <span class="title">AI 작성</span>
                </div>
                <div class="btn" @click="writeReview">
                    <span class="title">제출하기</span>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.code-list {
    /* border: 3px solid #3B72FF; */
    border-radius: 15px;
}

.code-header {
    width: 550px;
    justify-content: space-between;
    border-radius: 10px;
    background-color: #3B72FF;
    padding: 5px 10px;
}

.code-header>span {
    font-size: 25px;
    color: rgb(137, 204, 230);
}

.icon-close {
    width: 60px;
}

.modal-header span {
    font-size: 55px;
}

.input-con span {
    font-size: 30px;
}

.code-content {
    padding: 15px;
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
    height: 150px;
    font-size: 18px;
    /* font-weight: 600; */
    font-family: "yg-jalnan";
    padding: 10px;
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

.disabled {
    background-color: #c7c7c7 !important;
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
</style>