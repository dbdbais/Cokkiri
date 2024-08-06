<template>
  <Logo class="logo" />
  <div id="main-container" class="box-row">
    <div id="main-left" class="box-col">
      <div id="profile-btn" class="title main-title">개인 프로필</div>
      <Profile id="profile" />
      <FriendsList id="friends-list" />
    </div>
    <div id="main-right" class="box-col">
      <Header id="header" class="box-col" @create="getRoomList" @search="searchList"/>
      <MainContent
        id="main-content"
        :rooms="rooms"
        :current-page="currentPage"
        @change-page="pageChange"
        @is-game="categoryList"
      />
    </div>
  </div>
</template>

<script setup>
import Logo from "@/components/common/Logo.vue";
import Header from "@/components/home/Header.vue";
import Profile from "@/components/home/Profile.vue";
import FriendsList from "@/components/home/FriendsList.vue";
import MainContent from "@/components/home/MainContent.vue";
import { getWaitingRoomList } from "@/api/waitingroom";
import { ref, onMounted, onUnmounted } from "vue";
import "@/assets/css/home.css";
import { useMessageStore } from "@/stores/message";

const lobby = new WebSocket(`ws://i11e108.p.ssafy.io/lobby/abc`);
const messageStore = useMessageStore();
const currentPage = ref(1);
const searchList = function (roomName) {
  getRoomList({roomName: roomName})
}

const categoryList = function (isGame) {
  getRoomList({isGame: isGame})
}

lobby.onmessage = function (event) {
  let data = event.data.split("|!|");
  console.log(data);
  if (data.length === 4) {
    let event = data[2];
    let param = data[3];
    if (event === "ROOM") {
      console.log(event, param);
      messageStore.receiveInvite(param);
    }
  }
};

const rooms = ref("");

const getRoomList = function (params) {
  const success = (res) => {
    console.log(res.data);
    rooms.value = res.data;
    if (currentPage.value > 1 && rooms.value.length === 0) {
      pageChange(0);
    }
  };
  const fail = (err) => {
    console.log(err);
  };

  console.log(params);
  getWaitingRoomList(params, success, fail);
};

onMounted(() => {
  getRoomList();
  messageStore.resetNoti();
});

onUnmounted(() => {
  lobby.close();
});

const pageChange = function (motion) {
  if (motion === 1) {
    currentPage.value += 1;
  } else if (motion === 0) {
    currentPage.value -= 1;
    if (currentPage.value === 0) {
      currentPage.value = 1;
    }
  }
  console.log("motion: ", motion);
  console.log(currentPage.value);
  getRoomList({ page: currentPage.value });
};
</script>

<style scoped>
* {
  font-family: "RixInooAriDuriR";
  color: white;
}

/* 왼쪽 영역 */
#main-left {
  width: 350px;
  margin-left: 35px;
  margin-right: 35px;
}

#profile-btn {
  margin-top: 150px;
  font-size: 40px;
}

#profile {
  width: 340px;
  height: 150px;
  margin-top: 15px;
  padding: 15px;
}

#friends-list {
  width: 340px;
  height: 635px;
  margin-top: 15px;
  padding: 15px;
  overflow-y: auto;
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
  margin-top: 60px;
  padding: 15px 50px;
}
</style>
