<script setup>
import ReviewItem from "@/components/problem/ReviewItem.vue";

const props = defineProps({
    reviewData: Object,
});
</script>

<template>
    <div class="modal-overlay" @click.self="$emit('close')">
        <div class="modal-con">
            <div class="modal-header box-row">
                <span class="title main-title">리뷰 목록</span>
                <div>
                    <img src="@/assets/exit.svg" alt="close" class="icon" @click="$emit('close')" />
                </div>
            </div>
            <div class="modal-content">
                <div v-for="review in reviewData" :key="review.id">
                    <ReviewItem :review="review" />
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
    height: 900px;
    border: 3px solid #3B72FF;
    background-color: #DBE7FF;
    padding: 20px;
}

.modal-header {
    justify-content: space-between;
}

.modal-header>span {
    font-size: 45px;
}

.modal-content {
    height: 750px;
    border-radius: 10px;
    margin-top: 20px;
    overflow: auto;
}
</style>