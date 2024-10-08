<script setup>
import { ref, onMounted, watch, onUnmounted } from "vue";
import ace from "ace-builds";
import "ace-builds/src-noconflict/ext-language_tools";
import "ace-builds/src-noconflict/mode-python";
import "ace-builds/src-noconflict/mode-java";
import "ace-builds/src-noconflict/mode-c_cpp";
import "ace-builds/src-noconflict/theme-chrome";
import "ace-builds/src-noconflict/ext-options";
import { insert } from "@/api/submit";
import { useGameStore } from "@/stores/game";
import { problemStore } from "@/stores/problem";
import { useTriggerStore } from "@/stores/trigger";
import { userStore } from "@/stores/user";
import { sendSubmit } from "@/api/webRTC";
import { useSubmitStore } from "@/stores/submit";
import { useItemStore } from "@/stores/item";
import { useCorrectStore } from "@/stores/correct";

defineProps({
  bigfont: Boolean,
  minimum: Number,
  prevent: Boolean,
  roomData: Object,
  userCnt: Number,
});
const emit = defineEmits(["submit-code", "correct"]);

const editor = ref(null);
const selectedLanguage = ref("python");
const inputText = ref("");
const outputText = ref("");
const iStore = useItemStore();
const uStore = userStore();
const pStore = problemStore();
const tStore = useTriggerStore();
const gameStore = useGameStore();
const submitStore = useSubmitStore();
const cStore = useCorrectStore();
const trigger = ref(false);
const editorFontSize = ref(iStore.currentFontSize);
const userCodeList = ref(pStore.userCodeList);

const defaultCode = {
  python: "",
  java: "import java.io.*;\nimport java.util.*;\n\npublic class Temp {\n    //do not change Class Name\n    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));\n    static StringTokenizer st;\n    public static void main(String[] args) throws IOException {\n        \n        \n    }}",
  // c: '#include <stdio.h>\nint main() {\n    printf("Hello, World!");\n    return 0;\n}',
  cpp: "#include <iostream>\nusing namespace std;\nint main() {\n    \n    return 0;\n}",
};

const userCode = ref({ ...defaultCode });
// console.log(userCode);

onMounted(async () => {
  // selectedLanguage.value = "java";
  trigger.value = true;
  setTimeout(() => {
    trigger.value = false;
  }, 500);
  // initializeEditor(
  //   userCodeList.value[tStore.currentProblemNum].code,
  //   userCodeList.value[tStore.currentProblemNum].language
  // );
  getInit();
  // document.getElementById("language").dispatchEvent(new Event("change"));
  window.addEventListener("beforeunload", saveData);
});

onUnmounted(() => {
  saveData();
  iStore.setFontSize(20);
});

function saveData() {
  (userCodeList.value[tStore.currentProblemNum].code = editor.value.getValue()),
    (userCodeList.value[tStore.currentProblemNum].language =
      selectedLanguage.value);
}

function getInit() {
  // console.log(
  //   userCodeList.value[tStore.currentProblemNum].code,
  //   userCodeList.value[tStore.currentProblemNum].language
  // );

  initializeEditor(
    userCodeList.value[tStore.currentProblemNum].code,
    userCodeList.value[tStore.currentProblemNum].language
  );

  console.log(userCodeList.value);
}

watch(tStore, () => {
  console.log("문제 변경!");
  // console.log(userCodeList.value[pStore.beforeProblemNum]);
  // console.log(userCodeList.value[pStore.currentProblemNum]);
  // console.log(editor.value.getValue());
  userCodeList.value[tStore.beforeProblemNum].code = editor.value.getValue();
  userCodeList.value[tStore.beforeProblemNum].language = selectedLanguage.value;
  outputText.value = "";
  inputText.value = "";
  trigger.value = true;
  setTimeout(() => {
    trigger.value = false;
  }, 500);
  getInit();
});

const initializeEditor = (val, language) => {
  selectedLanguage.value = language;
  editor.value = ace.edit("editor");
  editor.value.setTheme("ace/theme/chrome");
  editor.value.session.setMode(`ace/mode/${language}`);
  editor.value.setValue(val ? val : defaultCode[language]);
  editor.value.setFontSize(editorFontSize.value);
  editor.value.setOptions({
    enableBasicAutocompletion: true,
    enableLiveAutocompletion: true,
  });

  editor.setOptions = {
    enableBasicAutocompletion: true,
    enableLiveAutocompletion: true,
  };
  // Set the desired font size here
};

// 2초 간격으로 메시지를 보여줌

// 5초 후에 정지

