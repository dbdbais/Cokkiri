<template>
  <div class="box-main-con box-col">
    <div class="box-row" style="justify-content: space-between; align-items: center">
      <div id="name" class="title main-title">{{ uStore.user.nickname }}</div>
      <img class="logout" src="@/assets/logout2.svg" @click="logout" />
    </div>
    <div class="rank-con box-row box-main-exp" style="align-items: center">
      <img :src="imageSrc" alt="rank" class="rank-img" />
      <Exp :tier="tier" :percentage="uStore.user.score" class="rank-exp"></Exp>
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import Exp from "@/components/home/Exp.vue";
import { getUser } from "@/api/user";
import { userStore } from "@/stores/user";
import { useRouter } from "vue-router";
import { onMounted } from "vue";

const uStore = userStore();
const router = useRouter();

onMounted(() => {
  getUser(uStore.user.id).then((res) => {
    uStore.setUser(res.data);
  });
});
const grade = uStore.user.tier;

const imageSrc = computed(() => {
  switch (grade) {
    case "SEED":
      return new URL("@/assets/rank/seed.svg", import.meta.url).href;
    case "KIWI":
      return new URL("@/assets/rank/kiwi.svg", import.meta.url).href;
    case "APPLE":
      return new URL("@/assets/rank/apple.svg", import.meta.url).href;
    case "ORANGE":
      return new URL("@/assets/rank/orange.svg", import.meta.url).href;
    case "KOREAMELON":
      return new URL("@/assets/rank/koreamelon.svg", import.meta.url).href;
    case "DURIAN":
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

.rank-img {
  width: 40px;
  height: 40px;
  margin: 10px 10px;
}

.rank-exp {
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
