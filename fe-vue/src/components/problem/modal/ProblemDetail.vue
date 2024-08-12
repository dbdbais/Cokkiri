<script setup>
import { ref, onMounted } from "vue";
import { useModal } from "@/composables/useModal";
import { getSolved } from "@/api/submit"
import { userStore } from "@/stores/user";
import SubmitList from "@/components/problem/modal/SubmitList.vue";
import ReviewItem from "@/components/problem/ReviewItem.vue";

const uStore = userStore();
const submitData = ref({});
const { isModalOpen, openModal, closeModal } = useModal();

const fetchSubmit = async () => {
    try {
        const response = await getSolved(uStore.user.id);
        console.log(response);
        submitData.value = response.data;
        openModal();
    } catch (error) {
        console.error(error);
    }
};


const props = defineProps({
    problemData: Object,
    reviewData: Object
});

const tmpReviewData = ref([
    {
        "id": 1,
        "user": {
            "nickname": "user1234"
        },
        "content": "리뷰내용",
        "code": "print('hello, world')",
    },
    {
        "id": 2,
        "user": {
            "nickname": "user1234"
        },
        "content": "리뷰내용",
        "code": "print('hello, world')",
    },
    {
        "id": 3,
        "user": {
            "nickname": "user1234"
        },
        "content": "리뷰내용",
        "code": "print('hello, world')",
    }
]);

onMounted(() => {
    tmpReviewData.value = tmpReviewData.value.map((item) => {
        item.code = "문제를 해결하거나 3회 이상 코드를 제출해야 확인할 수 있습니다."
        item.content = "문제를 해결하거나 3회 이상 코드를 제출해야 확인할 수 있습니다."
    });
})
</script>

<template>
    <div class="modal-overlay">
        <div class="modal-con">
            <div class="modal-header box-row">
                <div>
                    <span class="title header-span">{{ problemData.no + ". " }}</span>
                    <span class="title header-span">{{ problemData.title }}</span>
                </div>
                <div class="box-row">
                    <img src="@/assets/exit.svg" alt="close" class="icon-close" @click="$emit('close')" />
                </div>
            </div>
            <div class="modal-content box-grid">
                <div class="left-con">
                    <span v-html="problemData.info"></span>
                </div>
                <!-- <div class="btn-con">
                        <div class="btn-review" @click="fetchSubmit">
                            <span class="title main-title">리뷰하기</span>
                        </div>
                    </div> -->
                <div class="right-con">
                    <div class="overlay">
                        <span>문제를 해결하거나 3회 이상 제출해야합니다.</span>
                    </div>
                    <div v-for="review in tmpReviewData" :key="review.id">
                        <ReviewItem :review="review" />
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- <SubmitList v-if="isModalOpen" @close="closeModal" :problem-id="problemData.no" :submit-data="submitData" /> -->
</template>

<style scoped>
.title {
    color: white;
    font-family: "RixInooAriDuriR";
    -webkit-text-stroke: 1px black;
    font-size: 35px;
}

.modal-con {
    position: absolute;
    top: 60px;
    left: 180px;
    width: 1600px;
    height: 980px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: #DBE7FF;
    padding: 20px 30px;
}

.modal-header {
    justify-content: space-between;
    align-items: center;
}

.header-span {
    font-size: 55px;
    text-align: center;
}

.icon-close {
    width: 60px;
    margin-left: 20px;
}

.modal-content {
    margin-top: 30px;
    width: 100%;
    height: 100%;
}

.box-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-rows: 1fr;
    gap: 20px;
}

.left-con {
    width: 100%;
    height: 800px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: white;
    padding: 10px;
    overflow: auto;
}

.right-con {
    position: relative;
    width: 100%;
    height: 800px;
}

.overlay {
    position: absolute;
    color: white;
    top: 0;
    left: 00px;
    width: 755px;
    height: 800px;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 1000;
}

.overlay>span {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    text-align: center;

}
</style>