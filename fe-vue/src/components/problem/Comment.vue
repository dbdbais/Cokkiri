<script setup>
import { ref, onMounted, computed } from 'vue';
import Comment from '@/components/problem/Comment.vue';
import { getUser } from '@/api/user';

const props = defineProps({
    comment: Object,
    commentDepth: Number
});
const userInfo = ref({});

onMounted(async () => {
    try {
        const response = await getUser(props.comment.userId);
        console.log(response);
        userInfo.value = response.data;
    } catch (error) {
        console.error(error);
    }
})

const showChildren = ref(true);

const toggleChildren = () => {
    showChildren.value = !showChildren.value;
};

const imageSrc = computed(() => {
    switch (userInfo.value.tier) {
        case 0:
            return new URL("@/assets/rank/seed.svg", import.meta.url).href;
        case 1:
            return new URL("@/assets/rank/kiwi.svg", import.meta.url).href;
        case 2:
            return new URL("@/assets/rank/apple.svg", import.meta.url).href;
        case 3:
            return new URL("@/assets/rank/orange.svg", import.meta.url).href;
        case 4:
            return new URL("@/assets/rank/koreamelon.svg", import.meta.url).href;
        case 5:
            return new URL("@/assets/rank/durian.svg", import.meta.url).href;
        default:
            return new URL("@/assets/rank/seed.svg", import.meta.url).href;
    }
});
</script>

<template>
    <div>
        <div class="box-row comment" :style="{ marginLeft: props.commentDepth * 40 + 'px' }">
            <div class="box-row comment-left">
                <img id="rank-img" :src="imageSrc" alt="rank" class="rank" />
                <div class="box-col comment-text">
                    <div class="box-row comment-text-top">
                        <span>{{ props.comment.userId }}</span>
                    </div>
                    <span>{{ props.comment.content }}</span>
                </div>
            </div>
            <div class="comment-right">
                <button v-if="props.comment.children.length && commentDepth === 0" @click="toggleChildren"
                    class="btn-expand">
                    {{ showChildren ? '더 보기' : '숨기기' }}
                </button>
            </div>
        </div>
        <Comment v-if="showChildren" v-for="child in props.comment.children" :key="child.id" :comment="child"
            :comment-depth="commentDepth + 1" />
    </div>
</template>

<style scoped>
.comment {
    border-bottom: 3px solid #ccc;
    padding: 3px 0;
    justify-content: space-between;
    align-items: center;
}

.user-profile {
    align-items: center;
}


.rank {
    width: 50px;
    height: 50px;
    border: 1px solid black;
    border-radius: 50%;
    background-color: white;
    padding: 5px;
    align-self: center;
}

.comment-text {
    margin-left: 5px;
}

.comment-text-top {
    justify-content: space-between;
}

.btn-expand {
    width: 65px;
    height: 35px;
    color: white;
    border: 1px solid white;
    border-radius: 10px;
    background-color: hsl(223, 61%, 68%);
}
</style>