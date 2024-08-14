<script setup>
const props = defineProps({
    msg: String
})

const getClass = () => {
    return props.msg === '축소' ? 'red' : 'blue'
}
</script>

<template>
    <div class="box-row viewport-con">
        <div class="box-col box-info">
            <span class="viewport-title">
                해상도 조절이 필요합니다.
            </span>
            <span :class="getClass()" class="viewport-type">
                {{ props.msg }}
            </span>
            <span class="viewport-type">
                {{ props.msg === '축소' ? 'Ctrl + -' : 'Ctrl + -' }} 입력
            </span>
        </div>
    </div>
</template>

<style scoped>
.viewport-con {
    width: 100vw;
    height: 100vh;
    justify-content: center;
    align-items: center;
}

.box-info {
    width: 40vw;
    height: 40vh;
    justify-content: space-between;
    align-items: center;
    border: 0.5vh solid #0073E6;
    padding: 6vh 0;
    border-radius: 1vh;
    background-color: #A6C9FF;
}

.box-info>span {
    font-family: "RixInooAriDuriR";
}

.viewport-title {
    font-size: 4vh;
}

.viewport-type {
    font-size: 3vh;
}

.red {
    color: hsl(0, 70%, 50%);
}

.blue {
    color: blue;
}
</style>