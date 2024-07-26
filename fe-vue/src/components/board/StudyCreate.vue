<script setup></script>

<template>
  <div class="board-modal">
    <div class="bold-text" style="margin: 10px 0 0 30px">스터디 만들기</div>
    <div class="md-col">
      <div class="create-box md-col">
        <div class="flex-align">
          <div class="study-name md-col">
            <label for="name" class="bold-text">스터디명</label>
            <input type="text" name="name" id="name" />
          </div>
          <div class="member bold-text md-col">
            <label for="member">인원</label>
            <input type="number" id="member" />
          </div>
        </div>
        <div
          class="flex-align"
          style="justify-content: space-between; width: 550px; margin-top: 10px"
        >
          <div class="bold-text">스터디 시간</div>
        </div>
      </div>
      <button class="board-btn nomal-text">스터디 만들기</button>
    </div>
  </div>
</template>

<style scoped>
.bold-text {
  font-size: 30px;
}
.create-box {
  margin-top: 10px;
}
.board-btn {
  margin-top: 10px;
}
.study-name {
  margin-top: 20px;
  width: 550px;

  justify-content: space-between;
}
#name {
  width: 300px;
  height: 40px;
  border: 3px solid black;

  border-radius: 10px;
}
#member {
  width: 50px;
  height: 50px;
  border: 3px solid black;
  margin-left: 20px;
  border-radius: 10px;
}
</style>
