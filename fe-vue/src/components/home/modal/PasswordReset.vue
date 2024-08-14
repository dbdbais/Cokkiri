<script setup>
import { ref } from "vue"
import { userStore } from "@/stores/user"
import { login, modify } from "@/api/user"

const uStore = userStore()
const verifyPw = ref(false)
const inputText = ref("")
const inputTextCheck = ref("")

const requestVerifyPw = async () => {
    try {
        const response = await login({ id: uStore.user.id, password: inputText.value })
        console.log(response)
        if (response.data === 1) {
            verifyPw.value = true
            swal.fire({
                icon: "success",
                title: "비밀번호가 일치합니다."
            })
            inputText.value = ""
        } else {
            swal.fire({
                icon: "error",
                title: "비밀번호가 일치하지 않습니다."
            })
        }
    } catch (e) {
        console.error(e)
    }

}

const resetPw = async () => {

    if (inputText.value !== inputTextCheck.value) {
        swal.fire({
            icon: "error",
            title: "비밀번호가 일치하지 않습니다."
        })
        return
    }
    try {
        const response = await modify({ id: uStore.user.id, password: inputText.value })
        console.log(response)
        if (response.data === 1) {
            swal.fire({
                icon: "success",
                title: "비밀번호가 초기화되었습니다."
            })
        } else {
            swal.fire({
                icon: "error",
                title: "비밀번호 초기화에 실패했습니다."
            })
        }
    } catch (error) {
        console.error(error)
    }
}
</script>

<template>
    <div class="modal-overlay">
        <div class="modal-con">
            <div class="modal-header box-row">
                <span class="title main-title">비밀번호 변경</span>
                <img src="@/assets/exit.svg" @click="$emit('close')">
            </div>
            <div class="modal-content">
                <div v-if="!verifyPw" class="box-col box-check">
                    <input type="password" placeholder="현재 비밀번호를 입력하세요" v-model="inputText" class="box-check-input">
                    <button @click="requestVerifyPw" class="btn-check">확인</button>
                </div>
                <div v-else class="box-col">
                    <input type="password" placeholder="새로운 비밀번호를 입력하세요" v-model="inputText">
                    <input type="password" placeholder="비밀번호를 다시 입력하세요" v-model="inputTextCheck">
                    <button @click="resetPw">변경</button>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.modal-overlay {
    background-color: rgba(0, 0, 0, 0) !important;
}

.modal-con {
    width: 500px;
    height: 350px;
    position: absolute;
    right: 150px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: #DBE7FF;
    padding: 20px;
    z-index: 1005;
}

.modal-header {
    justify-content: space-between;
}

.box-check {
    width: 100%;
    height: 250px;
    justify-content: space-between;
    align-items: center;
}

.box-check-input {
    width: 350px;
    height: 50px;
    border: 2px solid black;
    border-radius: 10px;
    margin-top: 100px;
}

.btn-check {
    width: 75px;
    height: 45px;
    border-radius: 10px;
    border: 2px solid #DBE7FF;
    background-color: #3B72FF;
    font-weight: 800;
    color: white;
}
</style>