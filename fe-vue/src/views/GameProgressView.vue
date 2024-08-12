<script setup>
import Main from "@/components/meeting/Main.vue";
import BattleStatus from "@/components/gameprogress/BattleStatus.vue";
import GameRamdomItem from "@/components/gameprogress/GameRandomItem.vue";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { userStore } from "@/stores/user";
import { getWaitingRoom } from "@/api/waitingroom";
import { useGameStore } from "@/stores/game";

const enemyCharacter = "/src/assets/game-character.svg";
const myCharacter = "/src/assets/game-character.svg";
const store = userStore();
const gameStore = useGameStore();
const route = useRoute();
const roomData = ref([]);
const users = ref([]);
const items = ref({
  blind: false,
  minimum: false,
  prevent: false,
  fontBig: false,
  fontSmall: false,
});

const useBlind = ref(0);
const useMinimum = ref(100);
const usePrevernt = ref(false);
const useBigFont = ref(() => {});
const showitem = ref(false);
const getItem = ref(false);
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

  const itemText = {
    blind: "#",
    minimum: "$",
    prevent: "%",
    fontBig: "^",
    fontSmall: "&",
  };

  const data = `|${itemText[pickItem.value]}||!|${user}`;
  ws.send(data);
};

const ws = new WebSocket(
  `${process.env.VITE_VUE_SOCKET_URL}game/${route.params.gameId}/${store.user.nickname}`
);

ws.onmessage = function (event) {
  let data = event.data.split("|!|");
  console.log(data);
  if (data.length === 2) {
    let event = data[0];

    if (event === "BLIND") {
      console.log("문제 가리기");
      useBlind.value += 680;
      setTimeout(() => {
        useBlind.value = 0;
      }, 10000);
    } else if (event === "SIZE") {
      console.log("버튼 크기 작게");
      if (useMinimum.value > 20) {
        useMinimum.value -= 40;
      }
      console.log(useMinimum.value);
    } else if (event === "PREVENT") {
      console.log("제출 방해");
      usePrevernt.value = true;
      setTimeout(() => {
        usePrevernt.value = false;
      }, 10000);
    } else if (event === "BIG") {
      console.log("크게");
      gameStore.bigFontFun();
    } else if (event === "SMALL") {
      console.log("작게");
      gameStore.smallFontFun();
    }
  }
};

const itemList = [
  "blind",
  "minimum",
  "prevent",
  "fontBig",
  "fontSmall",
  "blind",
  "minimum",
  "prevent",
  "fontBig",
  "fontSmall",
  "blind",
  "minimum",
  "prevent",
  "fontBig",
  "fontSmall",
];
const userItem = ref([]);
const itemText = {
  blind: "화면 가리기",
  minimum: "제출 축소",
  prevent: "제출 방해",
  fontBig: "폰트 크게",
  fontSmall: "폰트 작게",
};
const showItemFun = function () {
  if (!getItem.value) {
    showitem.value = true;
  }
};

const hideItmeFun = function () {
  showitem.value = false;
};

onMounted(() => {
  getItem.value = true;
  for (let i = 0; i < 2; i++) {
    let itemIdx = getRandomIntInclusive(0, 4);
    if (items.value[itemList[itemIdx]] === true) {
      itemIdx += 1;
    }
    userItem.value.push(itemList[itemIdx]);
    items.value[itemList[itemIdx]] = true;
  }

  console.log(userItem.value);

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

function getRandomIntInclusive(min, max) {
  const minCeiled = Math.ceil(min);
  const maxFloored = Math.floor(max);
  return Math.floor(Math.random() * (maxFloored - minCeiled + 1) + minCeiled); // 최댓값도 포함, 최솟값도 포함
}

const close = () => {
  getItem.value = false;
};
</script>

<template>
  <div class="md container">
    <GameRamdomItem
      v-if="getItem"
      :items="itemList"
      :user-item="userItem"
      @close="close"
    />
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
        <div class="box user-btn box-row" v-if="useItem">
          <div>
            <button
              class="item bold-text"
              v-for="user in users"
              :key="user.index"
              @click="userUseItem(user)"
            >
              {{ user }}
            </button>
          </div>
          <button class="item bold-text close" @click="useItem = false">
            닫기
          </button>
        </div>
        <div @mouseenter="showItemFun" @mouseleave="hideItmeFun">
          <div class="box slideUp item-box" v-if="showitem">
            <button
              v-for="(val, key) in items"
              :key="key"
              class="item bold-text"
              :class="{ used: !items[key] }"
              :disabled="!items[key]"
              @click="useItemFun(key)"
            >
              <div class="box-col item-img md">
                <img
                  :src="'/src/assets/item/' + key + '.svg'"
                  alt="아이템"
                  style="margin-bottom: 10px"
                />
                {{ itemText[key] }}
              </div>
            </button>

            <!-- <button
              class="item bold-text"
              :class="{ used: !items.prevent }"
              :disabled="!items.prevent"
              @click="useItemFun('prevent')"
            >
              <img src="/src/assets/item/prevent.svg" alt="제출 방해" />
            </button> -->
          </div>
          <div class="item-guide md">
            <img
              src="/src/assets/item-guide.svg"
              alt="아이템 가이드"
              class="floating"
              style="margin-top: 50px"
            />
          </div>
        </div>
      </div>
      <div class="game-content box-row">
        <Main
          :blind="useBlind"
          :minimum="useMinimum"
          :prevent="usePrevernt"
          :bigfont="useBigFont"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.item-box,
.item-guide,
.user-btn {
  position: absolute;
  bottom: -70px;
}

.user-btn {
  width: 990px;
  left: 440px;
  background-color: #9fbaff;
}
.item-box {
  width: 720px;
  left: 610px;
  background-color: #9fbaff;
  z-index: 2;
}

.item-guide {
  left: 890px;
  width: 200px;
  height: 100px;
  z-index: 1;
}

.item {
  width: 100px;
  height: 100px;
  margin: 20px;

  border-radius: 10px;
  border-width: 5px;
  border-color: #3b72ff;
  background-color: #dbe7ff;
  font-size: 15px;
}
.item-img {
  width: 100%;
}

.user-btn {
  justify-content: space-between;
  z-index: 3;
}

.close {
  background-color: #ff4d4d;
  border-color: #ff8e8e;
}

/* Game Progress Container */

.game-prog-con {
  position: relative;
  width: 1820px;
  height: 970px;
  margin-top: 20px;
}

/* ============================ */
/*          Game Header         */
/* ============================ */

.game-header {
  display: flex;
  width: 100%;
  height: 240px;
  margin-bottom: 20px;
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
  top: 110px;
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
  width: 1820px;
  height: 750px;
}

.main-content {
  width: 100%;
  height: 800px;
  margin: 0px;
}

.used {
  background-color: rgb(117, 117, 117);
  border-color: gray;
}
</style>
