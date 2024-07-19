import { createApp } from "vue";
import { createPinia } from "pinia";
// pinia localStore 사용
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

import App from "./App.vue";
import router from "./router";

import "./assets/css/main.css";

const app = createApp(App);
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

// app.use(createPinia());
app.use(router);
app.use(pinia);

app.mount("#app");
