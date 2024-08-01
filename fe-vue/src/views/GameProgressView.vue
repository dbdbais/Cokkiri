<script setup>
import { ref } from 'vue';
import Main from '@/components/meeting/Main.vue';
import BattleStatus from '@/components/gameprogress/BattleStatus.vue';

const myCharacter = '/src/assets/game-character.svg';
const enemyCharacter = '/src/assets/game-character.svg';

</script>

<template>
    <div class="game-prog-con box-col">
        <RouterLink :to="{ name: 'home' }">
            <img src="@/assets/room_exit.svg" class="exit-icon">
        </RouterLink>
        <img src="@/assets/timer_temp.svg" class="timer">
        <div class="game-header box-row box-sb">
            <div class="prog-gui-con box-row">
                <BattleStatus :url="myCharacter" class="myStatus" />
                <BattleStatus :url="enemyCharacter" class="enemyStatus" />
            </div>
        </div>
        <div class="game-content box-row">
            <Main />
        </div>
    </div>
</template>

<style scoped>
/* Game Progress Container */

.game-prog-con {
    position: relative;
    width: 1920px;
    height: 1080px;
}

/* ============================ */
/*          Game Header         */
/* ============================ */

.game-header {
    display: flex;
    width: 100%;
    height: 280px;
}

.game-header span {
    font-size: 60px;
    font-weight: bold;
}

.exit-icon {
    position: absolute;
    right: 10px;
    top: 10px;
    width: 100px;
}

.timer {
    position: absolute;
    width: 250px;
    right: 0px;
    top: 190px;
}

.prog-gui-con {
    position: relative;
}

.myStatus {
    position: absolute;
    top: 20px;
    left: 100px;
}

.enemyStatus {
    position: absolute;
    top: 140px;
    left: 100px;
}

/* ============================ */
/*          Game Content        */
/* ============================ */

.game-content {
    width: 1920px;
    height: 800px;
}

.main-content {
    width: 100%;
    height: 800px;
    margin: 0px;
}
</style>