<template>
  <div id="box-room" class="box-row box-main-exp">
    <img src="@/assets/default2.svg" alt="room thumbnail" class="room-img" />
    <div id="info-container" class="box-col">
      <div id="room-info" class="box-row box-main-title">
        <div class="box-main-num font-main-pagi">{{ room.id }}</div>
        <div class="title room-title">{{ room.title }}</div>
      </div>
      <div class="room-extra-info">
        <div class="item-cate box-main-group font-main-pagi">
          {{ room.category }}
        </div>
        <div class="item-parti box-main-group font-main-pagi">
          {{ room.people }} / {{ room.maxPeople }}
        </div>
        <div class="item-join box-main-group">
          <img src="@/assets/study-member.svg" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  room: Object,
});
</script>

<style scoped>
#box-room {
  height: 175px;
  padding: 20px 40px;
}

.room-img {
  width: 130px;
  height: 130px;
  margin-right: 10px;
}

#info-container {
  justify-content: space-between;
}

#room-info {
  align-items: center;
  width: 400px;
  height: 60px;
  border-radius: 5px;
  padding: 5px 10px;
  border: 3px solid #3b72ff;
}

.box-main-num {
  margin-right: 15px;
  padding: 2px;
  border: 2px solid #c191ff;
}

.room-title {
  font-size: 25px;
}

.room-extra-info {
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  gap: 10px;
}

.box-main-group {
  height: 40px;
  border: 3px solid #3b72ff;
}

.item-cate {
  grid-column: 1 / 3;
  padding-top: 5px;
  font-size: 22px;
  text-align: center;
}

.item-parti {
  grid-column: 6 / 8;
  padding-top: 5px;
  font-size: 22px;
  text-align: center;
}

.item-join {
  grid-column: 8 / 9;
}

.item-join img {
  width: 26px;
  margin-top: 4px;
  margin-left: 5.5px;
}
</style>
