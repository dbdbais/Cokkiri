<template>
    <div class="box-main-con">
        <button id="add-friend-container" class=" box-row">
            <img src="@/assets/friend-plus.svg" alt="add-friend" class="icon" />
            <div id="add-friend-btn" class="title main-title" @click="openModal">친구 추가</div>
        </button>
        <Accordion id="friend-list" />
        <AddFriend v-if="isModalOpen" @close="closeModal" />
    </div>
</template>

<script setup>
import Accordion from "@/components/home/Accordion.vue"
import AddFriend from "@/components/home/modal/AddFriend.vue";
import { useModal } from "@/composables/useModal";

const { isModalOpen, openModal, closeModal } = useModal();

</script>

<style scoped>
#add-friend-container {
    width: 250px;
    margin-bottom: 5px;
    padding: 10px 25px;

    background-color: #81c3ff;
    border-width: 5px;
    border-radius: 10px;
    border-color: #b8cffe;
}

#add-friend-btn {
    margin-left: 12px;
    font-size: 35px;
}
#add-friend-container:hover {
    background-color: #5a88fd;
    border-color: #3B72FF;
}
.icon {
    width: 40px;
    height: 40px;
}
#friend-list {
    width: 280px;
    margin-top: 15px;
}
</style>
