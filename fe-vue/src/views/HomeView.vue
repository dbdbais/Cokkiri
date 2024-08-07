<template>
  <div id="main-container" class="box-row">
    <div id="main-left" class="box-col">
      <Logo class="logo" />
      <div id="profile-btn" class="title main-title">개인 프로필</div>
      <Profile id="profile" />
      <FriendsList id="friends-list" />
    </div>
    <div id="main-right" class="box-col right">
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
</script>

<style>
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
  height: 500px;
  margin-top: 20px;
  padding: 15px;
}


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
</style>
