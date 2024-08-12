<script setup>
import { useMeetingStore } from '@/stores/meeting';

const mStore = useMeetingStore();
</script>

<template>
    <div>
        <div class="modal-con">
            <div class="modal-header">
                <span class="title main-title">
                    힌트
                </span>
            </div>
            <div class="modal-content">
                <div class="hint-box">
                    <div v-for="hint in mStore.hints" :key="hint.no">
                        <span>{{ hint.no }}번</span>
                        <span>{{ hint.content }}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.modal-con {
    width: 500px;
    height: 500px;
    border: 5px solid black;
    background-color: blue;
}

.modal-content {
    width: 500px;
    height: 500px;
    border: 5px solid yellow;
    background-color: white;
}
</style>