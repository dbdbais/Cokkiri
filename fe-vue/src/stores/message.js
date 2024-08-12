import { ref } from "vue";
import { defineStore } from "pinia";
import { getWaitingRoom } from "@/api/waitingroom";

export const useMessageStore = defineStore("message", () => {
  const unReadNoti = ref(0);

  const noti = ref({
    room: [],
    friend: [],
    regular: [],
  });

  const receiveNoti = function (param) {
    unReadNoti.value += 1;
    console.log(unReadNoti.value);
    if (param === ".") {
      console.log("친구추가");
      // receiveFriend();
    } else if (param > 0) {
      console.log("방초대");
      receiveInvite(param);
    } else {
      console.log("정기 스터디 초대");
    }
  };

  const receiveInvite = function (message) {
    const success = (res) => {
      noti.value.room.push({ userName: res.data.hostName, roomId: message });
      console.log(res.data);
    };
    const fail = (err) => {
      console.log(err);
    };
    getWaitingRoom(message, success, fail);
  };

  function readNoti() {
    unReadNoti.value = 0;
  }

  return {
    noti,
    unReadNoti,
    receiveNoti,
    receiveInvite,
    readNoti,
  };
});
