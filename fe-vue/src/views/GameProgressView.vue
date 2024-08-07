<script setup>
import { onMounted, ref } from "vue";
import Main from "@/components/meeting/Main.vue";
import BattleStatus from "@/components/gameprogress/BattleStatus.vue";
import { useRoute } from "vue-router";
import { userStore } from "@/stores/user";
import { getWaitingRoom } from "@/api/waitingroom";

const myCharacter = "/src/assets/game-character.svg";
const enemyCharacter = "/src/assets/game-character.svg";
const store = userStore();
const route = useRoute();
const roomData = ref([]);
const users = ref([]);
const items = ref({
  blind: true,
  minimum: true,
  prevent: true,
});

const useItem = ref(false);
const pickItem = ref("");

const useItemFun = function (item) {
  useItem.value = true;
  pickItem.value = item;
};

const userUseItem = function (user) {
  console.log(`${user}에게 ${pickItem.value} 사용!`);
  items.value[pickItem.value] = false;
  useItem.value = false;

  const itemText = { blind: "#", minimum: "$", prevent: "%" };

  const data = `|${itemText[pickItem.value]}||!|${user}`;
  ws.send(data);
};

const ws = new WebSocket(
  `ws://localhost:8080/game/${route.params.gameId}/${store.user.nickname}`
);

ws.onmessage = function (event) {
  let data = event.data.split("|!|");
  console.log(data);
  if (data.length === 1) {
    let event = data[0];

    if (event === "BLIND") {
      console.log("문제 가리기");
    } else if (event === "SIZE") {
      console.log("버튼 크기 작게");
    } else if (event === "PREVENT") {
      console.log("제출 방해");
    }
  }
};

onMounted(() => {
  const success = (res) => {
    console.log(res.data);
    roomData.value = res.data;
    users.value = res.data.users;
  };
  const fail = (err) => {
    console.log(err);
  };

  getWaitingRoom(route.params.gameId, success, fail);
});
</script>

<template>
  <div class="game-prog-con box-col">
    <RouterLink :to="{ name: 'home' }">
      <img src="@/assets/room_exit.svg" class="exit-icon" />
    </RouterLink>
    <img src="@/assets/timer_temp.svg" class="timer" />
    <div class="game-header box-row box-sb">
      <!-- <div class="prog-gui-con box-row">
                <BattleStatus :url="myCharacter" class="myStatus" />
                <BattleStatus :url="enemyCharacter" class="enemyStatus" />
    </div> -->
      <div class="user bold-text">
        {{ users }}
      </div>
      <div class="box" v-if="useItem">
        <button
          class="item bold-text"
          v-for="user in users"
          :key="user.index"
          @click="userUseItem(user)"
        >
          {{ user }}
        </button>
      </div>
      <div>
        <button
          class="item bold-text"
          :disabled="!items.blind"
          @click="useItemFun('blind')"
        >
          가리기
        </button>
        <button
          class="item bold-text"
          :disabled="!items.minimum"
          @click="useItemFun('minimum')"
        >
          작게 만들기
        </button>
        <button
          class="item bold-text"
          :disabled="!items.prevent"
          @click="useItemFun('prevent')"
        >
          제출 방해
        </button>
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
.item {
  width: 100px;
  height: 100px;
  margin: 20px;
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
