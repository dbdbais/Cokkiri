<template>
  <div id="main" class="box-row">
    <Logo class="logo" />
    <!-- 스터디 모집 게시판 클릭 시 이동(이동 후 스터디 모집 게시판 글자 삭제) -->
    <RouterLink :to="{ name: 'studyRecruitment' }" v-if="linkStore.studyRecruitmentlink"
      @click="linkStore.gostudyRecruitment">스터디 모집 게시판</RouterLink>
    <div class="box-col">
      <Profile />
      <FriendsList />
    </div>
    <div class="box-col">
      <div class="box-col">
        <Header />
      </div>
      <div class="main-container">
        <MainContent />
      </div>
    </div>
  </div>
</template>

<script setup>
import Logo from "@/components/Logo.vue";

import Header from '@/components/home/Header.vue'
import Profile from '@/components/home/Profile.vue'
import FriendsList from '@/components/home/FriendsList.vue'
import MainContent from '@/components/home/MainContent.vue'

import { RouterLink, RouterView, useRouter } from "vue-router";

import { useLinkStore } from "@/stores/link";
const router = useRouter();
const linkStore = useLinkStore();
</script>

<style>
#main {
  width: 100vw;
  height: 100vh;
}

.main-container {
  display: flex;
  flex: 1;
}
</style>
