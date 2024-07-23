<template>
    <div class="box-main-con">
        <div id="add-friend-container" class="box-main-exp box-row">
            <img src="@/assets/friend-plus.svg" alt="add-friend" class="icon" />
            <div id="add-friend-btn" class="title main-title">친구 추가</div>
        </div>
        <Accordion id="friend-list" />
    </div>
</template>

<script setup>
import Accordion from "@/components/home/Accordion.vue"

</script>

<style scoped>
#add-friend-container {
    width: 200px;
    margin-bottom: 5px;
    padding: 5px 25px;
}

#add-friend-btn {
    margin-left: 12px;
    font-size: 25px;
}

#friend-list {
    margin-top: 15px;
}
</style>
