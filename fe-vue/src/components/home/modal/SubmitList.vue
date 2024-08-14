<script setup>
import { ref, onMounted } from "vue";
import { getFilteredSubmit } from "@/api/submit";
import { userStore } from "@/stores/user";
import WriteReview from "@/components/home/WriteReview.vue";

const props = defineProps({
    problemId: Number,
});
const submitData = ref();
const uStore = userStore();

onMounted(async () => {
    try {
        const response = await getFilteredSubmit(uStore.user.id, props.problemId.no);
        console.log(response);
        submitData.value = response.data;
    } catch (error) {
        console.error(error);
    }
})

// const tmpSubmitData = ref([
//     {
//         submit_id: 1,
//         correct: 1,
//         created_time: "2021-10-10",
//         code: "print('hello, world')",
//         algo_num: 1000,
//         user_id: "ssafy"
//     },
//     {
//         submit_id: 2,
//         correct: 1,
//         created_time: "2021-10-10",
//         code: "print('hello, world')",
//         algo_num: 1000,
//         user_id: "ssafy"
//     },
//     {
//         submit_id: 3,
//         correct: 1,
//         created_time: "2021-10-10",
//         code: "print('hello, world')",
//         algo_num: 1000,
//         user_id: "ssafy"
//     }
// ]);

</script>

<template>
    <div class="modal-overlay">
        <div class="modal-con">
            <div class="modal-header box-row">
                <span class="title main-title">리뷰작성</span>
                <img src="@/assets/exit.svg" alt="close" class="icon-close" @click="$emit('close')" />
            </div>
            <div class="modal-content">
                <WriteReview v-for="(submit, index) in submitData" :problem-id="problemId" :submit="submit"
                    :index="index + 1" />
            </div>
        </div>
    </div>
</template>

<style scoped>
.modal-overlay {
    background-color: rgba(0, 0, 0, 0);
}

.modal-con {
    position: absolute;
    right: 30px;
    width: 800px;
    height: 660px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: #DBE7FF;
    padding: 20px 30px 0 20px;
}

.modal-header {
    justify-content: space-between;
    align-items: center;
}

.modal-content {
    margin-top: 20px;
    height: 500px;
    overflow: auto;
}

.icon-close {
    width: 60px;
}

.modal-header span {
    font-size: 55px;
}

.input-con span {
    font-size: 30px;
}

.code-con {
    height: 210px;
}

.code {
    overflow: auto;
    height: 190px;
}

.review-con {
    height: 250px;
    margin-top: 20px;
}

textarea {
    width: 100%;
    height: 200px;
}

.btn-con {
    justify-content: space-between;
}

.btn {
    width: 120px;
    padding: 10px 0;
    text-align: center;
    border-radius: 10px;
}

.btn:nth-child(1) {
    background-color: #69f0aa;
}

.btn:nth-child(1):hover {
    background-color: #5be09c;
}

.btn:nth-child(2) {
    background-color: #6969f0;
}

.btn:nth-child(2):hover {
    background-color: #5b5be0;
}

.btn span {
    font-size: 25px;
}
</style>