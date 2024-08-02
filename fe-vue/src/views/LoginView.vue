<script setup>
import { ref } from 'vue'
import { login } from '@/api/user'

const userData = ref({
    id: '',
    password: ''
})

const submitForm = async () => {
    try {
        const response = await login(userData.value)
        console.log(response)
    } catch (e) {
        console.log(e)
    }
}
</script>

<template>
    <div>
        <form @submit.prevent="submitForm">
            <input type="text" placeholder="아이디" v-model="userData.id" />
            <input type="password" placeholder="비밀번호" v-model="userData.password" />
            <button type="submit">로그인</button>
        </form>
        <RouterLink :to="{ name: 'registry' }">
            <div class="registry">
                <span>처음이신가요? 회원가입</span>
            </div>
        </RouterLink>
    </div>
</template>

<style scoped></style>