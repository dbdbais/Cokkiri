<script setup>
import { ref } from 'vue'
import { userStore } from "@/stores/user";
import chat from "@/assets/data/chat.json";

const chatList = ref(chat);
const store = userStore();
const props = defineProps({
    selectedMember: Object
});
</script>

<template>
    <div class="modal-overlay" @click.self="$emit('close')">
        <div class="chat-con box-main-group">
            <div class="modal-header box-row">
                <span class="title main-title">{{ props.selectedMember.name }}</span>
                <div>
                    <img src="@/assets/exit.svg" alt="close" class="icon" @click="$emit('close')" />
                </div>
            </div>
            <div class="chat-window box-main-con box-col">
                <div v-for="(chat, index) in chatList" class="msg-con box-col">
                    <div v-if="chat.senderId === store.user.id" class="msg sent box-main-exp">
                        <span class="title main-title">{{ chat.text }}</span>
                    </div>
                    <div v-else class="msg received box-main-exp">
                        <span class="title main-title">{{ chat.text }}</span>
                    </div>
                </div>
            </div>
            <div class="input-con box-main-con">
                <input type="text" value="5시에 들어오세요" />
                <img src="@/assets/send.svg" class="send-icon">
            </div>
        </div>
    </div>
</template>

<style scoped>
.chat-con {
    display: flex;
    flex-direction: column;
    position: absolute;
    width: 550px;
    bottom: 30px;
    left: calc(1920/2)px;
    padding: 15px 20px;
}

.modal-header {
    width: 500px;
    margin-left: 5px;
    justify-content: space-between;
    align-items: center;
}

.chat-window {
    height: 360px;
    margin-top: 10px;
    padding: 10px;
}

.msg-con {
    margin-bottom: 8px;
}

.msg {
    max-width: 60%;
    word-wrap: break-word;
    padding: 5px 10px;
}

.box-main-exp {
    border: 2px solid #81c3ff;
    border-radius: 5px;
}

.msg span {
    font-size: 22px;
}

.received {
    align-self: flex-start;
}

.sent {
    align-self: flex-end;
}

.input-con {
    position: relative;
    height: 60px;
    padding-left: 15px;
    padding-top: 6px;
    margin-top: 10px;
}

input {
    width: 460px;
    height: 40px;
    margin-right: 10px;
    padding: 5px;
    font-size: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.send-icon {
    position: absolute;
    width: 35px;
    top: 8px;
    right: 20px;
}
</style>
