<template>
  <div class="box-main-con box-col">
    <div class="box-row" style="justify-content: space-between; align-items: center">
      <div id="name" class="title main-title">{{ store.user.nickname }}</div>
      <button class="bold-text logout" @click="logout">로그아웃</button>
    </div>
    <div id="rank-container" class="box-row box-main-exp" style="align-items: center">
      <img id="rank-img" :src="imageSrc" alt="rank" class="rank" />
      <Exp id="rank-exp" :grade="grade" :percentage="store.user.percent"></Exp>
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import Exp from "@/components/home/Exp.vue";
import { userStore } from "@/stores/user";
import { useRouter } from "vue-router";

const store = userStore();
const router = useRouter();
const grade = store.user.tier;

const imageSrc = computed(() => {
  switch (grade) {
    case 0:
      return new URL("@/assets/rank/seed.svg", import.meta.url).href;
    case 1:
      return new URL("@/assets/rank/kiwi.svg", import.meta.url).href;
    case 2:
      return new URL("@/assets/rank/apple.svg", import.meta.url).href;
    case 3:
      return new URL("@/assets/rank/orange.svg", import.meta.url).href;
    case 4:
      return new URL("@/assets/rank/koreamelon.svg", import.meta.url).href;
    case 5:
      return new URL("@/assets/rank/durian.svg", import.meta.url).href;
    default:
      return new URL("@/assets/rank/seed.svg", import.meta.url).href;
  }
});

const logout = function () {
  store.logout();
  router.replace({ name: "login" });
};
</script>

<style scoped>
#name {
  margin-bottom: 5px;
  font-size: 30px;
}

#rank-img {
  width: 40px;
  height: 40px;
  margin: 10px 10px;
}

#rank-exp {
  height: 30px;
  margin: 10px 10px;
}

.logout {
  padding: 10px 20px;
  background-color: #cadcff;
  border-radius: 5px;
  border-width: 3px;
  border-color: #c191ff;
}

.logout:hover {
  background-color: #E69500;
}
</style>
