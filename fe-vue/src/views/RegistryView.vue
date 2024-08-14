<script setup>
import { onMounted, ref, watch } from "vue";
import { register, getAllUser } from "@/api/user";
import { useRouter } from "vue-router";
import { registerSend, mailCheck } from "@/api/mail";
import "@/assets/css/home.css";
// import { re } from "@/api/openvidu-browser-2.30.0";

const router = useRouter();
const userData = ref({});
const allUser = ref([]);
const userId = ref("");
const email = ref("");
const emailConfirm = ref("");
const password = ref("");
const passwordConfirm = ref("");
const nickname = ref("");

const userIdCheck = ref(true);
const emailCheck = ref(true);
const emailConfirmCheck = ref(true);
const nicknameCheck = ref(true);
const loding = ref(false);

onMounted(() => {
  getAllUser()
    .then((res) => {
      console.log(res.data);
      allUser.value = res.data;
    })
    .catch((err) => {
      console.log(err);
    });
});

const userIdCheckFun = function () {
  if (userId.value === "") {
    Swal.fire({
      icon: "error",
      title: "아이디를 입력해주세요!",
    });
  } else if (
    allUser.value.some((user) => {
      if (user.id === userId.value) {
        return true;
      }
    })
  ) {
    console.log("중복 아이디 있음!");
    Swal.fire({
      icon: "error",
      title: "이미 아이디가 존재합니다!",
      text: "다른 아이디를 입력해주세요!",
    });
  } else {
    console.log("중복 아이디 없음");
    userIdCheck.value = false;
  }
};

const nicknameCheckFun = function () {
  if (nickname.value === "") {
    Swal.fire({
      icon: "error",
      title: "닉네임을 입력해주세요!",
    });
  } else if (
    allUser.value.some((user) => {
      if (user.nickname === nickname.value) {
        return true;
      }
    })
  ) {
    console.log("중복 닉네임 있음!");
    Swal.fire({
      icon: "error",
      title: "이미  존재하는 닉네임입니다!",
      text: "다른 닉네임을 입력해주세요!",
    });
  } else {
    console.log("중복 닉네임 없음");
    nicknameCheck.value = false;
  }
};

const submitForm = async () => {
  if (nicknameCheck.value) {
    Swal.fire({
      icon: "warning",
      title: "중복확인을 진행해주세요!",
    });
    return;
  }
  if (emailConfirmCheck.value) {
    Swal.fire({
      icon: "warning",
      title: "이메일 인증을 진행해주세요!",
    });
    return;
  }
  if (password.value !== passwordConfirm.value) {
    Swal.fire({
      icon: "warning",
      title: "비밀번호가 일치하지 않습니다!",
    });
    return;
  }

  try {
    userData.value = {
      id: email.value,
      password: password.value,
      passwordConfirm: passwordConfirm.value,
      nickname: nickname.value,
    };

    const response = await register(userData.value);
    console.log(response);
    Swal.fire({
      icon: "success",
      title: "성공적으로 가입되었습니다!",
    });
    router.push({ name: "login" });
  } catch (e) {
    Swal.fire({
      icon: "error",
      title: "회원가입에 실패했습니다.",
    });
  }
};

const emailCheckFun = async () => {
  loding.value = true;
  try {
    const response = await registerSend(email.value);
    console.log(response);
    if (response.data.success === true) {
      // emailCheck.value = false;
      loding.value = false;
      Swal.fire({
        icon: "success",
        title: "인증번호가 전송되었습니다.",
      });
    } else {
      loding.value = false;
      Swal.fire({
        icon: "error",
        title: "인증번호 전송에 실패했습니다.",
      });
    }
  } catch (e) {
    console.error(e);
    Swal.fire({
      icon: "error",
      title: "인증번호 전송에 실패했습니다.",
    });
  }
};

