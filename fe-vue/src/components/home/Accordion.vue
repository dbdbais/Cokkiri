<template>
    <div class="accordion">
        <div v-for="(item, index) in items" :key="index" class="accordion-item">
            <div class="accordion-header box-main-group" @click="toggle(index)">
                <span class="title header-title">{{ item.title }}</span>
            </div>
            <div v-if="isOpen(index)" class="accordion-content">
                <div v-for="(member, index2) in item.member" :key="index2" class="box-main-exp box-content animate__animated animate__bounce"
                :class="isOpen(index)? 'animate__fadeInDown':'slideUp'">
                    <img class="friend-profile" src="@/assets/elephant-profile2.svg">
                    <slot :name="`content-${index2}`" class="title-member">
                        <span class="title friend-name">{{ member.name }}</span>
                    </slot>
                    <img src="@/assets/message.svg" class="message-icon" @click="openModal(member)">
                </div>
            </div>
        </div>
        <Chat v-if="isModalOpen" @close="closeModal" :selectedMember="selectedMember" />
    </div>
</template>

<script setup>
import { ref } from 'vue';
import friendList from "@/assets/data/friendList.json";
import { useModal } from "@/composables/useModal";
import Chat from "@/components/home/modal/Chat.vue";

const items = ref([]);

items.value.push({
    title: "친구",
    member: friendList.friendList.member
});

friendList.studyList.forEach(study => {
    items.value.push({
        title: study.title,
        member: study.member
    })
});

const openIndex = ref(null);

const toggle = (index) => {
    openIndex.value = openIndex.value === index ? null : index;
};

const isOpen = (index) => {
    return openIndex.value === index;
};

const { isModalOpen, selectedMember, openModal, closeModal } = useModal();
</script>

<style scoped>
.accordion-item {
    margin-top: 1px;
}

.accordion-header {
    height: 35px;
    padding: 3px 5px;
}

.box-main-group {
    border: 3px solid #3b72ff;
    border-radius: 1px;
}

.box-content {
    display: flex;
    position: relative;
    align-items: center;
    height: 60px;
    padding: 5px 15px;
}

.header-title {
    margin-left: 5px;
    font-size: 25px;
}

.box-main-exp {
    border-radius: 1px;
}

.friend-name {
    margin-left: 15px;
    font-size: 30px;
}

.friend-profile {
    width: 45px;
    height: 45px;
    border-radius: 100%;
}

.message-icon {
    position: absolute;
    width: 40px;
    right: 15px;
}

.open {
  animation-name: open;
  animation-duration: 0.8s;
  animation-timing-function: ease;

  visibility: visible !important;
}

@keyframes open {
  0% {
    transform: translateY(0%);
  }
  100% {
    transform: translateY(100%);
  }
}

</style>
