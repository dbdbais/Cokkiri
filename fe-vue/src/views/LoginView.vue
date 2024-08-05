<template>
  <div class="login-container">
    <div class="login-box">
      <form @submit.prevent="handleSubmit">
        <img id="elephant" src="@/assets/login_elephant.svg" alt="" />
        <div class="form-group">
          <label for="username">아이디 :</label>
          <input type="text" id="username" v-model="username" />
        </div>

        <div class="form-group">
          <label for="password">비밀번호 :</label>
          <input type="password" id="password" v-model="password" />
        </div>

        <button id="submit" type="submit">로그인</button>
      </form>
      <a href="#" class="register-link">처음이신가요? 회원가입</a>
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

<script>
import "@/assets/css/home.css";
export default {
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    handleSubmit() {
      // Handle login logic here
      console.log("Username:", this.username);
      console.log("Password:", this.password);
    },
  },
};
</script>

<style scoped>
#elephant {
  position: relative;
  top: -130px;
  height: 200px;
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #d1e7ff;
  background-repeat: no-repeat;
  background-size: cover;
}

.login-box {
  background-color: #a6c9ff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
  height: 60vh;
  width: 90vh;
}

form {
  display: flex;
  flex-direction: column;
}

.form-group {
  display: flex;
  position: relative;
  align-items: center;
  margin-bottom: 30px;
  font-size: 60px;
  top: -100px;
  -webkit-text-stroke: 1px black;
}

label {
  flex: 1;
  font-weight: bold;
  text-align: center;
  margin-right: 10px;
}

input {
  flex: 2;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

button {
  padding: 10px;
  background-color: #a6a6ff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 40px;
  width: 50%;
  height: 60px;
  position: relative;
  top: -100px;
  margin: 0 auto;
  margin-bottom: 50px;
}

#submit {
  -webkit-text-stroke: 1px black;
}

button:hover {
  background-color: #8585ff;
}

.register-link {
  display: block;
  position: relative;
  /* margin-top: 10px; */
  color: #0073e6;
  text-decoration: none;
  font-size: 30px;
  top: -120px;
}

.google-login {
  color: black;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px;
  width: 70%;
}

.google-login img {
  width: 40px;
  height: 40px;
  margin-right: 10px;
}

#username,
#password {
  height: 60px;
}
</style>
