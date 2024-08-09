<template>
  <div class="box-main-con box-col">
    <div class="box-row" style="justify-content: space-between; align-items: center">
      <div id="name" class="title main-title">{{ uStore.user.nickname }}</div>
      <img class="logout" src="@/assets/logout2.svg" @click="logout" />
    </div>
    <div class="rank-con box-row box-main-exp" style="align-items: center">
      <img id="rank-img" :src="imageSrc" alt="rank" class="rank" />
      <Exp id="rank-exp" :grade="grade" :percentage="uStore.user.percent"></Exp>
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import Exp from "@/components/home/Exp.vue";
import { userStore } from "@/stores/user";
import { useRouter } from "vue-router";

const uStore = userStore();
const router = useRouter();
const grade = uStore.user.tier;

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
  uStore.logout();
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

.rank-con {
  margin-top: 5px;
}

.logout {
  width: 40px;
}
</style>
