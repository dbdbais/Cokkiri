<script setup>
import { ref } from "vue";
import { login, getUser } from "@/api/user";
import { userStore } from "@/stores/user";
import { useRouter } from "vue-router";
import { useModal } from "@/composables/useModal";
import FindPw from "@/components/login/modal/FindPw.vue";

const router = useRouter();
const store = userStore();
const { isModalOpen, openModal, closeModal } = useModal();

const userData = ref({
  id: "",
  password: "",
});

const submitForm = async () => {
  try {
    const response = await login(userData.value);
    if (response.data === 0) {
      Swal.fire({
        icon: "error",
        title: "아이디 또는 비밀번호가 틀렸습니다.",
      });
    } else {
      getUserData();
    }
    console.log(response);
  } catch (e) {
    console.log(e);
  }
};

const getUserData = async () => {
  try {
    const response = await getUser(userData.value.id);
    store.setUser(response.data);
    console.log("사용자 계정")
    console.log(response);
    if (response.data === "") {
      Swal.fire({
        icon: "error",
        title: "아이디 또는 비밀번호가 틀렸습니다.",
      });
    } else {
      router.push({ name: "home" });
    }
  } catch (e) {
    console.log(e);
  }
};
</script>

<template>
  <div class="login-container box-row">
    <div class="login-box">
      <form class="box-col" @submit.prevent="submitForm">
        <img class="login-img" src="@/assets/login_elephant.svg" alt="" />
        <div class="input-group box-row">
          <label for="username" class="lbl-id title">이메일 </label>
          <input type="text" id="username" v-model="userData.id" />
        </div>
        <div class="input-group box-row pass-group">
          <label for="password" class="lbl-pass title">비밀번호 </label>
          <input type="password" id="password" v-model="userData.password" />
        </div>
        <RouterLink :to="{ name: 'registry' }">
          <a href="#" class="register-link">처음이신가요? 회원가입</a>
        </RouterLink>
        <span class="forgot" @click="openModal">비밀번호를 잃어버리셨나요?</span>
        <button id="submit" type="submit" class="title">로그인</button>
      </form>
      <!-- <button class="google-login">
        <img src="https://developers.google.com/identity/images/g-logo.png" alt="Google Logo" />
        <span class="title">Continue with Google</span>
      </button> -->
    </div>
  </div>
  <FindPw v-if="isModalOpen" @close="closeModal" />
</template>

<style scoped>
.login-container {
  width: 1920px;
  height: 1080px;
  justify-content: center;
  align-items: center;
  /* background-image: url("@/assets/login_bg_elephant.svg"); */
  background-color: #d1e7ff;
  /* background-repeat: space; */
}

.login-box {
  position: relative;
  width: 760px;
  height: 500px;
  padding: 50px;
  border-radius: 20px;
  /* box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); */
  text-align: center;
  border: 10px solid #0073e6;
  background-color: #a6c9ff;
}

form {
  margin-top: 50px;
  justify-content: center;
  align-items: center;
}

.login-img {
  position: absolute;
  width: 160px;
  top: -90px;
  left: 300px;
}

.input-group {
  /* display: flex; */
  /* position: relative; */
  /* align-items: center; */
  /* margin-bottom: 3vh; */
  width: 620px;
  justify-content: space-between;
  align-items: center;
  font-size: 45px;
  /* top: -10vh; */
  -webkit-text-stroke: 1px black;
}

.lbl-id {
  letter-spacing: 30px;
}

.lbl-pass {
  letter-spacing: 7px;
}

.pass-group {
  margin-top: 25px;
}

span {
  color: black;
}

label {
  font-weight: bold;
  text-align: left;
}

input {
  width: 400px;
  height: 64px;
  padding: 15px;
  border: 5px solid black;
  border-radius: 5px;
  font-size: 30px;
  border-radius: 10px;
}

button {
  width: 370px;
  height: 75px;
  margin-top: 20px;
  font-size: 45px;
  color: white;
  border: 5px solid #0073e6;
  border-radius: 20px;
  background-color: #a6a6ff;
}

#submit {
  font-weight: bold;
  -webkit-text-stroke: 1px black;
}

button:hover {
  background-color: #8585ff;
}

.register-link {
  /* font-weight: bold; */
  display: block;
  /* position: relative; */
  /* margin-top: 10px; */
  /* text-decoration: none; */
  margin-top: 35px;
  font-size: 30px;
  color: white;
}

.register-link:hover {
  color: #0073e6;
}

.forgot {
  font-family: "RixInooAriDuriR";
  margin-top: 10px;
  font-size: 25px;
  color: red;
}

.forgot:hover {
  color: black;
}

.google-login {
  color: black;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2vh;
  background-color: white;
  border: 5px solid #000;
  cursor: pointer;
  /* margin-top: 10px; */
  width: 70%;
}

.google-login img {
  width: 5vh;
  height: 5vh;
  margin-right: 10px;
}

/* #username,
#password {
  height: 6vh;
} */
</style>
