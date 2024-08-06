<template>
  <div class="signup-container">
    <div class="signup-box">
      <img src="@/assets/login_elephant.svg" alt="" />
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="username">아이디 :</label>
          <input type="text" id="username" v-model="userData.id" />
          <button type="button" class="check-btn">중복확인</button>
        </div>

        <div class="form-group">
          <label for="password">비밀번호 :</label>
          <input type="password" id="password" v-model="userData.password" />
        </div>

        <div class="form-group">
          <label for="passwordConfirm">비밀번호 확인 :</label>
          <input
            type="password"
            id="passwordConfirm"
            v-model="userData.passwordConfirm"
          />
        </div>

        <div class="form-group">
          <label for="name">이름 :</label>
          <input type="text" id="name" v-model="userData.nickname" />
        </div>

        <!-- <div class="form-group">
            <label for="email">이메일 :</label>
            <input type="email" id="email" v-model="email" />
            <button type="button" class="verify-btn">인증하기</button>
          </div> -->

        <button type="submit" class="signup-btn">회원가입</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import "@/assets/css/main.css";
import { ref } from "vue";
import { register } from "@/api/user";
import { useRouter } from "vue-router";

const router = useRouter();

const userData = ref({
  id: "",
  password: "",
  passwordConfirm: "",
  nickname: "",
});

const submitForm = async () => {
  if (userData.value.password !== userData.value.passwordConfirm) {
    alert("비밀번호가 일치하지 않습니다.");
  }

  try {
    const response = await register(userData.value);
    console.log(response);
    alert("회원가입이 완료되었습니다.");
    router.push({ name: "login" });
  } catch (e) {
    alert("회원가입에 실패했습니다.");
  }
};
</script>

<style scoped>
img {
  height: 50px;
}

.signup-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #d1e7ff;
  background-image: url("path-to-your-koala-background-image.png");
  background-repeat: no-repeat;
  background-size: cover;
}

.signup-box {
  background-color: #a6c9ff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}

form {
  display: flex;
  flex-direction: column;
}

.form-group {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

label {
  flex: 1;
  font-weight: bold;
}

input {
  flex: 2;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
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
  cursor: url(@/assets/pointer.svg) 2 2, pointer;
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
  cursor: url(@/assets/pointer.svg) 2 2, pointer;
}

.signup-btn:hover {
  background-color: #8585ff;
}
</style>
