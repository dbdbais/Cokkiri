<script setup>
import { onMounted, ref } from "vue";
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

  getFriends(store.user.id)
    .then((res) => {
      res.data.forEach((friend) => {
        if (friend.status === "SELECT") {
          getUser(friend.friendUserId)
            .then((res) => {
              // console.log(res.data);
              if (
                !notiRequest.value.friends.some((element) => {
                  // console.log(element);
                  element.nickname === res.data.nickname;
                  return true;
                })
              ) {
                notiRequest.value.friends.push(res.data);
              }
              console.log(notiRequest.value.friends);
            })
            .catch((err) => {
              console.log(err);
            });
        }
      });
      console.log(notiRequest.value.friends);
    })
    .catch((err) => {
      console.log(err);
    });
}

onMounted(() => {
  getNotiData();
});

const router = useRouter();
const isSelected = ref("announ");
const announ = ref(announcement);
const noti = ref([]);
const roomInvite = ref([]);
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
  setTimeout(
    getFriends(store.user.id).then((res) => {
      fStore.setFriends(res.data);
    }),
    1000
  );

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
      <img
        src="@/assets/mail-close.svg"
        alt="close-icon"
        class="close-icon"
        @click="$emit('close')"
      />
    </div>
    <div class="type-con box-row">
      <input
        id="announ-in"
        type="radio"
        name="type"
        value="announ"
        v-model="isSelected"
      />
      <label for="announ-in">공지</label>
      <input
        id="noti-in"
        type="radio"
        name="type"
        value="noti"
        v-model="isSelected"
      />
      <label for="noti-in">알림</label>
    </div>
    <div class="content-con">
      <div v-if="isSelected == 'announ'">
        <div
          v-for="(item, index) in announ"
          :key="index"
          class="box-row announ-con"
        >
          <span>{{ item.title }}</span>
          <div class="btn-detail">
            <span @click="openAnnounceModal(item.content)">자세히</span>
          </div>
        </div>
      </div>
      <div v-if="isSelected == 'noti'">
        <div class="room-con box">
          <span>방 초대</span>
          <div
            v-for="(item, index) in notiRequest.room"
            :key="index"
            class="box-row announ-con"
          >
            <span>{{ item.userName }}님이 초대하였습니다.</span>
            <div class="box-row">
              <button class="btn-accept bold-text" @click="goRoom(item.roomId)">
                수락
              </button>
              <button class="btn-reject bold-text">거절</button>
            </div>
          </div>
        </div>
        <div class="friend-con box">
          <span>친구 신청</span>
          <div
            v-for="(item, index) in notiRequest.friends"
            :key="index"
            class="box-row announ-con"
          >
            <span>{{ item.nickname }}</span>
            <div class="box-row">
              <button class="btn-accept bold-text" @click="addFriend(item.id)">
                수락
              </button>
              <button class="btn-reject bold-text">거절</button>
            </div>
          </div>
        </div>
        <div class="regular-con box">
          <span>정기 스터디 (닉네임 [스터디 명])</span>
          <div v-for="(regular, index) in notiRequest.regular" :key="index">
            <div
              v-for="(user, index) in regular.users"
              :key="index"
              class="box-row announ-con"
            >
              <span>{{ user }} [{{ regular.regularName }}]</span>
              <div class="box-row">
                <button
                  class="btn-accept bold-text"
                  @click="acceptRegular(regular.sessionId, user)"
                >
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
  <AnnounceDetail
    v-if="isModalOpen"
    @close="closeModal"
    :announce="selectedAnnoun"
  />
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
  height: 700px;
  bottom: 50px;
  right: 330px;
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
  width: 45px;
}

/* type */
.type-con {
  margin-top: 25px;
}

.type-con input {
  appearance: none;
}

.type-con input + label {
  border: 3px solid #3b72ff;
  border-radius: 6px 6px 0 0;
  background-color: #dbe7ff;
  padding: 10px 20px;
  font-size: 25px;
}

.type-con input:checked + label {
  border: 3px solid #3b72ff;
  background-color: #c191ff;
}

/* content */
.content-con {
  height: 500px;
  border: 3px solid #3b72ff;
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

.announ-con > span {
  font-size: 22px;
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

.room-con > span,
.friend-con > span,
.regular-con > span {
  font-size: 25px;
}

.btn-accept,
.btn-reject {
  color: white;
  border-radius: 8px;
  padding: 3px;
  font-size: 22px;
  border-width: 4px;
}

.btn-accept {
  border-color: #81c3ff;
  background-color: #3b72ff;
  margin-right: 10px;
}

.btn-reject {
  border-color: #fe9d9d;
  background-color: #ff0000;
}
</style>
