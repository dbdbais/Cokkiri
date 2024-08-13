<script setup>
import { ref, onMounted } from 'vue';
import { getAllComment } from '@/api/comment';
import Comment from '@/components/problem/Comment.vue';

const props = defineProps({
    review: Object,
});
const commentData = ref([
    {
        "userId": "user1",
        "reviewId": 1,
        "content": "부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글",
        "children": [
            {
                "userId": "user1",
                "reviewId": 1,
                "content": "자식댓글",
                "children": []
            }
        ]
    },
    {
        "userId": "ssafy",
        "reviewId": 1,
        "content": "부모댓글",
        "children": [
            {
                "userId": "ssafy",
                "reviewId": 1,
                "content": "자식댓글",
                "children": [
                    {
                        "userId": "user1",
                        "reviewId": 1,
                        "content": "손자댓글",
                        "children": [
                            {
                                "userId": "ssafy",
                                "reviewId": 1,
                                "content": "증손자댓글증손자댓글증손자댓글증손자댓글증손자댓글증손자댓글증손자댓글증손자댓글증손자댓글증손자댓글증손자댓글증손자댓글증손자댓글증손자댓글증손자댓글증손자댓글",
                                "children": []
                            }
                        ]
                    }
                ]
            }
        ]
    },
    {
        "userId": "ssafy",
        "reviewId": 1,
        "content": "부모댓글",
        "children": [
            {
                "userId": "ssafy",
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

const openIndex = ref(true);
const commentDepth = ref(0);

const toggle = () => {
    openIndex.value = openIndex.value === true ? false : true;
};
</script>

<template>
    <div class="review-con">
        <div class="review-header box-row">
            <span class="title">{{ props.review.user.nickname }}</span>
            <span class="title">{{ props.review.createdTime.split("T").join(" ") }}</span>
            <span class="nomal-text md" @click="toggle()">
                {{ openIndex === false ? '▼' : '▲' }}
            </span>
        </div>
        <div v-if="openIndex" class="review-content">
            <div class="code-box">
                <span class="title">코드</span>
                <div class=" code">
                    <span class="normal-text inner-text">{{ props.review.code }}</span>
                </div>
            </div>
            <div class="review-box">
                <span class="title">리뷰</span>
                <div class="review">
                    <span class="normal-text inner-text">{{ props.review.content }}</span>
                </div>
            </div>
            <div class="divider"></div>
            <div class="comment-box">
                <span class="title">댓글</span>
                <div class="input-comment-con">
                    <input type="text" placeholder="댓글을 입력하세요." class="input-comment" />
                    <img class="comment-submit-img" src="@/assets/send_blue.svg" />
                </div>
                <Comment v-for="comment in commentData" :key="comment.id" :comment="comment"
                    :comment-depth="commentDepth" />
            </div>
        </div>
    </div>
</template>

<style scoped>
.review-con {
    margin-bottom: 25px;
    width: 100%;
    height: 100%;
}

.review-header {
    height: 72px;
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
    height: 660px;
    border: 3px solid #3B72FF;
    border-radius: 10px;
    background-color: white;
    padding: 20px;
    overflow: auto;
}

.review-content span {
    font-size: 30px;
}

.review-box,
.comment-box {
    margin-top: 20px;
}

.divider {
    margin-top: 20px;
    border-top: 5px dashed #3B72FF;
    border-radius: 8px;
}

.code,
.review {
    min-height: 180px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: #dbe7ff;
    padding: 10px;
    margin-top: 5px;
}

.comment-box>span {
    display: block;
    margin-bottom: 10px;
}

.input-comment-con {
    position: relative;
    margin-bottom: 10px;
}

.comment-submit-img {
    position: absolute;
    width: 40px;
    top: 0;
    right: 2px;
}

.input-comment {
    width: 100%;
    height: 40px;
    border: 3px solid #3B72FF;
    border-radius: 10px;
    padding: 5px;
    margin-right: 10px;
}


.inner-text {
    display: block;
    color: black;
    font-size: 25px !important;
    margin-top: 5px;
}
</style>