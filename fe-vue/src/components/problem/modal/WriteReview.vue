<script setup>
import { ref } from "vue";
import { create } from "@/api/review";
import { userStore } from "@/stores/user";

const props = defineProps({
    problemId: Number
});

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
    } catch (error) {
        console.error(error);
    }
};  
</script>

<template>
    <div class="modal-overlay" @click.self="$emit('close')">
        <div class="modal-con">
            <div class="modal-header">
                <span class="title">리뷰작성</span>
            </div>
            <div class="modal-content">
                <div class="input-con">
                    <span class="title">Code</span><br />
                    <input type="text" v-model="reviewCode" />
                </div>
                <div class="input-con">
                    <span class="title">Content</span><br />
                    <textarea v-model="reviewContent"></textarea>
                </div>
                <div class="btn-con">
                    <div class="btn-review">
                        <button class="title" @click="writeReview">작성</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.modal-con {
    position: absolute;
    right: 0;
    width: 800px;
    height: 640px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: #DBE7FF;
    padding: 20px 30px;
}
</style>