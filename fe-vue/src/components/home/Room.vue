<template>
    <div class="room">
        <div class="room-info">
            <h3>{{ room.name }}</h3>
            <p>{{ room.members }} members</p>
        </div>
        <div class="room-actions">
            <button>Join</button>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Room',
    props: {
        room: Object
    }
}
</script>

<style scoped>
.room {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #fff;
    padding: 10px;
    margin: 10px;
    border-radius: 5px;
    width: 45%;
}

.room-info {
    display: flex;
    flex-direction: column;
}

.room-actions button {
    padding: 5px 10px;
}
</style>
