div<template>
    <div class="accordion">
        <div v-for="(item, index) in items" :key="index" class="accordion-item ">
            <button class="accordion-header" @click="toggle(index)">
                {{ item.title }}
            </button>
            <div v-if="isOpen(index)" class="accordion-content">
                <div v-for="(member, index2) in item.member" :key="index2">
                    <slot :name="`content-${index2}`">{{ member.name }}</slot>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import friendList from "@/assets/data/friendList.json";

const items = ref([]);

friendList.studyList.forEach(study => {
    items.value.push({
        title: study.title,
        member: study.member
    })
});

items.value.push({
    title: "친구",
    member: friendList.friendList.member
});

const openIndex = ref(null);

const toggle = (index) => {
    openIndex.value = openIndex.value === index ? null : index;
};

const isOpen = (index) => {
    return openIndex.value === index;
};

</script>

<style>
.accordion {
    border: 1px solid #ccc;
    border-radius: 4px;
}

.accordion-item {
    border-bottom: 1px solid #ccc;
}

.accordion-header {
    background: #f1f1f1;
    border: none;
    width: 100%;
    text-align: left;
    padding: 10px;
    cursor: pointer;
    font-size: 16px;
}

.accordion-content {
    padding: 10px;
    background: #fff;
}
</style>
