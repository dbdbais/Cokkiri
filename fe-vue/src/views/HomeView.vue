<template>
  <div id="main-container" class="box-row">
    <div id="main-left" class="box-col">
      <Logo class="logo" />
      <div id="profile-btn" class="title main-title">개인 프로필</div>
      <Profile id="profile" />
      <FriendsList id="friends-list" />
    </div>
    <div id="main-right" class="box-col">
      <Header id="header" class="box-col" />
      <MainContent id="main-content" />
    </div>
  </div>
</template>

<script setup>
import Logo from "@/components/common/Logo.vue";
import Header from "@/components/home/Header.vue";
import Profile from "@/components/home/Profile.vue";
import FriendsList from "@/components/home/FriendsList.vue";
import MainContent from "@/components/home/MainContent.vue";
</script>

<style scoped>
/* 왼쪽 영역 */
#main-left {
  width: 350px;
  margin-left: 35px;
  margin-right: 35px;
}

#profile-btn {
  margin-top: 60px;
}

#profile {
  width: 340px;
  height: 130px;
  margin-top: 20px;
  padding: 15px;
}

#friends-list {
  width: 340px;
  height: 635px;
  margin-top: 20px;
  padding: 15px;
}

/* 오른쪽 영역 */
#main-right {
  width: 1400px;
  margin-left: 35px;
  margin-right: 35px;
}

#header {
  width: 1400px;
  height: 130px;
  margin-top: 20px;
}

#main-content {
  width: 1400px;
  height: 800px;
  margin-top: 20px;
  padding: 15px 50px;
}
</style>
