<script setup>
import { ref } from 'vue';
import { createWaitingRoom } from '@/api/waitingroom';
import { userStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const store = userStore();
const emit = defineEmits('create')
const people = ref(1);
const placeholder = ref('규칙을 입력해주세요.');
const roomName = ref("")
const isGame = ref(false)
const isOpen = ref(true)
const roomComment = ref("")
const ruleValue = ref('');
const router = useRouter()

const ruleList = ref([
    "상호간에 예의를 지킵시다"
])

const plus = () => {
    if (people.value > 1) {
        people.value--;
    }
};

const minus = () => {
    if (people.value < 6) {
        people.value++;
    }
};

const selectTime = () => {
    ruleValue.value = '시간 약속을 잘 지킵시다!';
};

const selectProblem = () => {
    ruleValue.value = '약속한 문제를 꼭 풀어오자!';
};

const selectManner = () => {
    ruleValue.value = '상호간에 예의를 지킵시다!';
};

const addrule = () => {
    if (ruleValue.value === '') {
        return;
    }
    ruleList.value.push(ruleValue.value);
    ruleValue.value = '';
};

const deleterule = (index) => {
    ruleList.value.splice(index, 1);
};

const createRoom = function () {
    const success = (res) => {
        console.log(res.data)
        router.push({ name: "waitingRoom", params: { roomId: res.data } });
        emit('create')
        emit('close')
    }
    const fail = (err) => {
        console.log(err)
    }

    const roomData = {
        roomName: roomName.value,
        userName: store.user.nickname,
        roomComment: roomComment.value,
        rules: ruleList.value,
        maxNum: people.value,
        isGame: isGame.value,
        isOpen: isOpen.value
    }
    console.log(roomData)
    createWaitingRoom(roomData, success, fail)
}

</script>

<template>
    <div class="modal-overlay">
        <div class="modal-content box-main-create-room box-row slideDown">
            <img src="/src/assets/exit.svg" alt="나가기" class="exit" @click.self="$emit('close')">
            <div class="left-con box-col">
                <div class="title-con box-row">
                    <span class="title main-title">제목</span>
                    <input type="text" class="nomal-text" v-model="roomName" required />
                </div>
                <div class="description-con box-col">
                    <span class="title main-title">소개 문구</span>
                    <textarea class="nomal-text" v-model="roomComment"></textarea>
                </div>
                <div class="type-con box-row">
                    <div class="box-col type">
                        <span class="title main-title">유형
                        </span>
                        <div class="box-row radio-box">
                            <input id="study" type="radio" name="type" class="study-radio" /><label for="study"
                                class="normal-text md" @click="() => isGame = false">공부</label>
                            <input id="game" type="radio" name="type" class="game-radio" /><label for="game"
                                class="normal-text md" @click="() => isGame = true">게임</label>
                        </div>
                        <div class="open-box title box-row">
                            공개
                            <input type="checkbox" class="open" v-model="isOpen">
                        </div>

                    </div>
                    <div class="box-row people" style="height: 50px;">
                        <span class="title main-title">인원</span>
                        <div>
                            <span @click="plus">-</span>
                            <input type="text" class="nomal-text" disabled :value="people" />
                            <span @click="minus">+</span>
                        </div>
                    </div>
                </div>

                <button class="title main-title create-btn" @click="createRoom">방 만들기</button>

            </div>
            <div class="right-con box-col">
                <span class="title main-title">규칙</span>
                <div class="rule-con box-col">
                    <div class="template-con box-row">
                        <div class="box-main-con">
                            <span class="title" @click="selectTime">시간 약속</span>
                        </div>
                        <div class="box-main-con"><span class="title" @click="selectProblem">문제</span></div>
                        <div class="box-main-con"><span class="title" @click="selectManner">예의</span></div>
                    </div>
                    <div class="input-con">
                        <input type="text" class="nomal-text" maxlength="12" :placeholder="placeholder"
                            v-model="ruleValue" />
                        <button @click="addrule" class="rule-plus nomal-text md">+</button>
                    </div>
                    <div class="rule-list-con box-col">
                        <span class="title main-title">규칙 목록</span>
                        <div class="rule-list scrollable-box">
                            <div v-for="(item, index) in ruleList" :key="index" class="box-main-con rule"> <span
                                    class="title">{{
                                        item }}
                                </span>
                                <img src="@/assets/delete_btn.svg" @click="deleterule(index)"></img>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
/* ============================ */
/*        Modal Content         */
/* ============================ */

.modal-content {
    position: absolute;
    width: 800px;
    top: calc(1080/2)px;
    left: calc(1920/2)px;
    padding: 25px;
}

/* ============================ */
/*        Left Container        */
/* ============================ */

.left-con {
    width: 360px;
}

/* Title Container */

.title-con span {
    margin-right: 20px;
}

.title-con input {
    width: 285px;
}


/* Description Container */

.description-con {
    margin-top: 20px;
}

.description-con textarea {
    height: 100px;
    margin-top: 10px;
}

/* Type Container */

.type-con {
    margin-top: 20px;
}

.type {
    width: 50%;
    position: relative;
}

.type div {
    margin-top: 15px;
}

.type input[type="radio"] {
    appearance: none;
}

.type input[type="radio"]+label {
    width: 150px;
    height: 50px;
    background-color: #fff;
    border: 5px solid #CADCFF;
    border-radius: 10px;
    /* padding: 5px 10px; */
    color: #2d87ef;
    font-size: 25px;
}

.type input[type="radio"]+label:hover {
    background-color: #2d87ef;
    color: #CADCFF;
}

.type input[type="radio"]:checked+label {
    background-color: #3B72FF;
    border: 5px solid #7498f3;
    color: rgb(255, 255, 255);
}

.open-box {
    width: 130px;
    align-items: center;

    font-size: 30px;
    position: absolute;
    bottom: 5px;
    right: -145px;
}

.open-box>input {
    width: 30px;
    height: 30px;
    margin-left: 10px;
    border: 3px solid black;

}

.game-radio {
    margin-left: 10px;
}

.people {
    position: relative;
    width: 50%;
    margin-left: 15px;
}

.people input {
    margin-left: 10px;
    width: 100px;
    height: 35px;
    font-size: 22px;
    text-align: center;
}

.people div {
    position: relative;
}

.people div span:nth-of-type(1) {
    position: absolute;
    top: -0.5px;
    left: 15px;
    font-size: 35px;
    color: #3a5eff;
}

.people div span:nth-of-type(2) {
    position: absolute;
    top: 5px;
    right: 5px;
    font-size: 25px;
    color: #3a5eff;

}

/* Create Room Button */

.create-room {
    margin-top: 30px;
    padding-top: 10px;
    padding-bottom: 10px;
    justify-content: center;
    align-items: center;
}


/* ============================ */
/*        Right Container       */
/* ============================ */

.right-con {
    margin-left: 30px;
}


/* rule Container */

.rule-con {
    background-color: var(--third-fill-color);
    border: 3px solid black;
    border-radius: 4px;
    width: 350px;
    height: 360px;
    margin-top: 10px;
    padding: 10px 15px;
}

.rule-plus {
    width: 25px;
    height: 25px;
    font-size: 25px;

    background-color: #CADCFF;
    color: #3a5eff;

    border-radius: 5px;
    border-width: 3px;
    border-color: #3a5eff;

    position: absolute;
    right: 10px;
    top: 10px;
}

/* Template Container */
.template-con {
    justify-content: space-between;
    padding: 0px 10px;
}

.template-con div {
    padding: 3px 15px;
}

.template-con span {
    font-size: 20px;
}

/* Input Container */
.nomal-text {
    font-size: 20px;
}

.input-con {
    position: relative;
    margin-top: 15px;
}

.title-con>input,
.input-con input,
textarea {
    border: 3px solid black;
    border-radius: 5px;
    padding-left: 5px;
}

.people input {
    border: 3px solid #3a5eff;
    border-radius: 5px;

}

.input-con input {
    width: 100%;
    padding: 5px 0 5px 10px;
}

.input-con span {
    position: absolute;
    top: 2px;
    right: 9px;
    font-size: 40px;
    color: #3a5eff;
}

/* rule Container */
.rule-list-con {
    margin-top: 15px;
    width: 315px;
    height: 200px;
}

.rule-list-con span {
    font-size: 20px;
}

.rule {
    position: relative;
    padding: 5px 10px;
    margin-top: 8px;
}

.rule img {
    position: absolute;
    right: 5px;
    top: 4px;
    width: 25px;
    height: 25px;
}

.create-btn {
    margin-top: 20px;
    padding: 5px 0;
    background-color: #627fff;
    border-color: #3a5eff;
    font-size: 35px;
}

.scrollable-box {
    height: 200px;
    padding: 10px;
    overflow-y: scroll;
}

/* 스크롤바 스타일 */
.scrollable-box::-webkit-scrollbar {
    width: 12px;
}

.scrollable-box::-webkit-scrollbar-thumb {
    background-color: #888;
    border-radius: 6px;
}

.scrollable-box::-webkit-scrollbar-thumb:hover {
    background-color: #555;
}

.scrollable-box::-webkit-scrollbar-track {
    background-color: #f1f1f1;
    border-radius: 6px;
}
</style>