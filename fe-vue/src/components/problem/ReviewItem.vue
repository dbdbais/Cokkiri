<script setup>
import { ref, onBeforeMount, computed } from 'vue';
import { insert, getListByReviewId } from '@/api/comment';
import Comment from '@/components/problem/Comment.vue';
import { userStore } from '@/stores/user';

const props = defineProps({
    review: Object,
});
const uStore = userStore();
const commentDto = ref({
    userId: uStore.user.id,
    id: props.review.rid,
    parentId: null,
    content: ''
})
// const commentData = ref([]);
const commentData = ref([
    {
        "userId": "user1",
        "id": 1,
        "content": "부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글부모댓글",
        "children": [
            {
                "userId": "user1",
                "id": 2,
                "content": "자식댓글",
                "children": []
            }
        ]
    },
    {
        "userId": "ssafy",
        "id": 3,
        "content": "부모댓글",
        "children": [
            {
                "userId": "ssafy",
                "id": 4,
                "content": "자식댓글",
                "children": [
                    {
                        "userId": "user1",
                        "id": 5,
                        "content": "손자댓글",
                        "children": [
                            {
                                "userId": "ssafy",
                                "id": 6,
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
        "id": 7,
        "content": "부모댓글",
        "children": [
            {
                "userId": "ssafy",
                "id": 8,
                "content": "자식댓글",
                "children": []
            }
        ]
    }
]);

onBeforeMount(async () => {
    // fetchGetListByReviewId
});

const openIndex = ref(true);
const commentDepth = ref(0);
const selectedReplyId = ref(0);

const toggle = () => {
    openIndex.value = openIndex.value === true ? false : true;
};

const requestInsert = async () => {
    console.log(commentDto.value);
    try {
        const response = await insert(commentDto.value);
        console.log(response);
        if (response.data === 1) {
            Swal.fire({
                title: '댓글이 등록되었습니다.',
                icon: 'success',
            });
            // fetchGetListByReviewId
        } else {
            Swal.fire({
                title: '댓글 등록에 실패했습니다.',
                icon: 'error',
            });
        }
    } catch (error) {
        console.error(error);
    }
}

const fetchGetListByReviewId = async () => {
    try {
        const response = await getListByReviewId(props.review.rid);
        commentData.value = response.data;
        console.log(commentData);
    } catch (error) {
        console.error(error);
    }
}

const selectedReply = (id) => {
    selectedReplyId.value = id;
}

function findCommentById(comments, targetId) {
    for (const comment of comments) {
        if (comment.id === targetId) {
            return comment;
        }
        if (comment.children && comment.children.length > 0) {
            const found = findCommentById(comment.children, targetId);
            if (found) {
                return found;
            }
        }
    }
    return null;
}

const findedComment = computed(() => {
    return findCommentById(commentData.value, selectedReplyId.value);
});
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
                <highlightjs class="code" :code="props.review.code" />
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
                    <input type="text" placeholder="댓글을 입력하세요." class="input-comment" v-model="commentDto.content" />
                    <img class="comment-submit-img" src="@/assets/send_blue.svg" @click="requestInsert" />
                    <span v-if="selectedReplyId > 0" class="selected-comment">{{ findedComment.content.slice(0, 5)
                        }}</span>
                </div>
                <Comment v-for="comment in commentData" :key="comment.id" :comment="comment"
                    :comment-depth="commentDepth" @selected-reply="selectedReply" />
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

.code {
    border: none;
    height: 180px;
    overflow: auto;
}

.review {
    height: 180px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: #dbe7ff;
    padding: 10px;
    margin-top: 5px;
    overflow: auto;
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

.selected-comment {
    position: absolute;
    top: 8px;
    right: 50px;
    font-size: 15px !important;
    background-color: #dbe7ff;
    border-radius: 10px;
    border: 1px solid white;
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