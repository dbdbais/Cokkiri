<template>
    <div class="box-p">
        <div id="add-friend-container" class="box-wb box-row">
            <img src="@/assets/friend-plus.svg" alt="add-friend" class="icon" />
            <div id="add-friend-btn" class="title main-title">친구 추가</div>
        </div>
        <Accordion />
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
</style>
