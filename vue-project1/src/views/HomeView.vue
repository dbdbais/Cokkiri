<template>
  <div id="main" class="box-row">
    <Logo class="logo" />

    <div class="title main-title" @click="() => {
        router.push({ name: 'studyBoard' });
      }
      ">
      스터디 모집 게시판
    </div>

    <div class="box-col">

      <Profile />
      <FriendsList />
    </div>
    <div class="box-col right">
      <Header />
      <div class="main-container">
        <MainContent />
      </div>
    </div>
  </div>
</template>

<script setup>
import Logo from "@/components/common/Logo.vue";

import Header from "@/components/home/Header.vue";
import Profile from "@/components/home/Profile.vue";
import FriendsList from "@/components/home/FriendsList.vue";
import MainContent from "@/components/home/MainContent.vue";

import { RouterLink, useRouter } from "vue-router";

const router = useRouter();
</script>

<style>
#main {
  width: 100vw;
  height: 100vh;
}

.left {
  width: 300px;
  margin-left: 20px;
  margin-right: 20px;
}

.right {
  width: 1500px;
  margin-left: 20px;
  margin-right: 20px;
}

.main-container {
  display: flex;
  flex: 1;
}
</style>
