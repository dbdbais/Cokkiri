<script setup>
import { ref, computed, onBeforeMount } from "vue"
import { userStore } from "@/stores/user"
import { useModal } from "@/composables/useModal"
import { getSolved } from "@/api/submit"
import { problemStore } from "@/stores/problem"
import Exp from "@/components/home/Exp.vue"
import SubmitList from "@/components/home/modal/SubmitList.vue"
import PasswordReset from "@/components/home/modal/PasswordReset.vue"

const uStore = userStore()
const pStore = problemStore()
const solvedProblems = ref([])
const selectedNo = ref(0)
const { isModalOpen: isSubmitModalOpen, openModal: openSubmitModal, closeModal: closeSubmitModal } = useModal()
const { isModalOpen: isResetModalOpen, openModal: openResetModal, closeModal: closeResetModal } = useModal()

const imageSrc = computed(() => {
    switch (uStore.user.tier) {
        case "SEED":
            return new URL("@/assets/rank/seed.svg", import.meta.url).href;
        case "KIWI":
            return new URL("@/assets/rank/kiwi.svg", import.meta.url).href;
        case "APPLE":
            return new URL("@/assets/rank/apple.svg", import.meta.url).href;
        case "ORANGE":
            return new URL("@/assets/rank/orange.svg", import.meta.url).href;
        case "KOREAMELON":
            return new URL("@/assets/rank/koreamelon.svg", import.meta.url).href;
        case "DURIAN":
            return new URL("@/assets/rank/durian.svg", import.meta.url).href;
        default:
            return new URL("@/assets/rank/seed.svg", import.meta.url).href;
    }
});

const solvedCnt = computed(() => solvedProblems.value.length)

onBeforeMount(async () => {
    await fetchSolvedList()
    solvedProblems.value = processData()
})

const fetchSolvedList = async () => {
    try {
        const response = await getSolved(uStore.user.id)
        console.log(response)
        solvedProblems.value = response.data
    } catch (error) {
        console.error(error)
    }
}

const processData = () => {
    var solvedList = []
    for (let solved of solvedProblems.value) {
        solvedList.push({
            no: solved,
            level: pStore.problems.find(item => item.no === solved).level
        })
    }
    console.log(solvedList)
    return solvedList
}

const openSubmitListModal = (problemId) => {
    selectedNo.value = problemId
    openSubmitModal()
}

const tier = (level) => {
    var tierNum = Math.floor((level - 1) / 5)
    switch (tierNum) {
        case 0:
            return "bronze"
        case 1:
            return "silver"
        case 2:
            return "gold"
        case 3:
            return "platinum"
        case 4:
            return "diamond"
        default:
            return "bronze"
    }
};


</script>

<template>
    <div class="modal-overlay">
        <div class="modal-con" :class="{ shifed: isResetModalOpen || isSubmitModalOpen }">
            <div class="modal-header box-row">
                <span class="title main-title header-text">마이프로필</span>
                <div class="modal-header-right box-row">
                    <span class="reset-pw" @click="openResetModal">비밀번호 변경</span>
                    <img class="close-icon" src="@/assets/exit.svg" @click="$emit('close')">
                </div>
            </div>
            <div class="box-row modal-content">
                <div class="box-col modal-content-left">
                    <div class="box-row left-name-box">
                        <span class="title main-title">{{ uStore.user.nickname }}</span>
                    </div>
                    <div class="box-col left-tier-box">
                        <img id="rank-img" :src="imageSrc" alt="rank" class="rank-img" />
                        <Exp :tier="uStore.user.tier" :percentage="uStore.user.percent" class="rank-exp" />
                    </div>
                    <div class="box-row left-count-box">
                        <span class="title main-title">푼 문제 수 : {{ solvedCnt }}</span>
                    </div>
                </div>
                <div class="modal-content-right">
                    <div class="box-row right-header-box">
                        <span class="title main-title">푼 문제</span>
                        <span class="write-review" @click="openSubmitModal">리뷰 작성하기</span>
                    </div>
                    <div class="right-solved-box">
                        <div v-for="problem in solvedProblems" :key="problem.no" class="solved-box">
                            <span :class="tier(problem.level)" @click="openSubmitListModal(problem.no)">{{ problem.no
                                }}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="box-modal-bind">
        <PasswordReset v-if="isResetModalOpen" @close="closeResetModal" />
        <SubmitList v-if="isSubmitModalOpen" @close="closeSubmitModal" :problem-id="selectedNo" />
    </div>
