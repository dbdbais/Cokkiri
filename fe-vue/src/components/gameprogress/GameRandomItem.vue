<script setup>
import { ref } from "vue";

defineProps({
  items: Array,
  userItem: Array,
});
const emit = defineEmits(["close"]);
const getItem = ref(false);
const random = ref("");
const item1 = ref("");
const item2 = ref("");

const stopBtn = (userItem, get) => {
  if (get) {
    emit("close");
  } else {
    item1.value = `ramdom-${userItem[0]}`;
    item2.value = `ramdom-${userItem[1]}`;
    getItem.value = true;
  }
};
</script>

<template>
  <div class="random-item box md slideDown">
    <span class="item-title bold-text"> 랜덤 아이템</span>
    <div class="items flex-align">
      <div class="item box md">
        <div class="item-con box-col" :class="item1">
          <div class="item-box md" v-for="item in items" :key="item.index">
            <img alt="아이템" :src="'/src/assets/item/' + item + '.svg'" />
          </div>
        </div>
      </div>
      <div class="item box md">
        <div class="item-con box-col" :class="item2">
          <div class="item-box md" v-for="item in items" :key="item.index">
            <img alt="아이템" :src="'/src/assets/item/' + item + '.svg'" />
          </div>
        </div>
      </div>
      <button class="item-btn bold-text" @click="stopBtn(userItem, getItem)">
        {{ getItem ? "받기" : "아이템 뽑기" }}
      </button>
    </div>
  </div>
</template>

<style scoped>
.random-item {
  width: 700px;
  height: 400px;
  position: absolute;
  z-index: 3;

  background-color: #9fbaff;
}
.items {
  position: relative;
  width: 700px;
  height: 400px;
  justify-content: space-around;
}
.item {
  width: 120px;
  height: 120px;

  background-color: #dbe7ff;
  position: relative;
  overflow: hidden;
}
.item-box {
  width: 120px;
  height: 120px;
}
.item-con {
  width: 120px;
  height: 1800px;
  position: absolute;
  align-items: center;
  transform: translateY(840px);
  transition: all 2s ease-in-out;
  background-color: #dbe7ff;
}
.item-con img {
  width: 50px;
}
.item-title {
  position: absolute;
  font-size: 55px;
  top: 20px;
  left: 220px;
}

.item-btn {
  width: 150px;
  height: 100px;
  font-size: 25px;

  background-color: #ff8e8e;
  border-radius: 10px;
  border-color: #ff4d4d;
  border-width: 5px;
}
.ramdom-blind {
  transform: translateY(-360px);
}
.ramdom-minimum {
  transform: translateY(-480px);
}
.ramdom-prevent {
  transform: translateY(-600px);
}
.ramdom-fontBig {
  transform: translateY(-720px);
}
.ramdom-fontSmall {
  transform: translateY(-840px);
}
</style>
