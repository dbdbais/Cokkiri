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
  fontBig: true,
  fontSmall: true,
});
const useBlind = ref(0);
const useMinimum = ref(100);
const usePrevernt = ref(false);
const useItem = ref(false);
const pickItem = ref("");
const showitem = ref(false);
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
  `ws://192.168.30.160:8080/game/${route.params.gameId}/${store.user.nickname}`
);

ws.onmessage = function (event) {
  let data = event.data.split("|!|");
  console.log(data);
  if (data.length === 1) {
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
    }
  }
};

const showItemFun = function () {
  showitem.value = true;
};

const hideItmeFun = function () {
  showitem.value = false;
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
  <div class="md container">
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
              <img :src="'/src/assets/item/' + key + '.svg'" alt="아이템" />
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
        <Main :blind="useBlind" :minimum="useMinimum" :prevent="usePrevernt" />
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
.user-btn,
.item-box {
  width: 710px;
  left: 590px;
  background-color: #9fbaff;
}
.item-box {
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
