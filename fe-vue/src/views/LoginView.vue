<template>
  <div class="login-container">
    <div class="background" :style="{ left: position + 'px' }"></div>
    <div class="login-box">
      <img id="elephant" src="@/assets/login_elephant.svg" alt="" />
      <form @submit.prevent="submitForm">
        <div class="form-group1">
          <label for="username">아이디 :</label>
          <input type="text" id="username" v-model="userData.id" />
        </div>

        <div class="form-group2">
          <label for="password">비밀번호 :</label>
          <input type="password" id="password" v-model="userData.password" />
        </div>

        <button type="submit" class="login-button">로그인</button>
      </form>
      <RouterLink :to="{ name: 'registry' }">
        <div class="registry">
          <span>처음이신가요? 회원가입</span>
        </div>
      </RouterLink>
      <button class="google-login">
        <img
          src="https://developers.google.com/identity/images/g-logo.png"
          alt="Google Logo"
        />
        Continue with Google
      </button>
    </div>
  </div>
</template>

<script setup>
import "@/assets/css/main.css";
import { ref } from "vue";
import { login } from "@/api/user";

const userData = ref({
  id: "",
  password: "",
});

const submitForm = async () => {
  try {
    const response = await login(userData.value);
    console.log(response);
  } catch (e) {
    console.log(e);
  }
};
// export default {
//   data() {
//     return {
//       username: "",
//       password: "",
//     };
//   },
//   mounted() {
//     this.startAnimation();
//   },
//   methods: {
//     handleSubmit() {
//       // Handle login logic here
//       console.log("Username:", this.username);
//       console.log("Password:", this.password);
//     },
//     startAnimation() {
//       const containerWidth = 1000;
//       const backgroundWidth = 1000; // 배경 이미지의 너비
//       const resetPosition = -backgroundWidth;
//       const startPosition = containerWidth;

//       this.position = startPosition;

//       setInterval(() => {
//         this.position -= 1;

//         if (this.position <= resetPosition) {
//           this.position = startPosition;
//         }
//       }, 16); // 60fps (1000ms/60 ≈ 16ms)
//     },
//   },
// };
</script>

<style scoped>
.background {
  position: absolute;
  top: 0;
  width: 1000px; /* 배경 이미지의 너비와 동일하게 설정 */
  height: 100vh;
  background-image: url("@/assets/login_bg_elephant.svg"); /* 배경 이미지 경로 */
  background-size: cover;
}

.login-box {
  position: relative;
}

#elephant {
  position: absolute;
  height: 100px;
  top: -90px;
  left: 180px;
  z-index: 1;
}

.login-container {
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #d1e7ff;
  background-repeat: no-repeat;
  background-size: cover;
}

.login-box {
  border: 5px solid #3b72ff;
  background-color: #a6c9ff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
  height: 50vh;
  width: 50vh;
}

form {
  display: flex;
  flex-direction: column;
}

.form-group1 {
  font-size: 40px;
  display: flex;
  align-items: center;
  margin-top: 40px;
  margin-bottom: 20px;
}

.form-group2 {
  font-size: 31px;
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

label {
  flex: 1;
  font-weight: normal;
  text-align: right;
  margin-right: 10px;
  -webkit-text-stroke: 1px black;
}

input {
  flex: 2;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  color: black;
}

button {
  padding: 5px;
  background-color: #a6a6ff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 100%;
  margin-top: 10px;
  font-weight: normal;
  -webkit-text-stroke: 0.5px black;
  margin-bottom: 10px;
  border: 3px solid #3b72ff;
}

button:hover {
  background-color: #8585ff;
  cursor: url(@/assets/pointer.svg) 2 2, pointer;
}

.registry {
  display: block;
  margin-top: 10px;
  color: #ffffff;
  text-decoration: none;
  font-size: 1em;
}

.registry:hover {
  color: #8585ff;
}

.google-login {
  margin-top: 20px;
  margin-bottom: 10px;
  color: black;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 5px;
  cursor: pointer;
  width: 100%;
  border: 3px solid black;
}

.google-login img {
  width: 20px;
  height: 20px;
  margin-right: 10px;
}

.login-button {
  font-size: 25px;
}
</style>
