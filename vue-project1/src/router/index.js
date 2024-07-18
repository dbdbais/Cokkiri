import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import StudyRecruitmentHomeView from "@/views/StudyRecruitmentHomeView.vue";
import { useLinkStore } from "@/stores/link";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/study_recruitment",
      name: "studyRecruitment",
      component: StudyRecruitmentHomeView,
    },
  ],
});

export default router;
