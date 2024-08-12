import { createRouter, createWebHistory } from "vue-router";
import { storeToRefs } from "pinia";
import { userStore } from "@/stores/user";
import HomeView from "@/views/HomeView.vue";
import StudyBoardView from "@/views/StudyBoardView.vue";
import MeetingView from "@/views/MeetingView.vue";
import WaitingRoomView from "@/views/WaitingRoomView.vue";

const onlyAuthUser = (to, from, next) => {
  const store = userStore();
  const { user } = storeToRefs(store);
  if (user.value.id && user.value.password) {
    next();
  } else {
    alert("로그인이 필요한 기능입니다.");
    next({ name: "login" });
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/start",
      name: "start",
      component: () => import("@/views/StartView.vue"),
    },
    {
      path: "/",
      name: "login",
      component: () => import("@/views/LoginView.vue"),
    },
    {
      path: "/registry",
      name: "registry",
      component: () => import("@/views/RegistryView.vue"),
    },
    {
      path: "/home",
      name: "home",
      beforeEnter: onlyAuthUser,
      component: HomeView,
    },
    {
      path: "/study_board",
      name: "studyBoard",
      beforeEnter: onlyAuthUser,
      component: StudyBoardView,
    },
    {
      path: "/meeting/:roomId",
      name: "meeting",
      beforeEnter: onlyAuthUser,
      component: MeetingView,
    },
    {
      path: "/waiting_room/:roomId",
      name: "waitingRoom",
      beforeEnter: onlyAuthUser,
      component: WaitingRoomView,
    },
    {
      path: "/game/:gameId",
      name: "gameProgress",
      beforeEnter: onlyAuthUser,
      component: () => import("@/views/GameProgressView.vue"),
    },
    {
      path: "/problem",
      name: "problem",
      beforeEnter: onlyAuthUser,
      component: () => import("@/views/ProblemView.vue"),
    },
  ],
});

export default router;
