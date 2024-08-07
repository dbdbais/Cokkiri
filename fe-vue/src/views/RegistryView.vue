<template>
  <div class="signup-container">
    <div class="signup-box">
      <div>
        <img id="elephant" src="@/assets/login_elephant.svg" alt="" />
        <div class="form-group">
          <label for="username" class="title" id="name">아이디 </label>
          <input type="text" id="username" v-model="userId" />
          <button
            v-if="userIdCheck"
            type="button"
            class="check-btn"
            @click="userIdCheckFun"
          >
            중복확인
          </button>
          <span v-else class="check">중복확인</span>
        </div>

        <div class="form-group">
          <label for="nickname" class="title" id="name">닉네임 </label>
          <input type="text" id="nickname" v-model="nickname" />
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

        <div class="form-group">
          <label for="password" class="title" id="password">비밀번호 </label>
          <input type="password" id="password" v-model="password" />
        </div>

        <div class="form-group">
          <label for="passwordConfirm" class="title" id="password"
            >비밀번호<br />확인
          </label>
          <input
            type="password"
            id="passwordConfirm"
            v-model="passwordConfirm"
          />
        </div>

        <button type="submit" class="signup-btn" @click="submitForm">
          회원가입
        </button>
      </div>
    </div>
  </div>
</template>

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

<style scoped>
#name {
  letter-spacing: 1.7vh;
}

#elephant {
  position: relative;
  top: -12vh;
  height: 20vh;
}

.signup-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  /* background-image: url("@/assets/login_bg_elephant.svg"); */
  background-color: #d1e7ff;
  background-repeat: space;
}

.signup-box {
  background-color: #a6c9ff;
  padding: 3vh;
  border-radius: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
  height: 70vh;
  width: 90vh;
  border: 10px solid #0073e6;
  /* justify-content: space-around; */
}

form {
  display: flex;
  flex-direction: column;
}

.form-group {
  display: flex;
  position: relative;
  align-items: center;
  margin-bottom: 3vh;
  font-size: 5.5vh;
  top: -10vh;
  -webkit-text-stroke: 1px black;
}

label {
  /* flex: 1; */
  font-weight: bold;
  text-align: center;
  margin-right: 10px;
}

input {
  flex: 1;
  padding: 1vh;
  border: 5px solid black;
  border-radius: 5px;
  font-size: 3vh;
  border-radius: 10px;
}

.check-btn,
.check,
.verify-btn {
  margin-left: 2vh;
  padding: 1vh;
  background-color: #8585ff;
  color: white;
  border-radius: 10px;
  height: 7vh;
  font-size: 4vh;
  font-family: "RixInooAriDuriR";
  -webkit-text-stroke: 1px black;
  border: 5px solid #0073e6;
}

.check-btn:hover,
.verify-btn:hover {
  background-color: #6a6aff;
}

.signup-btn {
  background-color: #a6a6ff;
  color: white;
  border: none;
  border-radius: 20px;
  font-size: 4vh;
  width: 50%;
  height: 8vh;
  position: relative;
  top: -11vh;
  margin: 0 auto;
  margin-bottom: 4vh;
  border: 5px solid #0073e6;
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
