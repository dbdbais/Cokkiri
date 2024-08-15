<script setup>
import { ref } from "vue"
import { userStore } from "@/stores/user"
import { login, setModPassword } from "@/api/user"

const uStore = userStore()
const verifyPw = ref(false)
const curPw = ref("")
const newPw = ref("")
const newPwCheck = ref("")
const emit = defineEmits(["close"])

const requestVerifyPw = async () => {
    try {
        const response = await login({ id: uStore.user.id, password: curPw.value })
        console.log(response)
        if (response.data === 1) {
            verifyPw.value = true
            swal.fire({
                icon: "success",
                title: "비밀번호가 일치합니다."
            })
            curPw.value = ""
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

    if (newPw.value !== newPwCheck.value) {
        swal.fire({
            icon: "error",
            title: "두 비밀번호가 일치하지 않습니다."
        })
        return
    }
    try {
        const response = await setModPassword({ id: uStore.user.id, password: newPw.value })
        console.log(response)
        if (response.data === 1) {
            swal.fire({
                icon: "success",
                title: "비밀번호가 변경되었습니다."
            })
            newPw.value = ""
            newPwCheck.value = ""
            emit("close")
        } else {
            swal.fire({
                icon: "error",
                title: "비밀번호 변경에 실패했습니다."
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
                <img src="@/assets/exit.svg" @click="$emit('close')" class="close-icon">
            </div>
            <div class="modal-content">
                <div v-if="!verifyPw" class="box-col box-check">
                    <label for="password" class="cur-label">현재 비밀번호 입력</label>
                    <input id="password" type="password" v-model="curPw" class="check-input">
                    <button @click="requestVerifyPw" class="btn-check">확인</button>
                </div>
                <div v-else class="box-col box-check">
                    <label for="new-password" class="new-pw-label">새 비밀번호 입력</label>
                    <input type="password" v-model="newPw" class="new-pw-input">
                    <label for="new-password-check" class="new-pw-check-label">비밀번호 확인</label>
                    <input type="password" v-model="newPwCheck" class="new-pw-check-input">
                    <button @click="resetPw" class="btn-check">변경</button>
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
    height: 500px;
    position: absolute;
    left: 250px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: #DBE7FF;
    padding: 20px;
    z-index: 1005;
}

.modal-header {
    position: relative;
    width: 450px;
    text-align: center;
}

.modal-header>span {
    flex-grow: 1;
    font-size: 40px;
}

.close-icon {
    position: absolute;
    right: 0;
    width: 45px;
    margin-left: auto;
}

.modal-content {
    width: 450px;
    height: 360px;
    margin-top: 20px;
}

.box-check {
    width: 100%;
    height: 360px;
    align-items: center;
}

.cur-label,
.new-pw-label,
.new-pw-check-label {
    font-size: 30px;
    font-family: "RixInooAriDuriR";
    color: rgb(230, 137, 137);
    -webkit-text-stroke: 1px black;
}

.cur-label {
    margin-top: 80px;
}

.check-input,
.new-pw-input,
.new-pw-check-input {
    width: 300px;
    height: 45px;
    font-size: 30px;
    padding-left: 15px;
    border: 2px solid black;
    border-radius: 10px;
}

.check-input {
    margin-top: 15px;
}

.btn-check {
    width: 100px;
    height: 45px;
    border-radius: 10px;
    margin-top: 20px;
    border: 2px solid #DBE7FF;
    background-color: #3B72FF;
    font-family: "RixInooAriDuriR";
    -webkit-text-stroke: 1px black;
    font-size: 20px;
    color: white;
}

.new-pw-label {
    margin-top: 50px;
}

.new-pw-input {
    margin-top: 15px;
}

.new-pw-check-label {
    margin-top: 15px;
}

.new-pw-check-input {
    margin-top: 15px;
}
</style>