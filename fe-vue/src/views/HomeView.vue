<template>
  <Logo class="logo" />
  <div id="main-container" class="box-row">
    <div id="main-left" class="box-col">
      <div class="box-row profile-header">
        <div class="btn-profile title main-title">개인 프로필</div>
        <img class="myprofile-icon" src="@/assets/myprofile-smile.svg" @click="openModal" />
      </div>
      <Profile class="profile" />
      <FriendsList id="friends-list" />
    </div>
    <div id="main-right" class="box-col">
      <Header id="header" class="box-col" @create="getRoomList" @search="searchList" @go-room="goRightNow" />
      <MainContent id="main-content" :rooms="rooms" :current-page="currentPage" :category-obj="categoryObj"
        @go-room="goRoom" @change-page="pageChange" @is-game="categoryList" @refresh="refreshRoom" />
    </div>
  </div>
  <MyProfile v-if="isModalOpen" @close="closeModal" />
</template>

<script setup>
import "@/assets/css/home.css";
import Logo from "@/components/common/Logo.vue";
import Header from "@/components/home/Header.vue";
import Profile from "@/components/home/Profile.vue";
import FriendsList from "@/components/home/FriendsList.vue";
import MainContent from "@/components/home/MainContent.vue";
import MyProfile from "@/components/home/modal/MyProfile.vue";

import { getWaitingRoomList, goWaitingRoom } from "@/api/waitingroom";
import { getAllUser, getFriends, getUser } from "@/api/user";
import { insertClass } from "@/api/problem";
import { ref, onMounted, onUnmounted } from "vue";
import { useRouter } from "vue-router";
import { userStore } from "@/stores/user";
import { useModal } from "@/composables/useModal";
import { useMessageStore } from "@/stores/message";
import { useMeetingStore } from "@/stores/meeting";
import { friendStore } from "@/stores/friend";
import { useSubmitStore } from "@/stores/submit";
import { useItemStore } from "@/stores/item";
import { useCorrectStore } from "@/stores/correct";

const uStore = userStore();
const mStore = useMeetingStore();
const fStore = friendStore();
const cStore = useCorrectStore();
const iStore = useItemStore();
const submitStore = useSubmitStore();
const lobby = new WebSocket(
  `${process.env.VITE_VUE_SOCKET_URL}lobby/${uStore.user.nickname}`
);

const messageStore = useMessageStore();
const router = useRouter();
const currentPage = ref(1);
const category = ref(undefined);
const categoryObj = ref({
  all: true,
  game: false,
  study: false,
});
const { isModalOpen, openModal, closeModal } = useModal();

function refreshRoom() {
  currentPage.value = 1;
  getRoomList({ page: currentPage.value });
}

const searchList = function (roomName) {
  currentPage.value = 1;
  getRoomList({ roomName: roomName, page: currentPage.value });
};

const categoryList = function (isGame) {
  category.value = isGame;
  Object.keys(categoryObj.value).forEach((key) => {
    categoryObj.value[key] = false;
  });
  if (isGame === undefined) {
    categoryObj.value.all = true;
  } else if (isGame === true) {
    categoryObj.value.game = true;
  } else if (isGame === false) {
    categoryObj.value.study = true;
  }
  currentPage.value = 1;
  getRoomList({ isGame: isGame, page: currentPage.value });
};

const goRightNow = function () {
  const params = {
    isGame: category.value,
  };

  const availableRoom = ref([]);

  const success = (res) => {
    availableRoom.value = res.data.filter((element) => {
      return element.maxNum > element.users.length;
    });

    console.log(availableRoom.value);
    if (availableRoom.value.length > 0) {
      goRoom(availableRoom.value[0].sessionId);
    } else {
      Swal.fire({
        icon: "error",
        title: "방이 없습니다.",
      });
    }
  };
  const fail = (err) => {
    console.log(err);
  };
  getWaitingRoomList(params, success, fail);
  console.log("바로가기");
};

const goRoom = function (id) {
  console.log(id);
  const success = (res) => {
    console.log(res.data);
    router.push({ name: "waitingRoom", params: { roomId: id } });
  };
  const fail = (err) => {
    console.log(err);
  };
  goWaitingRoom(
    { sessionId: id, userName: uStore.user.nickname },
    success,
    fail
  );
};

async function addFriendFun() {
  await getAllUser().then((res) => {
    console.log("유저 닉네임 가져오기 (2)");
    uStore.setUserNickName(res.data);
  });
  console.log("친구 목록 가져오기 (3)");
  const time = new Date();
  console.log("시간: " + time);
  setTimeout(() => {
    getFriends(uStore.user.id).then((res) => {
      fStore.setFriends(res.data);
    });
  }, 3000);
}

lobby.onmessage = function (event) {
  let data = event.data.split("|!|");
  console.log(data);
  if (data.length === 4) {
    let event = data[2];
    let param = data[3];
    if (event === "NOTI") {
      console.log(event, param);
      messageStore.receiveNoti(param);
    } else if (event === "FRIADD") {
      console.log("친구 신청 완료! (1)");
      addFriendFun();
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

  getWaitingRoomList(params, success, fail);
};

onMounted(() => {
  getUser(uStore.user.id).then((res) => {
    uStore.setUser(res.data);
    console.log(res.data);
  });
  getRoomList({ page: currentPage.value });
  callInsertClass();
  iStore.setFontSize(20);
  cStore.resetCorrect();
  submitStore.resetSubmitList();
  const shareData = [];

  localStorage.setItem("shareData", JSON.stringify(shareData));
  mStore.clearHint();
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

const callInsertClass = async () => {
  try {
    const response = await insertClass();
    console.log(response.data);
  } catch (e) {
    console.log(e);
  }
};

const logout = function () {
  uStore.logout();
  router.replace({ name: "login" });
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

.btn-profile {
  /* text-align: center; */
  margin-top: 150px;
  font-size: 40px;
}

.myprofile-icon {
  width: 50px;
  height: 50px;
  border: 5px solid hsl(223, 60%, 50%);
  border-radius: 10px;
  padding: 3px;
  background-color: hsl(223, 100%, 80%);
  margin-right: 11px;
}

.myprofile-icon:hover {
  border: 5px solid hsl(223, 80%, 50%);
  background-color: hsl(223, 100%, 90%);
}

.profile {
  width: 340px;
  height: 150px;
  margin-top: 15px;
  padding: 8px 15px;
}

#friends-list {
  width: 340px;
  height: 635px;
  margin-top: 15px;
  padding: 15px;
  overflow-y: auto;
}

.profile-header {
  justify-content: space-between;
  align-items: end;
}

.logout {
  font-size: 22px;
  margin-right: 10px;
  border: 4px solid hsl(0, 90%, 53%);
  border-radius: 10px;
  background-color: hsl(0, 71%, 53%);
  width: 110px;
  height: 40px;
}

.logout:hover {
  background-color: hsl(0, 100%, 53%);
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
