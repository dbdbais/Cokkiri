<template>
    <div class="main-content">
        <div class="tabs">
            <button>전체</button>
            <button>공부방</button>
            <button>게임방</button>
        </div>
        <div class="rooms">
            <Room v-for="room in rooms" :key="room.id" :room="room" />
        </div>
    </div>
</template>

<script>
import Room from './Room.vue'

export default {
    name: 'MainContent',
    components: {
        Room
    },
    data() {
        return {
            rooms: [
                { id: 1, name: '알고리즘 고수들방', members: 116 },
                { id: 2, name: '초보. 매너방요', members: 206 },
                // Add more rooms here
            ]
        }
    }
}
</script>

<style scoped>
.main-content {
    flex: 1;
    padding: 20px;
    background-color: #e4f0ff;
}

.tabs button {
    margin: 0 5px;
}

.rooms {
    display: flex;
    flex-wrap: wrap;
    margin-top: 20px;
}
</style>
