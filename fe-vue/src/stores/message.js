import { ref } from "vue";
import { defineStore } from "pinia";
import { getWaitingRoom } from "@/api/waitingroom";
import { getFriends } from "@/api/user";
import { userStore } from "./user";

export const useMessageStore = defineStore("message", () => {
  const uStore = userStore();
  const noti = ref({
    room: [],
    friend: [],
    regular: [],
  });

  const receiveNoti = function (param) {
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

  return {
    noti,
    receiveNoti,
    receiveInvite,
  };
});
