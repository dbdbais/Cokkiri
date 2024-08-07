<script setup>
import { onMounted, ref, watch } from "vue";
import { register, getAllUser } from "@/api/user";
import { useRouter } from "vue-router";
import "@/assets/css/home.css";
// import { re } from "@/api/openvidu-browser-2.30.0";

const router = useRouter();
const userData = ref({});
const allUser = ref([]);
const userId = ref("");
const password = ref("");
const passwordConfirm = ref("");
const nickname = ref("");

const userIdCheck = ref(true);
const nicknameCheck = ref(true);

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
  if (nicknameCheck.value || userIdCheck.value) {
    Swal.fire({
      icon: "warning",
      title: "중복확인을 진행해주세요!",
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
      id: userId.value,
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
</script>

<template>
  <div class="signup-container box-row">
    <div class="signup-box">
      <div class="form box-col">
        <img class="signup-img" src="@/assets/login_elephant.svg" alt="" />
        <div class="input-group box-col id-group">
          <label for="username" class="title lbl-id">아이디 </label>
          <div>
            <input type="text" id="username" v-model="userid" />
            <button v-if="useridcheck" type="button" class="check-btn" @click="useridcheckfun">
              중복확인
            </button>
            <span v-else class="check">중복확인</span>
          </div>
        </div>

        <div class="input-group box-col name-group">
          <label for="nickname" class="title lbl-name" id="name">닉네임 </label>
          <div>
            <input type="text" id="nickname" v-model="nickname" />
            <button v-if="nicknamecheck" type="button" class="verify-btn" @click="nicknamecheckfun">
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
          <label for="passwordconfirm" class="title lbl-pass-chk">비밀번호확인
          </label>
          <input type="password" id="passwordconfirm" v-model="passwordconfirm" />
        </div>

      </div>
      <button type="submit" class="signup-btn" @click="submitform">
        회원가입
      </button>
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
  height: 830px;
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
  -webkit-text-stroke: 1px black;
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
  height: 64px;
  padding: 15px;
  border: 5px solid black;
  border-radius: 5px;
  font-size: 30px;
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

.check-btn:hover,
.verify-btn:hover {
  background-color: #6a6aff;
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
</style>
