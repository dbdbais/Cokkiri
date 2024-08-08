<script setup>
import { ref, onMounted } from 'vue';
import Comment from '@/components/problem/Comment.vue';

const props = defineProps({
    comment: Object,
});
const showChildren = ref(false);

const toggleChildren = () => {
    showChildren.value = !showChildren.value;
};
</script>

<template>
    <p>{{ props.comment.content }}</p>
    <button v-if="props.comment.children.length" @click="toggleChildren">
        {{ showChildren ? '숨기기' : '더 보기' }}
    </button>
    <Comment v-if="showChildren" v-for="child in props.comment.children" :key="child.id" :comment="child" />
</template>

<style scoped></style>