<script setup>

</script>

<template>
    <div class="box-row">
        <div class="arrow-container">
            <img id="arrow-b" class="arrow" src="@/assets/arrow_back.svg"></img>
        </div>
        <div id="pagi-text" class="font-main-pagi">
            1 / 5
        </div>
        <div class="arrow-container">
            <img id="arrow-f" class="arrow" src="@/assets/arrow_forward.svg"></img>
        </div>
    </div>
</template>

<style scoped>
#pagi-container {
    justify-content: center;
    margin-top: 25px;
}

.arrow-container {
    width: 50px;
    height: 50px;
    background-color: #e6e7e8;
    border-radius: 10px;
    margin-top: 5px;
}

.arrow {
    width: 45px;
}

#arrow-b {
    margin-top: 3px;
    margin-left: 10px;
}

#arrow-f {
    margin-top: 3px;
    margin-left: 3px;
}


.font-main-pagi {
    font-size: 55px;
    -webkit-text-stroke: 2px #378775;
    margin-left: 25px;
    margin-right: 25px;
}
</style>