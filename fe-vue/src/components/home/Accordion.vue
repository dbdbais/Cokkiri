<template>
    <div class="accordion">
        <div v-for="(item, index) in items" :key="index" class="accordion-category">
            <div class="accordion-header box-main-group" >
                <span class="title header-title">{{ item.title }}</span> 
                <button class="open-btn nomal-text md" @click="toggle(index)">
                    {{ openIndex[index] === null ? '+' : '-'}}
                </button>
            </div>
            <div class="accordion-item" :class="isOpen(index)? 'open':''">
                <div v-for="(member, index2) in item.member" :key="index2" class="box-main-exp box-content"
                >
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

const openIndex = ref([]);

for (let i=0; i<items.value.length; i++) {
    openIndex.value.push(null)
}

const toggle = (index) => {
    openIndex.value[index] = openIndex.value[index] === index ? null : index;
};

const isOpen = (index) => {
    return openIndex.value[index] === index;
};

const { isModalOpen, selectedMember, openModal, closeModal } = useModal();
</script>

<style scoped>
.accordion-category {
    margin-top: 1px;
    
}
.accordion-item {
    transition: max-height 0.3s ease-in-out;
    max-height: 0;
    overflow-y: scroll;
}
.open {
  max-height: 180px;
  transition: max-height 0.3s ease-in-out;
}

.open-btn {
    width: 20px;
    height: 20px;
    font-size: 15px;
    
    background-color: #DBE7FF;
    color: #3b72ff;
    border-width: 3px;
    border-radius: 5px;
    border-color: #3b72ff;
}
.accordion-header {
    height: 35px;
    padding: 2px 5px;
    display: flex;
    justify-content: space-between;
    align-items: center;
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


</style>