</template>

<style scoped>
.modal-overlay {
    background-color: rgba(0, 0, 0, 0.6);
}

.modal-con {
    position: absolute;
    width: 1000px;
    height: 500px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: #DBE7FF;
    padding: 20px 30px;
}

.shifed {
    transition: transform 0.4s ease-in-out;
    transform: translate(-35%, 0);
}

.modal-header {
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}

.modal-header-right {
    justify-content: space-between;
    align-items: center;
}

.reset-pw {
    margin-right: 20px;
    font-size: 20px;
    font-weight: 800;
    border-radius: 5px;
    background-color: #3B72FF;
    color: white;
    padding: 10px;
}

.reset-pw:hover {
    background-color: #81C3FF;
}

.close-icon {
    width: 45px;
    height: 45px;
}

.header-text {
    font-size: 50px;
}

.modal-content {
    width: 930px;
    height: 350px;
    gap: 20px;
}

.modal-content-left {
    width: 350px;
    height: 100%;
    justify-content: space-between;
    align-items: center;
    border: 5px solid #3B72FF;
    background-color: #C191FF;
    border-radius: 10px;
    padding: 10px;
}

.left-name-box,
.left-tier-box,
.left-count-box {
    width: 280px;
    border: 5px solid #81C3FF;
    border-radius: 10px;
    background-color: #DBE7FF;
    text-align: center;
    font-size: 30px;
    -webkit-text-stroke: 1px black;
}

.left-name-box {
    height: 60px;
    justify-content: center;
    align-items: center;
}

.left-tier-box {
    height: 180px;
    justify-content: center;
    align-items: center;
}

.rank-img {
    width: 70px;
    height: 70px;
    margin: 10px 10px;
}

.rank-exp {
    width: 220px;
    height: 35px;
    margin: 10px 10px;
}

.left-count-box {
    height: 60px;
    justify-content: center;
    align-items: center;
}

.modal-content-right {
    width: 580px;
    border: 5px solid #3B72FF;
    background-color: #C191FF;
    border-radius: 10px;
    height: 100%;
    font-size: 30px;
    padding: 10px 20px;
    -webkit-text-stroke: 1px black;
}

.right-header-box {
    justify-content: space-between;
}

.write-review {
    margin-right: 20px;
    font-size: 20px;
    font-weight: 800;
    border-radius: 5px;
    background-color: hsl(138, 60%, 40%);
    color: white;
    padding: 10px;
    -webkit-text-stroke: unset !important;
}

.write-review:hover {
    background-color: hsl(138, 60%, 50%);
}

.right-solved-box {
    width: 100%;
    height: 270px;
    border: 3px solid #81C3FF;
    border-radius: 10px;
    background-color: #DBE7FF;
    margin-top: 10px;
    overflow: auto;
}

.solved-box {
    padding: 10px 20px;
}

.bronze,
.silver,
.gold,
.platinum,
.diamond {
    margin: 5px;
    padding: 5px;
    border-radius: 10px;
    font-size: 20px;
}

.bronze {
    border: 5px solid #AC8531;
    background-color: #FEC346;
    color: #AC8531;
}

.silver {
    border: 5px solid #B5A5A5;
    background-color: #E7D0D0;
    color: #B5A5A5;
}

.gold {
    border: 5px solid #B69E21;
    background-color: #FFD700;
    color: #B69E21;
}

.box-modal-bind {
    position: absolute;
    top: 0;
    left: 0;
}
</style>