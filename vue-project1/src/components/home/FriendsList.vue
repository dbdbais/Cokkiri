<template>
    <div class="friends-list">
        <h3>친구 추가</h3>
        <ul>
            <li v-for="friend in friends" :key="friend.id">
                <img :src="friend.avatar" alt="Friend Avatar" />
                <span>{{ friend.name }}</span>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    name: 'FriendsList',
    data() {
        return {
            friends: [
                { id: 1, name: '이지민', avatar: 'path/to/avatar1.png' },
                { id: 2, name: '김영수', avatar: 'path/to/avatar2.png' },
                // Add more friends here
            ]
        }
    }
}
</script>

<style scoped>
.friends-list {
    padding: 20px;
    border: 5px solid #3b72ff;
    border-radius: 10px;
    background-color: #c191ff;
    width: 300px;
}

.friends-list ul {
    list-style-type: none;
    padding: 0;
}

.friends-list li {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
}

.friends-list img {
    border-radius: 50%;
    margin-right: 10px;
}
</style>
