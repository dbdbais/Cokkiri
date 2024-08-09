<script setup>
import { ref, onMounted, watch } from "vue";
import axios from "axios";
import ace from "ace-builds";
import "ace-builds/src-noconflict/mode-python";
import "ace-builds/src-noconflict/mode-java";
import "ace-builds/src-noconflict/mode-c_cpp";
import "ace-builds/src-noconflict/theme-monokai";
import { useGameStore } from "@/stores/game";

defineProps({
  bigfont: Boolean,
});

const editor = ref(null);
const selectedLanguage = ref("python");
const inputText = ref("");
const outputText = ref("");
const fontReduceVal = ref(false);
const fontIncreaseVal = ref(false);
const editorFontSize = ref(18);

const defaultCode = {
  python: 'print("Hello, World!")',
  java: 'public class Temp {\n    public static void main(String[] args) {\n        System.out.println("Hello, World!");\n    }\n}',
  c: '#include <stdio.h>\nint main() {\n    printf("Hello, World!");\n    return 0;\n}',
  cpp: '#include <iostream>\nint main() {\n    std::cout << "Hello, World!" << std::endl;\n    return 0;\n}',
};

const userCode = ref({ ...defaultCode });

const initializeEditor = (val) => {
  editor.value = ace.edit("editor");
  editor.value.setTheme("ace/theme/Dawn");
  editor.value.session.setMode("ace/mode/python");
  editor.value.setValue(val ? val : defaultCode.python);
  editor.value.setFontSize(editorFontSize.value); // Set the desired font size here
};

// 2초 간격으로 메시지를 보여줌

// 5초 후에 정지

const fontReduce = () => {
  const saveVal = editor.value.getValue();
  console.log("폰트 작게!");
  let timerId = setInterval(() => {
    console.log("작아지는 중!");
    if (editorFontSize.value > 9) {
      editorFontSize.value -= 1;
      initializeEditor(saveVal);
    }
    // console.log(editorFontSize.value);
  }, 200);
  setTimeout(() => {
    clearInterval(timerId);
  }, 1000);
};

const gameStore = useGameStore();

const fontIncrease = () => {
  const saveVal = editor.value.getValue();
  console.log("폰트 크게!");
  let timerId = setInterval(() => {
    console.log("커지는 중!");
    if (editorFontSize.value < 40) {
      editorFontSize.value += 2;
      initializeEditor(saveVal);
    }
    // console.log(editorFontSize.value);
  }, 200);
  setTimeout(() => {
    clearInterval(timerId);
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

onMounted(() => {
  initializeEditor();

  watch(selectedLanguage, (newLang) => {
    const currentMode = editor.value.session.getMode().$id;
    const currentLanguage = currentMode.includes("python")
      ? "python"
      : currentMode.includes("java")
        ? "java"
        : currentMode.includes("c_cpp")
          ? editor.value.getValue().includes("#include <stdio.h>")
            ? "c"
            : "cpp"
          : "python";

    userCode.value[currentLanguage] = editor.value.getValue();
    editor.value.session.setMode(
      "ace/mode/" + (newLang === "cpp" || newLang === "c" ? "c_cpp" : newLang)
    );
    editor.value.setValue(userCode.value[newLang] || defaultCode[newLang]);
  });
  document.getElementById("language").dispatchEvent(new Event("change"));
});

const runCode = async () => {
  const code = editor.value.getValue();
  const language = selectedLanguage.value;
  userCode.value[language] = code;

  axios({
    method: "POST",
    url: "http://192.168.30.188:8080/api/compiler/run",
    headers: {
      "Content-Type": "application/json",
    },
    data: {
      language: language,
      code: code,
      ipt: inputText.value,
      inputOutput: {
        1000: 13,
        123: 133,
      },
      time: 1,
      memory: 100,
    },
  })
    .then((res) => {
      if (inputText.value === "") {
        console.log(res.data);
        let outPutData = "";
        Object.keys(res.data).forEach((key) => {
          outPutData += key + "\n";
          if (res.data[key] === "correct") {
            outPutData += "정답!\n";
          } else {
            outPutData += "오답.\n";
          }
          outPutData += "\n";
        });
        outputText.value = outPutData;
      } else {
        console.log(res.data);
        outputText.value = res.data.output;
      }
    })
    .catch((err) => {
      console.log(err);
    });
};

const resetCode = () => {
  const language = selectedLanguage.value;
  editor.value.setValue(defaultCode[language]);
};

const clearInput = () => {
  inputText.value = "";
};
</script>

<template>
  <div>
    <div class="editor-con box-w">
      <div id="editor-container">
        <select id="language" class="box language bold-text" v-model="selectedLanguage">
          <option value="python">Python</option>
          <option value="java">Java</option>
          <option value="cpp">C++</option>
          <option value="c">C</option>
        </select>

        <div id="editor"></div>
        <button class="run-btn bold-text" @click="runCode">실행</button>
        <button class="submit-btn bold-text">제출</button>
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

          <textarea id="inputText" class="nomal-text" v-model="inputText" rows="10" cols="30"></textarea>
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
.clear-btn {
  border-width: 5px;
  border-radius: 10px;
  border-color: #3b72ff;
  background-color: #9fbaff;
}

.run-btn,
.submit-btn {
  width: 80px;
  height: 40px;
  margin-top: 10px;
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
  padding: 5px;
}

#outputText {
  border: 2px solid black;
  width: 90%;
  overflow: auto;
  /* Enable scrolling */
  white-space: pre-wrap;
  /* Preserve whitespace and wrap long lines */
}
</style>