const fontReduce = () => {
  const saveVal = editor.value.getValue();
  console.log("폰트 작게!");
  let timerId = setInterval(() => {
    console.log("작아지는 중!");
    if (editorFontSize.value > 12) {
      editorFontSize.value -= 5;
      initializeEditor(saveVal, selectedLanguage.value);
    }
  }, 100);
  setTimeout(() => {
    clearInterval(timerId);
    iStore.setFontSize(editorFontSize.value);
  }, 1000);
};

const fontIncrease = () => {
  const saveVal = editor.value.getValue();
  console.log("폰트 크게!");
  let timerId = setInterval(() => {
    console.log("커지는 중!");
    if (editorFontSize.value < 60) {
      editorFontSize.value += 5;
      initializeEditor(saveVal, selectedLanguage.value);
    }
  }, 100);
  setTimeout(() => {
    clearInterval(timerId);
    iStore.setFontSize(editorFontSize.value);
  }, 1000);
};

watch(gameStore, () => {
  if (gameStore.bigFont) {
    fontIncrease();
  }
  if (gameStore.smallFont) {
    fontReduce();
  }
});

watch(selectedLanguage, (newLang, oldLang) => {
  if (!trigger.value) {
    initializeEditor(null, newLang);
  }
});

const lodingSubmit = ref(false);

const runCode = async (isSubmit) => {
  if (isSubmit === 1) {
    lodingSubmit.value = true;
  }

  const code = editor.value.getValue();
  const language = selectedLanguage.value;

  userCodeList.value[tStore.currentProblemNum].code = code;
  userCodeList.value[tStore.currentProblemNum].language = language;

  const data = {
    algo_num: userCodeList.value[tStore.currentProblemNum].no,
    user_id: uStore.user.id,
    language: userCodeList.value[tStore.currentProblemNum].language,
    submit_code: userCodeList.value[tStore.currentProblemNum].code,
    ipt: inputText.value === "" ? null : inputText.value,
    isSubmit: isSubmit,
  };

  insert(data).then((res) => {
    console.log(res.data);
    if (isSubmit === 0) {
      if (inputText.value === "") {
        let outPut = "";

        Object.keys(res.data.tcOutput).forEach((key) => {
          outPut += `예제 입력 ${key}\n${res.data.tcOutput[key]}\n`;
          outPut += res.data.result[key] ? "정답입니다!" : "틀렸습니다.";
          outPut += "\n";
        });
        outputText.value = outPut;
      } else {
        outputText.value = res.data.opt;
      }
    } else {
      lodingSubmit.value = false;
      console.log(res.data);
      emit("submit-code", {
        username: uStore.user.nickname,
        problemNum: tStore.currentProblemNum,
        correct: res.data.correct ? 1 : 0,
      });

      if (res.data.correct) {
        cStore.correct(tStore.currentProblemNum);
        emit("correct");
        Swal.fire({
          icon: "success",
          title: "정답입니다!",
        });
      } else {
        Swal.fire({
          icon: "error",
          title: `${Math.ceil(res.data.accuracy * 100)}점 입니다..`,
        });
      }
    }
  });
};

const shareCode = (userCnt) => {
  const code = editor.value.getValue();
  const language = selectedLanguage.value;

  userCodeList.value[tStore.currentProblemNum].code = code;
  userCodeList.value[tStore.currentProblemNum].language = language;
  const problemNum =
    pStore.selectedProblemList.length === 0
      ? "문제 없음"
      : tStore.currentProblemNum;
  const data = `${problemNum}|!|${uStore.user.nickname}|!|${
    userCodeList.value[tStore.currentProblemNum].language
  }|!|${userCodeList.value[tStore.currentProblemNum].code}|!|${userCnt}`;
  sendSubmit(data);
  console.log(userCnt);
};

const clearInput = () => {
  inputText.value = "";
};
</script>

<template>
  <div>
    <div class="editor-con box-w">
      <div id="editor-container">
        <select
          id="language"
          ref="language"
          class="box language bold-text"
          v-model="selectedLanguage"
        >
          <option value="python">Python</option>
          <option value="java">Java</option>
          <option value="cpp">C++</option>
          <!-- <option value="c">C</option> -->
        </select>

        <div id="editor"></div>
        <div class="flex-align" style="position: relative">
          <button class="run-btn bold-text" @click="runCode(0)">실행</button>
          <button
            class="submit-btn bold-text"
            :style="{ scale: minimum * 0.01 }"
            :class="{
              prevent: prevent,
              loding: lodingSubmit,
            }"
            :disabled="prevent"
            @click="runCode(1)"
          >
            {{ lodingSubmit ? "" : "제출" }}
          </button>
          <div class="dots-flow" v-if="lodingSubmit"></div>
        </div>
        <button
          v-if="!roomData.isGame"
          class="share-btn bold-text"
          @click="shareCode(userCnt)"
        >
          공유
        </button>
      </div>
    </div>
    <div class="input-con box-w">
      <div id="input-output-area">
        <div id="input">
          <p class="bold-text">
            Input
            <button class="clear-btn bold-text" @click="clearInput">
              초기화
            </button>
          </p>

          <textarea
            id="inputText"
            class="nomal-text"
            v-model="inputText"
            rows="10"
            cols="30"
          ></textarea>
        </div>
        <div id="output">
          <p class="bold-text">Output</p>
          <pre id="outputText" class="nomal-text">{{ outputText }}</pre>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.input-con {
  width: 100%;
  height: 200px;
  margin-top: 50px;
}

