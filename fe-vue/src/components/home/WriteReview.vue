<script setup>
import { ref } from "vue";
import { create } from "@/api/review";
import { userStore } from "@/stores/user";
import { auto } from "@/api/review"

const props = defineProps({
    problemId: Number,
    submit: Object,
    index: Number
});

const codeSelected = ref(false);
const reviewContent = ref("");
const uStore = userStore();

const writeReview = async () => {
    try {
        const response = await create({
            user: uStore.user.id,
            problem: props.problemId,
            code: props.submit.code,
            content: reviewContent.value
        });
        console.log(response);
        Swal.fire({
            icon: "success",
            title: "리뷰 작성이 완료되었습니다.",
        });
        reviewContent.value = "";
    } catch (error) {
        console.error(error);
        Swal.fire({
            icon: "error",
            title: "리뷰 작성에 실패했습니다.",
        });
    }
};

const fetchAuto = async () => {
    try {
        console.log(tmpCode.value);
        const response = await auto(tmpCode.value);
        console.log(response);
        reviewContent.value = response.data;
    } catch (e) {
        console.error(e);
    }
};

const toggle = () => {
    codeSelected.value = !codeSelected.value;
};
</script>

<template>
    <div>
        <div class="code-header box-row" @click="toggle">
            <span class="title main-title">{{ props.index }}번 코드</span>
            <span class="title main-title">{{ props.submit.createdTime.split("T").join(" ") }}</span>
        </div>
    </div>
    <div v-if="codeSelected">
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
            <div class="btn" @click="fetchAuto">
                <span class="title">자동작성</span>
            </div>
            <div class="btn" @click="writeReview">
                <span class="title">작성하기</span>
            </div>
        </div>
    </div>
</template>

<style scoped>
.code-header {
    justify-content: space-between;
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
</style>