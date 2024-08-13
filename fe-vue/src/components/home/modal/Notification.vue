<script setup>
import { onMounted, onUnmounted, ref } from "vue";
import announcement from "@/assets/data/announcement.json";
import notification from "@/assets/data/notification.json";
import AnnounceDetail from "@/components/home/modal/AnnounceDetail.vue";
import { getWaitingRoom, goWaitingRoom } from "@/api/waitingroom";
import { getFriends, getUser, acceptFriend } from "@/api/user";
import { receiveRegular, acceptRegularJoin } from "@/api/board";
import { useModal } from "@/composables/useModal";
import { userStore } from "@/stores/user";
import { useMessageStore } from "@/stores/message";
import { useRouter } from "vue-router";
import { friendStore } from "@/stores/friend";

const { isModalOpen, openModal, closeModal } = useModal();
const store = userStore();
const messageStore = useMessageStore();

const notiRequest = ref({
  friends: [],
  room: [],
  regular: [],
});

function getNotiData() {
  notiRequest.value = {
    friends: [],
    room: [],
    regular: [],
  };

  messageStore.noti.room.forEach((room) => {
    notiRequest.value.room.push(room);
  });

  receiveRegular({ userName: store.user.nickname })
    .then((res) => {
      console.log(res.data);
      res.data.forEach((element) => {
        notiRequest.value.regular.push(element);
      });
    })
    .catch((err) => console.log(err));
  messageStore.noti.friend.forEach((friend) => {
    notiRequest.value.friends.push(friend);
  });
  getFriends(store.user.id).then((res) => {
    res.data.forEach((element) => {
      if (element.status === "SELECT") {
        console.log(element);
        notiRequest.value.friends.push(element.friendUserId);
      }
    });
  });
}

onMounted(() => {
  getNotiData();
  messageStore.readNoti();
});

onUnmounted(() => {
  messageStore.resetRoomInvite();
});

const router = useRouter();
const isSelected = ref("announ");
const announ = ref(announcement);
const fStore = friendStore();
const selectedAnnoun = ref(null);

async function addFriend(friendUserId) {
  await acceptFriend(store.user.id, friendUserId)
    .then((res) => {
      console.log(res.data);
    })
    .catch((err) => {
      console.log(err);
    });
  // setTimeout(
  //   getFriends(store.user.id).then((res) => {
  //     fStore.setFriends(res.data);
  //   }),
  //   1000
  // );

  getNotiData();
}

const acceptRegular = function (sessionId, userName) {
  acceptRegularJoin({ sessionId: sessionId, userName: userName })
    .then((res) => {
      console.log(res.data);
      getNotiData();
    })
    .catch((err) => {
      console.log(err);
    });
};

const openAnnounceModal = (content) => {
  selectedAnnoun.value = content;
  openModal();
};

const goRoom = function (roomId) {
  const success = (res) => {
    console.log(res.data);
    router.push({ name: "waitingRoom", params: { roomId: roomId } });
  };
  const fail = (err) => {
    console.log(err);
  };

  goWaitingRoom(
    { userName: store.user.nickname, sessionId: roomId },
    success,
    fail
  );
};
</script>

