<script setup>
import { onMounted, ref } from "vue";
import announcement from "@/assets/data/announcement.json";
import notification from "@/assets/data/notification.json";
import AnnounceDetail from "@/components/home/modal/AnnounceDetail.vue";
import { getWaitingRoom, goWaitingRoom } from "@/api/waitingroom";
import { receiveRegular } from "@/api/board";
import { useModal } from "@/composables/useModal";
import { useMessageStore } from "@/stores/message";
import { useRouter } from "vue-router";

const { isModalOpen, openModal, closeModal } = useModal();

const messageStore = useMessageStore();

onMounted(() => {
  noti.value = messageStore.noti;

  noti.value.room.forEach((element) => {
    const success = (res) => {
      console.log(res.data);
      roomInvite.value.push({
        roomId: element,
        hostName: res.data.hostName,
      });
      getWaitingRoom(element, success, fail);
    };
  });
  const fail = (err) => {
    console.log(err);
  };
  const getReceiveRegular = (res) => {
    console.log(res.data);
  };

  receiveRegular({ userName: "김종덕" }, getReceiveRegular, fail);
  console.log(noti.value);
});
const router = useRouter();
const isSelected = ref("announ");
const announ = ref(announcement);
const noti = ref([]);
const roomInvite = ref([]);
const selectedAnnoun = ref(null);

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

  goWaitingRoom({ userName: "김종덕", sessionId: roomId }, success, fail);
};
</script>

<template>
  <div class="modal-overlay slideLeft" @click.self="$emit('close')">
    <div class="modal-content box-col">
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
          <div v-if="'room' in noti" class="room-con">
            <span>방 초대</span>
            <div
              v-for="(item, index) in roomInvite"
              :key="index"
              class="box-row announ-con"
            >
              <span>{{ item.hostName }}님이 초대하였습니다.</span>
              <div class="box-row">
                <button
                  class="btn-accept bold-text"
                  @click="goRoom(item.roomId)"
                >
                  수락
                </button>
                <button class="btn-reject bold-text">거절</button>
              </div>
            </div>
          </div>
          <div v-if="'friend' in noti" class="friend-con">
            <span>친구 신청</span>
            <div
              v-for="(item, index) in noti.friend"
              :key="index"
              class="box-row announ-con"
            >
              <span>{{ item.info }}</span>
              <div class="box-row">
                <div class="btn-accept">
                  <span>수락</span>
                </div>
                <div class="btn-reject">
                  <span>거절</span>
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
  </div>
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
  width: 550px;
  height: 700px;
  bottom: 50px;
  right: 30px;
  border: 3px solid #3b72ff;
  border-radius: 10px;
  background-color: #cadcff;
  padding: 20px 25px;
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

.friend-con {
  margin-top: 20px;
}

.room-con > span,
.friend-con > span {
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
