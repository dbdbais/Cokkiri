<script setup>
import { ref } from 'vue';
import announcement from '@/assets/data/announcement.json';
import notification from '@/assets/data/notification.json';
import AnnounceDetail from '@/components/home/modal/AnnounceDetail.vue';
import { useModal } from '@/composables/useModal';

const { isModalOpen, openModal, closeModal } = useModal();

const isSelected = ref('announ');
const announ = ref(announcement);
const noti = ref(notification);
const selectedAnnoun = ref(null);

const openAnnounceModal = (content) => {
    selectedAnnoun.value = content;
    openModal();
}

</script>

<template>
    <div class="modal-overlay" @click.self="$emit('close')">
        <div class="modal-content box-col">
            <div class="modal-header box-row">
                <img src="@/assets/mail2.svg" alt="mail-icon" class="mail-icon">
                <span>우편함</span>
                <img src="@/assets/mail-close.svg" alt="close-icon" class="close-icon" @click="$emit('close')">
            </div>
            <div class="type-con box-row">
                <input id="announ-in" type="radio" name="type" value="announ" v-model="isSelected" />
                <label for="announ-in">공지</label>
                <input id="noti-in" type="radio" name="type" value="noti" v-model="isSelected" />
                <label for="noti-in">알림</label>
            </div>
            <div class="content-con">
                <div v-if="isSelected == 'announ'">
                    <div v-for="(item, index) in announ" :key="index" class="box-row announ-con">
                        <span>{{ item.title }}</span>
                        <div class="btn-detail">
                            <span @click="openAnnounceModal(item.content)">자세히</span>
                        </div>
                    </div>
                </div>
                <div v-if="isSelected == 'noti'">
                    <div v-if="'room' in noti" class="room-con">
                        <span>방 초대</span>
                        <div v-for="(item, index) in noti.room" :key="index" class="box-row announ-con">
                            <span>{{ item.info }}</span>
                            <div class="box-row">
                                <div class="btn-accept">
                                    <span>수락</span>
                                </div>
                                <div class="btn-reject">
                                    <span>거절</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div v-if="'friend' in noti" class="friend-con">
                        <span>친구 신청</span>
                        <div v-for="(item, index) in noti.friend" :key="index" class="box-row announ-con">
                            <span>{{ item.info }}</span>
                            <div class="box-row">
                                <div class="btn-accept">
                                    <span>수락</span>
                                </div>
                                <div class="btn-reject">
                                    <span>거절</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <AnnounceDetail v-if="isModalOpen" @close="closeModal" :announce="selectedAnnoun" />
    </div>
</template>

<style scoped>
span,
label,
div {
    -webkit-text-stroke: black 1px;
    /* background-color: transparent; */
}

.modal-content {
    position: absolute;
    width: 550px;
    height: 700px;
    bottom: 50px;
    right: 30px;
    border: 3px solid #3B72FF;
    background-color: #CADCFF;
    padding: 20px 25px;
}

/* header */

.modal-header {
    position: relative;
    align-items: center;
    padding-left: 15px;
}

.mail-icon {
    width: 70px;
    margin-right: 10px;
}

.modal-header span {
    font-size: 30px;
}

.close-icon {
    position: absolute;
    right: 5px;
    width: 45px;
}

/* type */
.type-con {
    margin-top: 25px;
}

.type-con input {
    appearance: none;
}

.type-con input+label {
    border: 3px solid #3B72FF;
    border-radius: 6px 6px 0 0;
    background-color: #DBE7FF;
    padding: 10px 20px;
    font-size: 25px;
}

.type-con input:checked+label {
    border: 3px solid #3B72FF;
    background-color: #C191FF;
}

/* content */
.content-con {
    height: 500px;
    border: 3px solid #3B72FF;
    border-radius: 0 5px 5px 5px;
    background-color: #C191FF;
    padding: 10px 20px;
}

.announ-con {
    justify-content: space-between;
    align-items: center;
    padding: 10px 15px;
    margin-top: 10px;
    border: 2px solid #3B72FF;
    border-radius: 6px;
    background-color: #DBE7FF;
}

.announ-con>span {
    font-size: 22px;
}



.btn-detail {
    border: 2px solid#81C3FF;
    background-color: #3B72FF;
    font-size: 18px;
    padding: 5px 10px;
}

.friend-con {
    margin-top: 20px;
}

.room-con>span,
.friend-con>span {
    font-size: 25px;
}

.btn-accept {
    border: 4px solid #81C3FF;
    background-color: #3B72FF;
    border-radius: 8px;
    padding: 3px;
    font-size: 22px;
}

.btn-reject {
    border: 4px solid #FE9D9D;
    background-color: #FF0000;
    border-radius: 8px;
    padding: 3px;
    font-size: 22px;
}
</style>