const emailConfirmCheckFun = async () => {
  try {
    const response = await mailCheck(email.value, emailConfirm.value);
    console.log(response);
    if (response.data === true) {
      emailConfirmCheck.value = false;
      Swal.fire({
        icon: "success",
        title: "이메일이 확인되었습니다.",
      });
    } else {
      Swal.fire({
        icon: "error",
        title: "인증번호가 올바르지 않습니다.",
      });
    }
  } catch (e) {
    console.error(e);
    Swal.fire({
      icon: "error",
      title: "이메일 확인에 실패했습니다.",
    });
  }
};
</script>

<template>
  <div class="signup-container box-row">
    <div class="signup-box">
      <div class="form box-col">
        <img class="signup-img" src="@/assets/login_elephant.svg" alt="" />
        <!-- <div class="input-group box-col id-group">
          <label for="username" class="title lbl-id">아이디 </label>
          <div>
            <input type="text" id="username" v-model="userId" />
            <button v-if="userIdCheck" type="button" class="check-btn" @click="userIdCheckFun">
              중복확인
            </button>
            <span v-else class="check">중복확인</span>
          </div>
        </div> -->

        <div class="input-group box-col id-group">
          <label for="username" class="title lbl-id">이메일 </label>
          <div class="flex-align" style="position: relative">
            <input
              type="text"
              id="email"
              class="nomal-text"
              v-model="email"
              placeholder="example@ssafy.com"
            />
            <button
              v-if="emailCheck"
              type="button"
              class="check-btn email-check"
              :class="{ loding: loding }"
              @click="emailCheckFun"
            >
              {{ loding ? "  " : "요청" }}
            </button>
            <span v-else class="check">요청</span>
            <div class="dots-flow" v-if="loding"></div>
          </div>
        </div>
        <div class="input-group box-col id-group" style="margin-top: 10px">
          <div>
            <input
              type="text"
              id="email-verify"
              class="nomal-text"
              v-model="emailConfirm"
              placeholder="인증번호"
            />
            <button
              v-if="emailConfirmCheck"
              type="button"
              class="check-btn email-check"
              @click="emailConfirmCheckFun"
            >
              확인
            </button>
            <span v-else class="check">확인</span>
          </div>
        </div>
        <div class="input-group box-col name-group">
          <label for="nickname" class="title lbl-name" id="name">닉네임 </label>
          <div>
            <input
              type="text"
              id="nickname"
              class="nomal-text"
              v-model="nickname"
              placeholder="닉네임"
            />
            <button
              v-if="nicknameCheck"
              type="button"
              class="verify-btn"
              @click="nicknameCheckFun"
            >
              중복확인
            </button>
            <span v-else class="check">중복확인</span>
          </div>
        </div>

        <div class="input-group box-col pass-group">
          <label for="password" class="title lbl-pass">비밀번호 </label>
          <input type="password" id="password" v-model="password" />
        </div>

        <div class="input-group box-col pass-group">
          <label for="passwordconfirm" class="title lbl-pass-chk"
            >비밀번호확인
          </label>
          <input
            type="password"
            id="passwordconfirm"
            v-model="passwordConfirm"
          />
        </div>
      </div>
      <button class="signup-btn" @click="submitForm">회원가입</button>
    </div>
  </div>
</template>

<style scoped>
.signup-container {
  width: 1920px;
  height: 1080px;
  justify-content: center;
  align-items: center;
  /* background-image: url("@/assets/login_bg_elephant.svg"); */
  background-color: #d1e7ff;
  /* background-repeat: space; */
}

.signup-box {
  position: relative;
  width: 680px;
  height: 890px;
  padding: 50px;
  border-radius: 20px;
  /* box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); */
  text-align: center;
  border: 10px solid #0073e6;
  background-color: #a6c9ff;
  /* justify-content: space-around; */
}

.form {
  margin-top: 40px;
  margin-left: 80px;
  justify-content: center;
  align-items: start;
}

.signup-img {
  position: absolute;
  width: 160px;
  top: -90px;
  left: 260px;
}

.input-group {
  align-items: start;
  font-size: 38px;
  /* -webkit-text-stroke: 1px black; */
}

.lbl-id,
.lbl-name {
  letter-spacing: 18px;
  margin-left: 110px;
  margin-bottom: 10px;
}

