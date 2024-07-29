<script setup>
import RecruitmentBadge from "./RecruitmentBadge.vue";
import StudyDetailTime from "@/components/board/StudyDetailTime.vue";
import StudyDetailRule from "@/components/board/StudyDetailRule.vue";
defineProps({
  detailData: Object,
});

const emit = defineEmits(["close"]);

const rankImgUrl = "/src/assets/rank/";

const closeBtn = function () {
  emit("close");
};
</script>

<template>
  <div class="study-detail board-modal box-main-con">
    <img
      src="/src/assets/exit.svg"
      alt="나가기"
      class="exit"
      @click="closeBtn"
    />
    <div class="detail-content">
      <RecruitmentBadge class="badge" />
      <div class="title sub-title group-name">{{ detailData.groupName }}</div>
      <div class="top">
        <div class="box-psb text-p md lang">
          {{ detailData.groupData.lang }}
        </div>
        <div class="box-psb text-p md member">
          <div
            v-for="member in detailData.groupData.member"
            :key="member.id"
            class="rank md"
          >
            <img :src="member.img" alt="등급" />
            <span style="margin-left: 8px">{{ member.cnt }}명</span>
          </div>
        </div>
      </div>
      <div class="middle">
        <div class="box-psb box-col times">
          <div class="text-p nomal-text-title">시간</div>
          <div class="time-list">
            <StudyDetailTime
              v-for="time in detailData.groupData.times"
              :key="time.week"
              :study-time="time"
            />
          </div>
        </div>
        <div class="box-psb box-col rules">
          <div class="text-p nomal-text-title">규칙</div>
          <div class="rule-list">
            <StudyDetailRule
              v-for="rule in detailData.groupData.rules"
              :key="rule.id"
              :study-rule="rule"
            />
          </div>
        </div>
      </div>
      <div class="bottom">
        <div class="box-psb text-p intro">{{ detailData.groupData.intro }}</div>
      </div>
      <button class="nomal-text board-btn">가입신청</button>
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
  height: 70px;
  background-color: transparent;
}

.middle {
  height: 280px;
}

.bottom {
  height: 150px;
}

.lang {
  width: 20%;

  height: 50px;
}

.member {
  width: 79%;
  height: 50px;

  display: flex;
  justify-content: space-around;
  align-items: center;
}

.rank img,
span {
  background-color: transparent;
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
  height: 130px;

  padding-top: 5px;
  padding-left: 15px;
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
