<script setup>
import { ref } from "vue";
import { getAllUser, addFriend } from "@/api/user";
import { userStore } from "@/stores/user";

const store = userStore();
const friendList = ref();
const keyword = ref("");

const search = async () => {
    try {
        const response = await getAllUser();
        friendList.value = response.data;
        friendList.value = filterUsersById(keyword.value);
        console.log(response);
    } catch (e) {
        console.log(e);
    }
}

function filterUsersById(keyword) {
    return friendList.value.filter(user => user.id.toLowerCase().includes(keyword.toLowerCase()));
}

const add = async (friendId) => {
    try {
        const response = await addFriend(store.tUser.id, friendId);
        console.log(response);
    } catch (e) {
        console.log(e);
    }
}
</script>

<template>
    <div class="modal-overlay" @click.self="$emit('close')">
        <div class="modal-content box-main-group">
            <div class="modal-header box-row">
                <span class="title main-title">친구 추가</span>
                <div>
                    <img src="@/assets/exit.svg" alt="close" class="icon" @click="$emit('close')" />
                </div>
            </div>
            <div class="modal-body">
                <div class="search-con box-row box-main-con">
                    <span class="title main-title">검색</span>
                    <input type="text" placeholder="아이디 입력" class="nomal-text" v-model="keyword"
                        @keyup.enter="search" />
                    <button class="search-btn" @click="search">🔍</button>
                </div>
                <div class="box-main-con friend-list">
                    <div class="grid-container">
                        <div v-for="friend in friendList" :key="friend.id" class="box-row box-main-exp friend-item">
                            <img :src="'/src/assets/rank/' + friend.tier.toLowerCase() + '.svg'" alt="friend"
                                class="friend-profile" />
                            <div>
                                <span class="title main-title">{{ friend.nickname }}</span>
                            </div>
                            <img src="@/assets/plus-green.svg" class="friend-plus" @click="add(friend.id)">
                        </div>
                    </div>
                    <div class="box-row pagi-con">
                        <div class="box-row">
                            <div class="arrow-container">
                                <img class="arrow arrow-b" src="@/assets/arrow_left.svg"></img>
                            </div>
                            <div class="spacer"></div>
                            <div class="arrow-container">
                                <img class="arrow arrow-f" src="@/assets/arrow_right.svg"></img>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.modal-content {
    display: flex;
    flex-direction: column;
    position: absolute;
    width: 550px;
    bottom: 30px;
    left: 400px;
    padding: 15px 20px;
}

.modal-header {
    width: 500px;
    margin-left: 5px;
    justify-content: space-between;
    align-items: center;
}

.modal-body {
    width: 480px;
    margin-top: 20px;
    margin-left: 10px;
}

.search-con {
    position: relative;
    align-items: center;
    height: 70px;
    padding: 6px 20px;
    padding-right: 0;
}

.search-con .title {
    margin-top: 1px;
}

.search-con input {
    width: 380px;
    height: 38px;
    margin-right: 5px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    background-color: var(--secondary-fill-color);
    font-size: 28px;
    padding-left: 15px;
}

.search-con span {
    font-size: 26px;
    margin-right: 10px;
}

.search-btn {
    position: absolute;
    font-size: 20px;
    right: 20px;
    background: none;
    border: none;
}

.friend-list {
    margin-top: 20px;
    padding: 10px;
    height: 380px;
}

.grid-container {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    grid-template-rows: repeat(3, 1fr);
    height: 300px;
    gap: 10px;
}

.friend-profile {
    width: 40px;
    height: 40px;
}

.friend-item span {
    font-size: 20px;
    margin-left: 10px;
}

.friend-item {
    display: flex;
    position: relative;
    align-items: center;
    padding-top: 3px;
    padding-left: 15px;
}

.friend-item:hover {
    background-color: #CADCFF;
}

.friend-plus {
    position: absolute;
    right: 8px;
    width: 30px;
    height: 30px;
}

.pagi-con {
    justify-content: center;
}

.arrow-container {
    width: 100px;
    height: 35px;
    background-color: #e6e7e8;
    border-radius: 10px;
    margin-top: 10px;
    position: relative;
}

.arrow {
    width: 50px;
}

.arrow-b {
    position: absolute;
    top: -10px;
    left: 12px;
    margin-top: 3px;
    margin-left: 10px;
}

.arrow-f {
    position: absolute;
    top: -10px;
    right: 26px;
    margin-top: 3px;
    margin-left: 3px;
}

.spacer {
    width: 25px;
}
</style>