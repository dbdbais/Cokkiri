<template>
    <div class="box-wbb">
        <img src="" alt="Profile Picture" />
        <div class="info">
            <h2>김퐁덕</h2>
            <div class="progress">
                <div class="progress-bar" style="width: 35%;"></div>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    name: 'Profile'
}
</script>

<style scoped>
.profile {
    display: flex;
    align-items: center;
    padding: 20px;
    background-color: #d4e4ff;
    width: 300px;
}

.info {
    margin-left: 10px;
}

.progress {
    background-color: #eee;
    border-radius: 5px;
    overflow: hidden;
    width: 100px;
    height: 10px;
}

.progress-bar {
    background-color: #ff9999;
    height: 100%;
}
</style>
