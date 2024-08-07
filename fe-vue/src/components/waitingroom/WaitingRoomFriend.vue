<script setup>
import { getFriend } from "@/api/friend";
import { inviteFriend } from "@/api/friend";
import { getUser } from "@/api/user";
import { onMounted, ref } from "vue";
import { userStore } from "@/stores/user";

const store = userStore();

onMounted(() => {
  getFriend(store.user.nickname).then((res) => {
    friendList.value = res.data;
  });
});

defineProps({
  roomId: Number,
});

const friendInvite = function (user, roomId) {
  getUser(user)
    .then((res) => {
      console.log(res.data);
      inviteFriend(
        { sessionId: roomId, userName: res.data.nickname },
        success,
        fail
      );
      console.log(roomId);
    })
    .catch((err) => {
      console.log(err);
    });

  const success = (res) => {
    console.log(res.data);
  };

  const fail = (err) => {
    console.log(err);
  };

  // inviteFriend({}, success, fail);
};

const friendList = ref([]);
</script>

<template>
  <div class="friend-invite box box-col slideDown">
    <div class="top box-row">
      <span class="title">친구 초대</span>
      <img src="/src/assets/exit.svg" alt="나가기" @click="$emit('close')" />
    </div>
    <div class="friend-content box">
      <div
        class="friend-item box box-row md nomal-text"
        v-for="friend in friendList"
        :key="friend.id"
      >
        {{ friend.friendUserId }}
        <button
          class="plus nomal-text"
          @click="friendInvite(friend.friendUserId, roomId)"
        >
          초대
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.friend-invite {
  position: absolute;
  width: 500px;
  height: 550px;
  left: 700px;
  top: 200px;
  background-color: #c191ff;
}
.top {
  width: 96%;
  align-items: center;
  justify-content: space-between;
}
.title {
  font-size: 30px;
  margin: 20px 0px 0px 20px;
}
.friend-content {
  width: 450px;
  height: 450px;
  margin: 10px 20px;
  background-color: #cadcff;
  padding: 10px;
  display: grid;
  grid-template-columns: 200px 200px;
  grid-template-rows: 100px 100px 100px;
  grid-gap: 20px;
}

.friend-item {
  align-content: center;
  justify-content: space-between;
  padding: 0px 10px;

  background-color: white;
}
.plus {
  font-size: 15px;
  padding: 3px 5px;
  background-color: #ff8888;
  color: white;

  border-radius: 10px;
  border-width: 5px;
  border-color: #ff8888;
}
</style>
