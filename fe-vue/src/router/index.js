import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import StudyBoardView from "@/views/StudyBoardView.vue";
import MeetingView from "@/views/MeetingView.vue";
import WaitingRoomView from "@/views/WaitingRoomView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/start",
      name: "start",
      component: () => import("@/views/StartView.vue"),
    },
    {
      path: "/login",
      name: "login",
      component: () => import("@/views/LoginView.vue"),
    },
    {
      path: "/registry",
      name: "registry",
      component: () => import("@/views/RegistryView.vue"),
    },
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/study_board",
      name: "studyBoard",
      component: StudyBoardView,
    },
    {
      path: "/meeting/:roomId",
      name: "meeting",
      component: MeetingView,
    },
    {
      path: "/waiting_room/:roomId",
      name: "waitingRoom",
      component: WaitingRoomView,
    },
    {
      path: "/game/:gameId",
      name: "gameProgress",
      component: () => import("@/views/GameProgressView.vue"),
    },
  ],
});

export default router;