.editor-con {
  width: 100%;
  height: 450px;
  margin-top: 50px;
  border-radius: 0px 10px 10px 10px;
  position: relative;
}

.language {
  position: absolute;
  width: 130px;
  height: 50px;
  padding-left: 10px;
  top: -60px;
  right: 0;
  font-size: 20px;
  background-color: #9fbaff;
}

option {
  color: black;
}

#input-output-area {
  width: 100%;
  margin: 10px;
  display: flex;
  justify-content: space-between;
}

.run-btn,
.submit-btn,
.share-btn,
.clear-btn {
  border-width: 5px;
  border-radius: 10px;
  border-color: #3b72ff;
  background-color: #9fbaff;
}

.run-btn,
.share-btn,
.submit-btn {
  width: 80px;
  height: 40px;
  margin-top: 10px;
  transition: all 0.5s ease-in-out;
}

.share-btn {
  position: absolute;
  bottom: -50px;
  right: 0;
}

.submit-btn {
  margin-left: 20px;
}

.clear-btn {
  width: 90px;
  height: 35px;
  font-size: 20px;
  position: absolute;
  top: -5px;
  right: 5px;
}

#input,
#output {
  width: 48%;
  margin: 0 5px;
  position: relative;
}

#editor-container {
  width: 100%;
}

textarea {
  width: 100%;
  flex-grow: 1;
  resize: none;
  border: 2px solid black;
  font-size: 20px;
  margin-top: 10px;
}

textarea,
#outputText {
  margin-top: 15px;
  height: 130px;
  overflow-y: auto;
}

#editor {
  height: 440px;
  width: 100%;
  border-radius: 5px;
}

#inputText {
  padding: 15px;
}

#outputText {
  border: 2px solid black;
  width: 90%;
  overflow: auto;
  font-size: 20px;
  white-space: pre-wrap;
}

.prevent {
  background-color: rgb(117, 117, 117);
  border-color: gray;
  color: gray;
}

.dots-flow {
  scale: 0.6;
}

.dots-flow:before {
  -webkit-animation: dots-flow 0.85s infinite ease;
  animation: dots-flow 0.85s infinite ease;
  border-radius: 100%;
  content: "";
  height: 16px;
  position: absolute;
  top: 5px;
  left: -67px;
  -webkit-transform: translate(-50%, -40px);
  transform: translate(-50%, -40px);
  width: 16px;
}

.loding {
  background-color: gray;
}

@-webkit-keyframes dots-flow {
  0%,
  100% {
    -webkit-box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white,
      26px 32px 0 0 white;
    box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white, 26px 32px 0 0 white;
  }

  35% {
    -webkit-box-shadow: -26px 32px 0 4px purple, 0px 32px 0 0 white,
      26px 32px 0 0 white;
    box-shadow: -26px 32px 0 4px purple, 0px 32px 0 0 white, 26px 32px 0 0 white;
  }

  50% {
    -webkit-box-shadow: -26px 32px 0 0 white, 0px 32px 0 4px purple,
      26px 32px 0 0 white;
    box-shadow: -26px 32px 0 0 white, 0px 32px 0 4px purple, 26px 32px 0 0 white;
  }

  65% {
    -webkit-box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white,
      26px 32px 0 4px purple;
    box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white,
      26px 32px 0 4px purple;
  }
}

@keyframes dots-flow {
  0%,
  100% {
    -webkit-box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white,
      26px 32px 0 0 white;
    box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white, 26px 32px 0 0 white;
  }

  35% {
    -webkit-box-shadow: -26px 32px 0 4px purple, 0px 32px 0 0 white,
      26px 32px 0 0 white;
    box-shadow: -26px 32px 0 4px purple, 0px 32px 0 0 white, 26px 32px 0 0 white;
  }

  50% {
    -webkit-box-shadow: -26px 32px 0 0 white, 0px 32px 0 4px purple,
      26px 32px 0 0 white;
    box-shadow: -26px 32px 0 0 white, 0px 32px 0 4px purple, 26px 32px 0 0 white;
  }

  65% {
    -webkit-box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white,
      26px 32px 0 4px purple;
    box-shadow: -26px 32px 0px 0 white, 0px 32px 0 0 white,
      26px 32px 0 4px purple;
  }
}
</style>
