<script setup>
import Logo from "@/components/Logo.vue";
import FriendList from "@/components/friend/FriendList.vue";
</script>

<template>
  <Logo class="logo" />

  <div>
    <FriendList />
  </div>
</template>

<style scoped>
.logo {
  margin-top: 15px;
  margin-left: 30px;
}
</style>
