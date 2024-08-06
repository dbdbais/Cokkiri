import { ref } from "vue";
import { defineStore } from "pinia";

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

  const receiveInvite = function (message) {
    noti.value.room.push(message);
  };

  const receiveFriend = function (message) {
    noti.value.friend = [];
    message.forEach((element) => {
      noti.value.friend.push(element);
    });
  };
  return {
    noti,
    receiveInvite,
    receiveFriend,
    resetNoti,
  };
});
