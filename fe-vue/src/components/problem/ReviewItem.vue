<script setup>
import { ref, onMounted } from 'vue';
import { getAllComment } from '@/api/comment';
import Comment from '@/components/problem/Comment.vue';

const props = defineProps({
    review: Object,
});
const commentData = ref([
    {
        "userId": "user1234",
        "reviewId": 1,
        "content": "부모댓글",
        "children": [
            {
                "userId": "user1234",
                "reviewId": 1,
                "content": "자식댓글",
                "children": []
            }
        ]
    },
    {
        "userId": "user1234",
        "reviewId": 1,
        "content": "부모댓글",
        "children": [
            {
                "userId": "user1234",
                "reviewId": 1,
                "content": "자식댓글",
                "children": [
                    {
                        "userId": "user1234",
                        "reviewId": 1,
                        "content": "손자댓글",
                        "children": [
                            {
                                "userId": "user1234",
                                "reviewId": 1,
                                "content": "증손자댓글",
                                "children": []
                            }
                        ]
                    }
                ]
            }
        ]
    },
    {
        "userId": "user1234",
        "reviewId": 1,
        "content": "부모댓글",
        "children": [
            {
                "userId": "user1234",
                "reviewId": 1,
                "content": "자식댓글",
                "children": []
            }
        ]
    }
]);

onMounted(async () => {
    // try {
    //     const response = await getAllComment(props.review.rid);
    //     commentData.value = response.data;
    //     console.log(commentData);
    // } catch (error) {
    //     console.error(error);
    // }

});

const openIndex = ref(false);

const toggle = () => {
    openIndex.value = openIndex.value === true ? false : true;
};
</script>

<template>
    <div class="review-con">
        <div class="review-header box-row">
            <span class="title">{{ props.review.user.nickname }}</span>
            <span class="nomal-text md" @click="toggle()">
                {{ openIndex === false ? '▼' : '▲' }}
            </span>
        </div>
        <div v-if="openIndex" class="review-content">
            <div class=" code">
                <span class="title">코드</span>
                <span class="normal-text inner-text">{{ props.review.code }}</span>
            </div>
            <div class="review">
                <span class="title">코드 리뷰</span>
                <span class="normal-text inner-text">{{ props.review.content }}</span>
            </div>
            <Comment v-for="comment in commentData" :key="comment.id" :comment="comment" />
        </div>
    </div>
</template>

<style scoped>
.review-con {
    margin-bottom: 25px;
}

.review-header {
    justify-content: space-between;
    align-items: center;
    border: 3px solid #3B72FF;
    border-radius: 10px;
    background-color: #B6CAFF;
    padding: 5px 15px;
    z-index: 1000;
}

.review-header>span {
    font-size: 35px;
    color: #3B72FF;
    font-family: "yg-jalnan";
    -webkit-text-stroke: 1.5px #3B72FF;
}

.review-content {
    width: 100%;
    height: 360px;
    border: 3px solid #3B72FF;
    border-radius: 10px;
    background-color: #C191FF;
    padding: 10px;
    overflow: auto;
}

.review-content span {
    font-size: 30px;
}

.code,
.review {
    min-height: 180px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: #FFFFFF;
    padding: 10px;
}

.review {
    margin-top: 15px;
}

.inner-text {
    display: block;
    color: black;
    font-size: 25px !important;
    margin-top: 5px;
}
</style>