<script setup>
import FriendList from "@/components/friend/FriendList.vue";
import Logo from "@/components/Logo.vue";
import { useRouter } from "vue-router";

const router = useRouter();
const goRegular = function () {
  router.push({ name: "studyBoard" });
};
</script>

<template>
  <span @click="goRegular">정기 스터디 모집 게시판</span>
  <Logo />
  <FriendList />
</template>
