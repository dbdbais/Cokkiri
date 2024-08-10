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

  function setFriends(newFriends) {
    friendList.value = [];
    console.log(newFriends);
    newFriends.forEach((element) => {
      if (element.status === "ACCEPT") {
        console.log(element.friendUserId);
        console.log(uStore.userNickname);
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
