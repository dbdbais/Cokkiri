<template>
  <div class="box-main-con box-col friend-con">
    <button class="add-friend-con box-row">
      <img src="@/assets/friend-plus.svg" alt="add-friend" class="icon" />
      <div class="title main-title btn-add-friend" @click="openModal">
        친구 추가
      </div>
    </button>
    <Accordion class="friend-list" />
    <AddFriend v-if="isModalOpen" @close="closeModal" />
  </div>
</template>

<script setup>
import Accordion from "@/components/home/Accordion.vue";
import AddFriend from "@/components/home/modal/AddFriend.vue";
import { useModal } from "@/composables/useModal";


const { isModalOpen, openModal, closeModal } = useModal();
</script>

<style scoped>
.friend-con {
  align-items: center;
}

.add-friend-con {
  justify-content: center;
  align-items: center;
  width: 95%;
  padding: 5px 10px;
  background-color: #81c3ff;
  border-width: 5px;
  border-radius: 10px;
  border-color: #b8cffe;
}

.btn-add-friend {
  margin-left: 12px;
  font-size: 40px;
}

.add-friend-con:hover {
  background-color: #5a88fd;
  border-color: #3b72ff;
}

.icon {
  width: 60px;
  height: 60px;
}

.friend-list {
  width: 280px;
  margin-top: 15px;
}
</style>
