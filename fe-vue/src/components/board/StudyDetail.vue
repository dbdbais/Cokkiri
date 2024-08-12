<script setup>
import RecruitmentBadge from "./RecruitmentBadge.vue";
import { joinStudy } from "@/api/board";
import { userStore } from "@/stores/user";

defineProps({
  detailData: Object,
});

const emit = defineEmits(["close"]);
const store = userStore();
const rankImgUrl = "/src/assets/rank/";

const closeBtn = function () {
  emit("close");
};

const sendJoin = function (sessionId) {
  const success = (res) => {
    console.log(res.data);
  };
  const fail = (err) => {
    console.log(err);
  };
  const joinData = {
    userName: store.user.nickname,
    sessionId: sessionId,
  };
  joinStudy(joinData, success, fail);
};
</script>

<template>
  <div class="study-detail board-modal box-main-con slideDown">
    <img
      src="/src/assets/exit.svg"
      alt="나가기"
      class="exit"
      @click="closeBtn"
    />
    <div class="detail-content">
      <RecruitmentBadge class="badge" :group-badge="detailData.isRecruitment" />
      <div class="title sub-title group-name">{{ detailData.regularName }}</div>
      <div class="top">
        <div class="box-psb text-p md member">
          <div
            v-for="(cnt, tier) in detailData.tiers"
            :key="tier.index"
            class="rank md"
          >
            <img :src="rankImgUrl + tier + '.svg'" alt="등급" />
            <span style="margin-left: 8px">{{ cnt }}명</span>
          </div>
        </div>
      </div>
      <div class="middle">
        <div class="box-psb box-col times">
          <div class="text-p nomal-text time-label">시간</div>
          <div class="time-list">
            <div
              v-for="time in detailData.times"
              :key="time.index"
              class="time bold-text box-main-group md"
            >
              {{ time }}
            </div>
          </div>
        </div>
        <div class="box-psb box-col rules">
          <div class="text-p nomal-text rule-label">규칙</div>
          <div class="rule-list">
            <div
              v-for="rule in detailData.rules"
              :key="rule.id"
              class="rule bold-text box-main-group md"
            >
              {{ rule }}
            </div>
          </div>
        </div>
      </div>
      <div class="bottom">
        <div class="box-psb text-p intro">{{ detailData.regularComment }}</div>
      </div>
      <button
        class="nomal-text board-btn"
        @click="sendJoin(detailData.sessionId)"
      >
        가입신청
      </button>
    </div>
  </div>
</template>

<style scoped>
.top,
.middle,
.bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: transparent;
}

.top {
  height: 80px;
  background-color: transparent;
}

.middle {
  height: 280px;
}

.bottom {
  height: 130px;
}
.group-name {
  margin-top: 5px;
}

.member {
  width: 99%;
  height: 60px;

  display: flex;
  justify-content: space-around;
  align-items: center;
}

.time,
.rule {
  width: 90%;
  height: 50px;

  margin-top: 10px;
  font-size: 20px;
  color: white;
  -webkit-text-stroke: 1.5px black;
}

.time-label,
.rule-label {
  font-size: 25px;
  padding-left: 10px;
}
.rank img,
span {
  background-color: transparent;
}
.rank img {
  scale: 1.3;
}
.times,
.rules {
  width: 49%;
  height: 280px;
}

.time-list,
.rule-list {
  display: flex;
  flex-direction: column;
  align-items: center;

  background-color: transparent;
}

.intro {
  width: 100%;
  height: 110px;

  padding-top: 5px;
  padding-left: 15px;
  font-size: 25px;
  overflow-y: scroll;
}

.study-detail {
  display: flex;
  justify-content: center;
}

.badge {
  top: 20px;
  left: 40px;
}

.detail-content {
  margin-top: 70px;
  width: 620px;
  height: 300px;
  background-color: transparent;
}
</style>