<template>
  <!-- <div class="modal-overlay"> -->
  <div class="modal-content box-col slideLeft">
    <div class="modal-header box-row">
      <img src="@/assets/mail2.svg" alt="mail-icon" class="mail-icon" />
      <span>우편함</span>
      <img src="@/assets/mail-close.svg" alt="close-icon" class="close-icon" @click="$emit('close')" />
    </div>
    <div class="type-con box-row">
      <input id="announ-in" type="radio" name="type" value="announ" v-model="isSelected" />
      <label for="announ-in">공지</label>
      <input id="noti-in" type="radio" name="type" value="noti" v-model="isSelected" />
      <label for="noti-in">알림</label>
    </div>
    <div class="content-con">
      <div v-if="isSelected == 'announ'">
        <div v-for="(item, index) in announ" :key="index" class="box-row announ-con">
          <span>{{ item.title }}</span>
          <div class="btn-detail">
            <span @click="openAnnounceModal(item.content)">자세히</span>
          </div>
        </div>
      </div>
      <div v-if="isSelected == 'noti'">
        <span class="noti-title">방 초대</span>
        <div class="announ-con box room-con">
          <div v-for="(item, index) in notiRequest.room" :key="index" class="box-row noti-common-request-con">
            <div v-if="index > 1" class="divider"></div>
            <span class="noti-common-request-text">{{ item.userName }}님이 초대하였습니다.</span>
            <div class="box-row">
              <button class="btn-accept bold-text" @click="goRoom(item.roomId)">
                수락
              </button>
              <button class="btn-reject bold-text">거절</button>
            </div>
          </div>
        </div>
        <span class="noti-title">친구 신청</span>
        <div class="announ-con box friend-con">
          <div v-for="(item, index) in notiRequest.friends" :key="index" class="box-col">
            <!-- <span>{{ store.userNickname[item] }}</span> -->
            <div v-if="index > 0" class="divider"></div>
            <div class="box-row noti-common-request-con">

              <span class="noti-common-request-text">{{ item }}</span>
              <div class="box-row">
                <button class="btn-accept bold-text" @click="addFriend(item)">
                  수락
                </button>
                <button class="btn-reject bold-text">거절</button>
              </div>
            </div>
          </div>
        </div>
        <span class="noti-title">정기 스터디</span>
        <div class="announ-con box regular-con">
          <div v-for="(regular, index) in notiRequest.regular" :key="index">
            <div v-for="(user, index) in regular.users" :key="index" class="box-row noti-common-request-con">
              <div class="flex-align">
                <div class="badge box md">{{ regular.regularName }}</div>
                <span style="margin-left: 10px" class="noti-common-request-text">{{ user }}</span>
              </div>
              <div class="box-row">
                <button class="btn-accept bold-text" @click="acceptRegular(regular.sessionId, user)">
                  수락
                </button>
                <button class="btn-reject bold-text">거절</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <AnnounceDetail v-if="isModalOpen" @close="closeModal" :announce="selectedAnnoun" />
  <!-- </div> -->
</template>

<style scoped>
span,
label,
div {
  -webkit-text-stroke: black 1.5px;
  /* background-color: transparent; */
}

.modal-content {
  position: absolute;
  width: 600px;
  height: 1000px;
  right: 20px;
  bottom: 20px;
  border: 3px solid #3b72ff;
  border-radius: 10px;
  background-color: #cadcff;
  padding: 20px 25px;
  z-index: 1000;
}

/* header */

.modal-header {
  position: relative;
  align-items: center;
  padding-left: 15px;
}

.mail-icon {
  width: 70px;
  margin-right: 10px;
}

.modal-header span {
  font-size: 30px;
}

.close-icon {
  position: absolute;
  right: 5px;
}

/* type */
.type-con {
  margin-top: 25px;
}

.type-con input {
  appearance: none;
}

.type-con input+label {
  border: 2px solid #3b72ff;
  border-radius: 6px 6px 0 0;
  background-color: #dbe7ff;
  padding: 10px 20px;
  font-size: 25px;
}

.type-con input:checked+label {
  border: 2px solid #3b72ff;
  background-color: #c191ff;
}

/* content */
.content-con {
  height: 800px;
  border: 2px solid #3b72ff;
  border-radius: 0 5px 5px 5px;
  background-color: #c191ff;
  padding: 10px 20px;
  overflow-y: auto;
}

.announ-con {
  justify-content: space-between;
  align-items: center;
  padding: 10px 15px;
  margin-top: 10px;
  border: 2px solid #3b72ff;
  border-radius: 6px;
  background-color: #dbe7ff;
}

.announ-con>span {
  font-size: 22px;
}

.noti-title {
  display: block;
  font-size: 25px;
  margin-top: 10px;
}

.btn-detail {
  border: 2px solid#81C3FF;
  background-color: #3b72ff;
  font-size: 18px;
  padding: 5px 10px;
}

.friend-con,
.regular-con {
  margin-top: 20px;
}

.friend-con,
.regular-con,
.room-con {
  height: 200px;
  padding: 10px;
  overflow-y: auto;
}

.room-con>span,
.friend-con>span,
.regular-con>span {
  font-size: 25px;
}

.badge {
  padding: 5px 10px;
  border: 2px solid;

  background-color: #c191ff;
}

.btn-accept,
.btn-reject {
  color: white;
  padding: 5px 10px;
  font-size: 18px;
}

.noti-common-request-con {
  justify-content: space-between;
  align-items: center;
}

.noti-common-request-text {
  font-size: 20px;
}

.btn-accept {
  border: 2px solid hsl(209, 100%, 75%);
  background-color: hsl(223, 100%, 62%);
  margin-right: 10px;
}

.btn-reject {
  border-color: hsl(0, 51%, 80%);
  background-color: hsl(0, 100%, 62%);
}

.divider {
  border-bottom: 2px dashed hsl(209, 80%, 50%);
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>
