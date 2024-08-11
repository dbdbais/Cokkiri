import { ref } from "vue";
import { defineStore } from "pinia";
import { getUser } from "@/api/user";
import { userStore } from "./user";
import { useTriggerStore } from "./trigger";

export const friendStore = defineStore("friend", () => {
  const uStore = userStore();
  const TStore = useTriggerStore();
  const allRelationships = ref([]);
  const friendList = ref([]);

  function setAllRelationships(newFriends) {
    allRelationships.value = newFriends;
  }

  async function setFriends(newFriends) {
    console.log("친구 목록 세팅하는 중 (4)");
    const time = new Date();
    console.log("시간: " + time);
    friendList.value = [];
    // console.log(newFriends);
    console.log(uStore.userNickname);
    newFriends.forEach((element) => {
      console.log(element);
      if (element.status === "ACCEPT") {
        friendList.value.push(uStore.userNickname[element.friendUserId]);
      }
    });

    console.log(friendList.value);
    TStore.isTrigger();
  }

  return {
    allRelationships,
    friendList,
    setFriends,
    setAllRelationships,
  };
});
