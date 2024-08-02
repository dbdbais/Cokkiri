<script setup>
import { ref } from 'vue'
import { register } from '@/api/user'
import { useRouter } from 'vue-router'

const router = useRouter()

const userData = ref({
    id: '',
    password: '',
    passwordConfirm: '',
    nickname: ''
})

const submitForm = async () => {
    if (userData.value.password !== userData.value.passwordConfirm) {
        alert('비밀번호가 일치하지 않습니다.')
    }

    try {
        const response = await register(userData.value)
        console.log(response)
        alert('회원가입이 완료되었습니다.')
        router.push({ name: 'login' })
    } catch (e) {
        alert('회원가입에 실패했습니다.')
    }
}


</script>

<template>
    <div>
        <form @submit.prevent="submitForm">
            <input type="text" placeholder="아이디" v-model="userData.id" />
            <input type="password" placeholder="비밀번호" v-model="userData.password" />
            <input type="password" placeholder="비밀번호 확인" v-model="userData.passwordConfirm" />
            <input type="text" placeholder="이름" v-model="userData.nickname" />
            <button type="submit">회원가입</button>
        </form>
    </div>
</template>

<style scoped></style>