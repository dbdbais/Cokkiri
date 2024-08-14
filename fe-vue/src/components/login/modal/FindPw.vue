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
        title: "인증번호 전송에 실패했습니다.",
      });
      return;
    } else {
      Swal.fire({
        icon: "success",
        title: "인증번호가 전송되었습니다.",
      });
    }
  } catch (error) {
    console.error(error);
  }
};

const checkCode = async () => {
  try {
    const resposne = await mailCheck(email.value, code.value);
    console.log(resposne);
    if (resposne.data === false) {
      Swal.fire({
        icon: "error",
        title: "인증번호가 올바르지 않습니다.",
      });
      return;
    } else {
      Swal.fire({
        icon: "success",
        title: "인증번호가 확인되었습니다.",
      });
      await fetchTempPw();
    }
  } catch (e) {
    console.error(e);
  }
};

const fetchTempPw = async () => {
  try {
    const response = await resetPW(email.value);
    console.log(response);
    if (response.data === 0) {
      Swal.fire({
        icon: "error",
        title: "인증번호가 올바르지 않습니다.",
      });
      return;
    } else {
      Swal.fire({
        icon: "success",
        title: "비밀번호가 초기화되었습니다.",
      });
      emit("close");
    }
  } catch (error) {
    console.error(error);
  }
};
</script>

<template>
  <div class="modal-overlay">
    <div class="modal-con box-main-con slideDown">
      <img
        src="@/assets/exit.svg"
        alt="close"
        class="icon-close exit"
        @click="$emit('close')"
      />
      <div class="modal-header title main-title">비밀번호 찾기</div>
      <div class="modal-content">
        <div class="input-con">
          <div class="email bold-text">이메일</div>
          <input
            class="email-input nomal-text"
            type="text"
            placeholder="이메일을 입력해주세요."
            v-model="email"
          />
          <div class="bold-text auth-num">인증번호</div>
          <input
            class="check-input nomal-text"
            type="text"
            placeholder="인증번호"
            maxlength="6"
            v-model="code"
          />
        </div>

        <div class="box-row btn-con">
          <button class="bold-text btn request md" @click="fetchTempCode">
            인증번호 요청
          </button>
          <button class="btn check-btn md" @click="checkCode">
            <span class="title">인증번호 확인</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-con {
  width: 400px;
  height: 330px;
  position: relative;
  background-color: #d9bbff;
}
.modal-header {
  margin: 20px 0px 0px 10px;
}
.email,
.auth-num {
  font-size: 23px;
  margin: 10px;
}
input {
  border: 2px solid black;
  border-radius: 5px;
  font-size: 20px;
}
.email-input,
.check-input {
  width: 370px;
  height: 30px;
  padding: 10px;
  margin-left: 10px;
}

.check-input {
  text-align: center;
}
.btn {
  width: 150px;
  height: 70px;
  padding: 10px 20px;
  border-radius: 10px;
  border-width: 5px;
  border-color: #3b72ff;
  background-color: #c191ff;
}
.btn-con {
  margin-top: 15px;
  align-items: center;
  justify-content: space-around;
}
.check-btn {
  background-color: #8220ff;
}
</style>
