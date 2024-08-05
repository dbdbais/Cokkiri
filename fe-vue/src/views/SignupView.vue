<template>
  <div class="signup-container">
    <div class="signup-box">
      <form @submit.prevent="handleSubmit">
        <img id="elephant" src="@/assets/login_elephant.svg" alt="" />
        <div class="form-group">
          <label for="username">아이디 :</label>
          <input type="text" id="username" v-model="username" />
          <button type="button" class="check-btn">중복확인</button>
        </div>

        <div class="form-group">
          <label for="password">비밀번호 :</label>
          <input type="password" id="password" v-model="password" />
        </div>

        <div class="form-group">
          <label for="passwordConfirm">비밀번호<br />확인 :</label>
          <input
            type="password"
            id="passwordConfirm"
            v-model="passwordConfirm"
          />
        </div>

        <div class="form-group">
          <label for="email">이메일 :</label>
          <input type="email" id="email" v-model="email" />
          <button type="button" class="verify-btn">인증하기</button>
        </div>

        <button type="submit" class="signup-btn">회원가입</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: "",
      password: "",
      passwordConfirm: "",
      email: "",
    };
  },
  methods: {
    handleSubmit() {
      // Handle signup logic here
      console.log("Username:", this.username);
      console.log("Password:", this.password);
      console.log("Password Confirm:", this.passwordConfirm);
      console.log("Email:", this.email);
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

.signup-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #d1e7ff;
  background-repeat: no-repeat;
  background-size: cover;
}

.signup-box {
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
  margin-bottom: 20px;
  font-size: 50px;
  top: -100px;
  -webkit-text-stroke: 1px black;
}

label {
  flex: 1;
  font-weight: bold;
  text-align: left;
  margin-right: 10px;
}

input {
  flex: 2;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  height: 50px;
}

.check-btn,
.verify-btn {
  margin-left: 10px;
  padding: 8px;
  background-color: #8585ff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  height: 50px;
  font-size: 35px;
}

.check-btn:hover,
.verify-btn:hover {
  background-color: #6a6aff;
}

.signup-btn {
  padding: 10px;
  background-color: #a6a6ff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  height: 50px;
  font-size: 35px;
  position: relative;
  top: -100px;
}

.signup-btn:hover {
  background-color: #8585ff;
}
</style>