.lbl-pass {
  letter-spacing: 30px;
  margin-left: 70px;
  margin-bottom: 10px;
}

.lbl-pass-chk {
  letter-spacing: 10px;
  margin-left: 50px;
  margin-bottom: 10px;
}

label {
  display: block;
  font-weight: bold;
  text-align: center;
}

input {
  width: 360px;
  text-align: center;
  height: 64px;
  padding: 15px;
  border: 5px solid black;
  border-radius: 5px;
  font-size: 25px;
  border-radius: 10px;
}

.name-group,
.pass-group {
  margin-top: 25px;
}

.check-btn,
.check,
.verify-btn {
  margin-left: 10px;
  padding: 5px;
  background-color: #8585ff;
  color: white;
  border-radius: 10px;
  font-size: 25px;
  font-family: "RixInooAriDuriR";
  -webkit-text-stroke: 1px black;
  border: 5px solid #0073e6;
}
.check-btn {
  margin-top: 5px;
}
.check-btn:hover,
.verify-btn:hover {
  background-color: #6a6aff;
}

.email-check {
  width: 80px;
  height: 50px;
  background-color: blue;
}

.signup-btn {
  width: 370px;
  height: 85px;
  margin-top: 45px;
  color: white;
  font-size: 45px;
  border: none;
  border-radius: 20px;
  border: 5px solid #0073e6;
  background-color: #a6a6ff;
  font-family: "RixInooAriDuriR";
  -webkit-text-stroke: 1px black;
}

.signup-btn:hover {
  background-color: #8585ff;
}

.check {
  background-color: gray;
  color: gainsboro;
  border-color: gainsboro;
}
/**
  * Dots flow
  *
  * @author jh3y - jheytompkins.com
*/
.dots-flow {
  scale: 0.8;
}
.dots-flow:before {
  -webkit-animation: dots-flow 0.85s infinite ease;
  animation: dots-flow 0.85s infinite ease;
  border-radius: 100%;
  content: "";
  height: 16px;
  position: absolute;
  top: 2px;
  right: 35px;
  -webkit-transform: translate(-50%, -40px);
  transform: translate(-50%, -40px);
  width: 16px;
}
.loding {
  background-color: #a6a6ff;
}
@-webkit-keyframes dots-flow {
  0%,
  100% {
    -webkit-box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white,
      26px 32px 0 0 white;
    box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white, 26px 32px 0 0 white;
  }
  35% {
    -webkit-box-shadow: -26px 32px 0 4px purple, 0px 32px 0 0 white,
      26px 32px 0 0 white;
    box-shadow: -26px 32px 0 4px purple, 0px 32px 0 0 white, 26px 32px 0 0 white;
  }
  50% {
    -webkit-box-shadow: -26px 32px 0 0 white, 0px 32px 0 4px purple,
      26px 32px 0 0 white;
    box-shadow: -26px 32px 0 0 white, 0px 32px 0 4px purple, 26px 32px 0 0 white;
  }
  65% {
    -webkit-box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white,
      26px 32px 0 4px purple;
    box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white,
      26px 32px 0 4px purple;
  }
}

@keyframes dots-flow {
  0%,
  100% {
    -webkit-box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white,
      26px 32px 0 0 white;
    box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white, 26px 32px 0 0 white;
  }
  35% {
    -webkit-box-shadow: -26px 32px 0 4px purple, 0px 32px 0 0 white,
      26px 32px 0 0 white;
    box-shadow: -26px 32px 0 4px purple, 0px 32px 0 0 white, 26px 32px 0 0 white;
  }
  50% {
    -webkit-box-shadow: -26px 32px 0 0 white, 0px 32px 0 4px purple,
      26px 32px 0 0 white;
    box-shadow: -26px 32px 0 0 white, 0px 32px 0 4px purple, 26px 32px 0 0 white;
  }
  65% {
    -webkit-box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white,
      26px 32px 0 4px purple;
    box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white,
      26px 32px 0 4px purple;
  }
}
</style>
