<script setup>

const props = defineProps(['announce']);

</script>

<template>
    <div class="modal-overlay" @click.self="$emit('close')">
        <div class="modal-content">
            <span>공지사항</span>
            <div class="content">{{ announce }}</div>
        </div>
    </div>
</template>

<style scoped>
.modal-overlay {
    z-index: 1001;
}

.modal-content {
    text-align: center;
    border: 3px solid #3B72FF;
    border-radius: 8px;
    background-color: #CADCFF;
    padding: 20px;
}

.modal-content span {
    font-size: 35px;
    color: white;
}

.content {
    width: 550px;
    height: 500px;
    border-radius: 10px;
    background-color: white;
    margin-top: 30px;
    padding: 10px;
    color: black;
    font-size: 25px;
    text-align: start;
    overflow: auto;
}
</style>