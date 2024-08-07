<script setup>

</script>

<template>
    <div>
        <RouterLink :to="{ name: 'login' }">
            <div class="start">
                <span>시작하기</span>
            </div>
        </RouterLink>
    </div>
</template>

<style scoped>
.start {
    border: 3px solid #3B72FF;
    background-color: #C191FF;
}
</style>