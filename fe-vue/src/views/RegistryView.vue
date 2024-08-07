<template>
  <div class="signup-container">
    <div class="signup-box">
      <form @submit.prevent="submitForm">
        <img id="elephant" src="@/assets/login_elephant.svg" alt="" />
        <div class="form-group">
          <label for="username" class="title" id="name">아이디 </label>
          <input type="text" id="username" v-model="userData.id" />
          <button type="button" class="check-btn">중복확인</button>
        </div>

        <div class="form-group">
          <label for="nickname" class="title" id="name">닉네임 </label>
          <input type="text" id="nickname" v-model="userData.nickname" />
          <button type="button" class="verify-btn">중복확인</button>
        </div>

        <div class="form-group">
          <label for="password" class="title" id="password">비밀번호 </label>
          <input type="password" id="password" v-model="userData.password" />
        </div>

        <div class="form-group">
          <label for="passwordConfirm" class="title" id="password"
            >비밀번호<br />확인
          </label>
          <input
            type="password"
            id="passwordConfirm"
            v-model="userData.passwordConfirm"
          />
        </div>

        <button type="submit" class="signup-btn">회원가입</button>
      </form>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { register } from "@/api/user";
import { useRouter } from "vue-router";
import "@/assets/css/home.css";

export default {
  setup() {
    const userData = ref({
      id: "",
      password: "",
      passwordConfirm: "",
      nickname: "",
    });

    const router = useRouter();

    const submitForm = async () => {
      if (userData.value.password !== userData.value.passwordConfirm) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
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

    return {
      userData,
      submitForm,
    };
  },
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
.verify-btn {
  margin-left: 2vh;
  padding: 1vh;
  background-color: #8585ff;
  color: white;
  border-radius: 10px;
  cursor: pointer;
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
</style>
