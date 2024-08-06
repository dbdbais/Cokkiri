<template>
    <div class="progress-container">
        <div class="progress-bar" :style="{ width: progress + '%' }"></div>
    </div>
    <button @click="startApiRequests">Start API Requests</button>
</template>

<script>
export default {
    data() {
        return {
            progress: 0,
        };
    },
    methods: {
        async startApiRequests() {
            this.progress = 0; // 초기화

            for (let i = 0; i < 4; i++) {
                await this.simulateApiCall();
                this.progress += 25;
            }
        },
        simulateApiCall() {
            return new Promise(resolve => setTimeout(resolve, 1000)); // 1초 대기
        },
    },
};
</script>

<style>
.progress-container {
    width: 100%;
    background-color: #f3f3f3;
    border-radius: 5px;
    overflow: hidden;
    height: 30px;
    position: relative;
}

.progress-bar {
    height: 100%;
    background-color: #4caf50;
    transition: width 1s ease-in-out;
}
</style>