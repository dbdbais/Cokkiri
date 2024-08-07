<script setup>
import { ref, onMounted, onUnmounted } from 'vue';

const resizable = ref(null);
const separator = ref(null);
const panel1 = ref(null);
const panel2 = ref(null);

const startResize = (e) => {
    e.preventDefault();
    document.addEventListener('mousemove', resizePanels);
    document.addEventListener('mouseup', stopResize);
}

const resizePanels = (e) => {
    const containerOffsetLeft = resizable.value.offsetLeft;
    const pointerRelativeXpos = e.clientX - containerOffsetLeft;
    const panel1MinWidth = 50;
    const panel2MinWidth = 50;
    const panel1Width = Math.max(panel1MinWidth, pointerRelativeXpos);
    const panel2Width = Math.max(panel2MinWidth, resizable.clientWidth - pointerRelativeXpos - separator.value.offsetWidth);
    panel1.value.style.flex = `0 0 ${panel1Width}px`;
    panel2.value.style.flex = `0 0 ${panel2Width}px`;
}

const stopResize = () => {
    document.removeEventListener('mousemove', resizePanels);
    document.removeEventListener('mouseup', stopResize);
}

onUnmounted(() => {
    document.removeEventListener('mousemove', resizePanels);
    document.removeEventListener('mouseup', stopResize);
});

</script>

<template>
    <div class="game-prog-con box-col">
        <div class="game-header box-row">
            <span class="title">스피드 코딩</span>
            <RouterLink :to="{ name: 'home' }">
                <img src="@/assets/room_exit.svg" class="exit-icon">
            </RouterLink>
        </div>
        <div class="game-content box-row">
            <div class="resizable box-row" ref="resizable">
                <div class="problem-con box-col panel" ref="panel1">
                    <div class="problem-header">
                        <span class="title">문제</span>
                    </div>
                    <div class="problem-content">
                        <div class="problem"></div>
                    </div>
                </div>
                <div class="seperator" ref="separator" @mousedown="startResize"></div>
                <div class="code-con box-col panel" ref="panel2">
                    <div class="code-header box-row">
                        <div>
                            <span class="title">python</span>
                        </div>
                        <div>
                            <span class="title">코드</span>
                        </div>
                        <div>
                            <span class="title">제출하기</span>
                        </div>
                    </div>
                    <div class="code-content">
                        <div class="code"></div>
                    </div>
                </div>
            </div>
            <div class="timer-con">시계</div>
            <div class="prog-gui-con box-row">
                <div>
                    내 진행 창
                </div>
                <div>
                    상대 진행 창
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
/* Game Progress Container */

.game-prog-con {
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    width: 1920px;
    height: 1080px;
}

/* ============================ */
/*          Game Header         */
/* ============================ */

.game-header {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 10%;
}

.game-header span {
    font-size: 60px;
    font-weight: bold;
}

.exit-icon {
    position: absolute;
    top: 15px;
    right: 15px;
    width: 70px;
}

/* ============================ */
/*          Game Content        */
/* ============================ */

.game-content {
    width: 95%;
    height: 90%;
    border: 3px solid #3B72FF;
    border-radius: 10px;
    background-color: #C191FF;
    padding: 30px;
}

.resizable {
    display: flex;
    width: 1400px;
    height: 100%;
}

.seperator {
    background: #aaa;
    cursor: col-resize;
    width: 5px;
}

.panel {
    flex-grow: 1;
    overflow: auto;
}

/* Problem Container */

.problem-header,
.problem-content,
.code-header,
.code-content {
    border: 3px solid #3B72FF;
    background-color: #DBE7FF;
}

.problem-header,
.code-header {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 50px;
    border-radius: 10px 10px 0 0;
    font-size: 30px;
}

.problem-content,
.code-content {
    height: 100%;
    border-radius: 0 0 10px 10px;
    padding: 10px;
}

.problem,
.code {
    width: 100%;
    height: 100%;
    border: 3px solid #3B72FF;
    border-radius: 10px;
    background-color: white;
}


/* Code Container */


/* Timer Container */

/* Progress GUI Container */
</style>