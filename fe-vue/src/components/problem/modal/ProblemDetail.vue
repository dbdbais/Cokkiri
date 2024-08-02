<script setup>
import { infoSplit } from "@/utils/parse-problem";
defineProps({
    problemData: Object
});
</script>

<template>
    <div class="modal-overlay" @click.self="$emit('close')">
        <div class="main-con box-col">
            <div class="modal-header">
                <span class="title">{{ problemData.title }}</span>
            </div>
            <div class="modal-content">
                <div class="left-content">
                    <div class="problem-con">
                        <span class="title">문제</span><br />
                        <span class="content">{{ infoSplit(problemData.info) }}</span>
                    </div>
                </div>
                <div class="rigth-content right-grid">
                    <div class="input-con">
                        <span class="title">입력</span><br />
                        <span class="content">{{ problemData.algoInput }}</span>
                    </div>
                    <div class="output-con">
                        <span class="title">출력</span><br />
                        <span class="content">{{ problemData.algoOutput }}</span>
                    </div>
                    <div class="restrict-con">
                        <span class="title">제한</span><br />
                    </div>
                    <div class="category-con">
                        <span class="title">유형</span><br />
                        <span class="content">{{ problemData.algoType }}</span>
                    </div>
                    <div class="btn-con">
                        <div class="btn-review">
                            <span class="title">리뷰작성</span>
                        </div>
                        <div class="btn-like">
                            <span class="title">찜하기</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>

<style scoped>
.title {
    color: white;
    font-family: "RixInooAriDuriR";
    -webkit-text-stroke: 1px black;
    font-size: 35px;
}

.content {
    color: black;
    font-family: "RixInooAriDuriR";
    -webkit-text-stroke: 1px white;
}

.main-con {
    position: absolute;
    width: 1000px;
    height: 800px;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: #DBE7FF;
    padding: 20px 30px;
}

.modal-header>span {
    font-size: 60px;
}

.modal-content {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 20px;
    margin-top: 30px;
    width: 100%;
    height: 100%;
    overflow: auto;
}

.left-content {
    width: 100%;
    height: 100%;
}

.right-content {
    width: 100%;
    height: 100%;
}

.right-grid {
    display: grid;
    grid-template-rows: 2fr 2fr 1.5fr 1.25fr 1fr;
    gap: 10px;
}

.problem-con,
.input-con,
.output-con,
.restrict-con,
.category-con {
    width: 100%;
    border: 5px solid #3B72FF;
    border-radius: 10px;
    background-color: white;
    padding: 10px;
    overflow: auto;
}

.problem-con {
    height: 100%;
}

.btn-con {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 20px;
    text-align: center;
}

.btn-review {
    border: 5px solid #1959FF;
    background-color: #C191FF;
    padding: 10px;
}

.btn-like {
    border: 5px solid #7FB115;
    background-color: #CCFF61;
    padding: 10px;
}
</style>