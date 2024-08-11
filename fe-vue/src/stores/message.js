import { ref } from "vue";
import { defineStore } from "pinia";
import { getWaitingRoom } from "@/api/waitingroom";

export const useMessageStore = defineStore("message", () => {
  const noti = ref({
    room: [],
    friend: [],
    regular: [],
  });

  const resetNoti = function () {
    noti.value = {
      room: [],
      friend: [],
      regular: [],
    };
  };

  const receiveNoti = function (param) {
    console.log(param, typeof param);
    if (param === ".") {
      console.log("친구추가");
    } else if (typeof param === Number) {
      console.log("방초대");
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

  const receiveFriend = function (message) {
    console.log(message);
    noti.value.friend = [];
    message.forEach((element) => {
      noti.value.friend.push(element);
    });
  };
  return {
    noti,
    receiveNoti,
    receiveInvite,
    receiveFriend,
    resetNoti,
  };
});
