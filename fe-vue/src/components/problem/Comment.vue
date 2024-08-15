<script setup>
import { ref, onBeforeMount, computed } from 'vue';
import Comment from '@/components/problem/Comment.vue';
import { getUser } from '@/api/user';

const props = defineProps({
    comment: Object,
    commentDepth: Number
});
const userInfo = ref({});

onBeforeMount(async () => {
    try {
        const response = await getUser(props.comment.writer.id);
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
        case "SEED":
            return new URL("@/assets/rank/seed.svg", import.meta.url).href;
        case "KIWI":
            return new URL("@/assets/rank/kiwi.svg", import.meta.url).href;
        case "APPLE":
            return new URL("@/assets/rank/apple.svg", import.meta.url).href;
        case "ORANGE":
            return new URL("@/assets/rank/orange.svg", import.meta.url).href;
        case "KOREAMELON":
            return new URL("@/assets/rank/koreamelon.svg", import.meta.url).href;
        case "DURIAN":
            return new URL("@/assets/rank/durian.svg", import.meta.url).href;
        default:
            return new URL("@/assets/rank/seed.svg", import.meta.url).href;
    }
});
</script>

<template>
    <div class="box-row comment"
        :style="{ marginLeft: (props.commentDepth * 40 > 120 ? 120 : props.commentDepth * 40) + 'px' }">
        <div class="box-row comment-left">
            <img id="rank-img" :src="imageSrc" alt="rank" class="rank" />
            <div class="box-col comment-text">
                <div class="box-row comment-text-top">
                    <span>{{ props.comment.writer.nickname }}</span>
                    <span class="reply" @click="$emit('selectedReply', comment.id)">답글</span>
                </div>
                <span>{{ props.comment.content }}</span>
            </div>
        </div>
        <div class="comment-right">
            <button v-if="props.comment.children.length && commentDepth === 0" @click="toggleChildren"
                class="btn-expand">
                {{ showChildren ? '숨기기' : '더보기' }}
            </button>
            <div v-else class="expand-blank"></div>
        </div>
    </div>
    <Comment v-if="showChildren" v-for="child in props.comment.children" :key="child.id" :comment="child"
        :comment-depth="commentDepth + 1" @selected-Reply="(id) => $emit('selectedReply', id)" />
</template>

<style scoped>
.comment {
    border-bottom: 3px solid #ccc;
    padding: 3px 0;
    justify-content: space-between;
    align-items: center;
}

.comment-left {
    width: 100%;
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
    width: 100%;
    margin-left: 5px;
}

.comment-text-top {
    width: 100%;
    justify-content: space-between;
}

.reply {
    font-weight: 800;
}

.btn-expand {
    width: 65px;
    height: 35px;
    color: white;
    border: 1px solid white;
    border-radius: 10px;
    background-color: hsl(223, 61%, 68%);
}

.expand-blank {
    width: 65px;
    height: 35px;
}
</style>