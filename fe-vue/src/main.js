import { createApp } from "vue";
import { createPinia } from "pinia";
// pinia localStore 사용
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

import App from "./App.vue";
import router from "./router";

import "./assets/css/main.css";
import "./assets/css/animation.css";

import "highlight.js/styles/atom-one-dark.css";
import hljs from "highlight.js/lib/core";
import java from "highlight.js/lib/languages/java";
import python from "highlight.js/lib/languages/python";
import cpp from "highlight.js/lib/languages/cpp";
import hljsVuePlugin from "@highlightjs/vue-plugin";

hljs.registerLanguage("java", java);
hljs.registerLanguage("python", python);
hljs.registerLanguage("cpp", cpp);

const app = createApp(App);
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

// app.use(createPinia());
app.use(router);
app.use(pinia);
app.use(hljsVuePlugin);

app.mount("#app");
