<script setup>
import { ref } from "vue";
import { create } from "@/api/review";
import { userStore } from "@/stores/user";

const props = defineProps({
    problemId: Number
});

const codeSelected = ref(false);
const tmpCode = ref(`print('hello, world')














`);

const reviewCode = ref("");
const reviewContent = ref("");
const uStore = userStore();

const writeReview = async () => {
    try {
        console.log(uStore.user.id + " " + props.problemId + " " + reviewCode.value + " " + reviewContent.value);
        const response = await create({
            user: uStore.user.id,
            problem: props.problemId,
            code: reviewCode.value,
            content: reviewContent.value
        });
        console.log(response);
        Swal.fire({
            icon: "success",
            title: "리뷰 작성이 완료되었습니다.",
        });
    } catch (error) {
        console.error(error);
        Swal.fire({
            icon: "error",
            title: "리뷰 작성에 실패했습니다.",
        });
    }
};  
</script>

<template>
    <div class="modal-overlay">
        <div class="modal-con">
            <div class="modal-header box-row">
                <span class="title main-title">리뷰작성</span>
                <img src="@/assets/exit.svg" alt="close" class="icon-close" @click="$emit('close')" />
            </div>
            <div class="modal-content">
                <div v-if="codeSelected">

                </div>
                <div v-else>
                    <div class="input-con code-con">
                        <span class="title">코드 히스토리</span><br />
                        <div class="code">
                            <highlightjs language="python" :code="tmpCode"></highlightjs>
                        </div>
                    </div>
                    <div class=" input-con review-con">
                        <span class="title">리뷰</span><br />
                        <textarea v-model="reviewContent"></textarea>
                    </div>
                    <div class="btn-con" @click="writeReview">
                        <span class="title">작성하기</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.modal-con {
    position: absolute;
    right: 30px;
    width: 800px;
    height: 660px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: #DBE7FF;
    padding: 20px 30px 0 20px;
}

.modal-header {
    justify-content: space-between;
    align-items: center;
}

.modal-content {
    margin-top: 20px;
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
    width: 120px;
    padding: 10px 0;
    text-align: center;
    border-radius: 10px;
    background-color: #6969f0;
}

.btn-con span {
    font-size: 25px;
}
</style>