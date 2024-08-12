<script setup>
import { ref } from "vue";
import { mailCheck, passwordSend, resetPW } from "@/api/mail";

const email = ref("");
const code = ref("");
const emit = defineEmits(["close"]);

const fetchTempCode = async () => {
    try {
        const response = await passwordSend(email.value);
        console.log(response);
        if (response.data.success === 0) {
            Swal.fire({
                icon: "error",
                title: "인증번호 전송에 실패했습니다."
            });
            return;
        } else {
            Swal.fire({
                icon: "success",
                title: "인증번호가 전송되었습니다."
            });
        }
    } catch (error) {
        console.error(error);
    }
}

const checkCode = async () => {

    try {
        const resposne = await mailCheck(email.value, code.value);
        console.log(resposne);
        if (resposne.data === false) {
            Swal.fire({
                icon: "error",
                title: "인증번호가 올바르지 않습니다."
            });
            return;
        } else {
            Swal.fire({
                icon: "success",
                title: "인증번호가 확인되었습니다."
            });
            await fetchTempPw();
        }
    } catch (e) {
        console.error(e);
    }
}

const fetchTempPw = async () => {
    try {
        const response = await resetPW(email.value);
        console.log(response);
        if (response.data === 0) {
            Swal.fire({
                icon: "error",
                title: "인증번호가 올바르지 않습니다."
            });
            return;
        } else {
            Swal.fire({
                icon: "success",
                title: "비밀번호가 초기화되었습니다."
            });
            emit("close");
        }
    } catch (error) {
        console.error(error);
    }
}
</script>

<template>
    <div class="modal-overlay">
        <div class="modal-con box-main-con">
            <div class="modal-header">
                <span class="title main-title">비밀번호 찾기</span>
                <img src="@/assets/exit.svg" alt="close" class="icon-close" @click="$emit('close')" />
            </div>
            <div class="modal-content">
                <div>

                    <div class="input-con">
                        <span class="title">이메일</span><br />
                        <input type="text" v-model="email" />
                    </div>
                    <div @click="fetchTempCode">
                        <span>인증번호 요청</span>
                    </div>
                </div>
                <div>
                    <div>
                        <input type="text" v-model="code" />
                    </div>
                    <div class="box-row btn-con">
                        <div class="btn" @click="checkCode">
                            <span class="title">인증번호 확인</span>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>