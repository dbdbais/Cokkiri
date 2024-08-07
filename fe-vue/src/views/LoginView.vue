<script setup>
import "@/assets/css/main.css";
import { ref } from "vue";
import { login, getUser } from "@/api/user";
import { userStore } from "@/stores/user";
import { useRouter } from "vue-router";

const router = useRouter();
const store = userStore();

const userData = ref({
  id: "",
  password: "",
});

const submitForm = async () => {
  try {
    const response = await login(userData.value);
    getUserData();
    console.log(response);
  } catch (e) {
    console.log(e);
  }
};

const getUserData = async () => {
  try {
    const response = await getUser(userData.value.id);
    store.setUser(response.data);
    console.log(response);
    if (response.data === 1) {
      router.push({ name: "home" });
    } else {
      Swal.fire({
        icon: "error",
        title: "아이디 또는 비밀번호가 틀렸습니다.",
      });
    }
  } catch (e) {
    console.log(e);
  }
};
</script>

<template>
  <div class="login-container">
    <div class="login-box">
      <form @submit.prevent="submitForm">
        <img id="elephant" src="@/assets/login_elephant.svg" alt="" />
        <div class="form-group">
          <label for="username" class="title" id="id">아이디 </label>
          <input type="text" id="username" v-model="userData.id" />
        </div>

        <div class="form-group">
          <label for="password" class="title">비밀번호 </label>
          <input type="password" id="password" v-model="userData.password" />
        </div>

        <button id="submit" type="submit" class="title">로그인</button>
      </form>
      <RouterLink :to="{ name: 'registry' }">
        <a href="#" class="register-link">처음이신가요? 회원가입</a>
      </RouterLink>

      <button class="google-login">
        <img src="https://developers.google.com/identity/images/g-logo.png" alt="Google Logo" />
        <span class="title">Continue with Google</span>
      </button>
    </div>
  </div>
</template>

<style scoped>
#id {
  letter-spacing: 2.7vh;
}

span {
  color: black;
}

#elephant {
  position: relative;
  top: -12vh;
  height: 20vh;
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  /* background-image: url("@/assets/login_bg_elephant.svg"); */
  background-color: #d1e7ff;
  background-repeat: space;
}

.login-box {
  background-color: #a6c9ff;
  padding: 3vh;
  border-radius: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
  height: 60vh;
  width: 90vh;
  border: 10px solid #0073e6;
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
  font-size: 6vh;
  top: -10vh;
  -webkit-text-stroke: 1px black;
}

label {
  flex: 1;
  font-weight: bold;
  text-align: left;
  margin-right: 10px;
  flex-direction: row;
}

input {
  flex: 2;
  padding: 8px;
  border: 5px solid black;
  border-radius: 5px;
  font-size: 30px;
  border-radius: 10px;
}

button {
  background-color: #a6a6ff;
  color: white;
  border: none;
  border-radius: 20px;
  font-size: 4vh;
  width: 50%;
  height: 7vh;
  position: relative;
  top: -10vh;
  margin: 0 auto;
  margin-bottom: 4vh;
  border: 5px solid #0073e6;
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
  position: relative;
  /* margin-top: 10px; */
  text-decoration: none;
  font-size: 30px;
  top: -120px;
  color: white;
}

.register-link:hover {
  color: #0073e6;
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

#username,
#password {
  height: 6vh;
}
</style>
