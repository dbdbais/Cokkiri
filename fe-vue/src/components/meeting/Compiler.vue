<template>
  <div>
    <div class="editor-con box-w">
      <div id="editor-container">
        <h3>Code Editor</h3>
        <select id="language" v-model="selectedLanguage">
          <option value="python">Python</option>
          <option value="java">Java</option>
          <option value="cpp">C++</option>
          <option value="c">C</option>
        </select>
        <button @click="runCode">실행</button>
        <button @click="resetCode">코드 초기화</button>
        <button @click="fontIncrease">폰트 크게</button>
        <button @click="fontReduce">폰트 작게</button>
        <button>제출</button>
        <div id="editor"></div>
      </div>
    </div>
    <div class="input-con box-w">
      <div id="input-output-area">
        <div id="input">
          <h3>Input <button @click="clearInput">Clear Input</button></h3>

          <textarea
            id="inputText"
            v-model="inputText"
            rows="10"
            cols="30"
          ></textarea>
        </div>
        <div id="output">
          <h3>Output</h3>
          <pre id="outputText">{{ outputText }}</pre>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import axios from "axios";
import ace from "ace-builds";
import "ace-builds/src-noconflict/mode-python";
import "ace-builds/src-noconflict/mode-java";
import "ace-builds/src-noconflict/mode-c_cpp";
import "ace-builds/src-noconflict/theme-monokai";

const editor = ref(null);
const selectedLanguage = ref("python");
const inputText = ref("");
const outputText = ref("");
const editorFontSize = ref(16);

const defaultCode = {
  python: 'print("Hello, World!")',
  java: 'public class Temp {\n    public static void main(String[] args) {\n        System.out.println("Hello, World!");\n    }\n}',
  c: '#include <stdio.h>\nint main() {\n    printf("Hello, World!");\n    return 0;\n}',
  cpp: '#include <iostream>\nint main() {\n    std::cout << "Hello, World!" << std::endl;\n    return 0;\n}',
};

const userCode = ref({ ...defaultCode });

const initializeEditor = (val) => {
  editor.value = ace.edit("editor");
  editor.value.setTheme("ace/theme/monokai");
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
  }, 400);
  setTimeout(() => {
    clearInterval(timerId);
  }, 1000);
};

const fontIncrease = () => {
  const saveVal = editor.value.getValue();

  console.log("폰트 크게!");
  let timerId = setInterval(() => {
    console.log("커지는 중!");
    if (editorFontSize.value < 40) {
      editorFontSize.value += 1;
      initializeEditor(saveVal);
    }
    // console.log(editorFontSize.value);
  }, 200);
  setTimeout(() => {
    clearInterval(timerId);
  }, 1000);
};

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
    url: "http://localhost:8080/compile",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
    },
    data: {
      language: language,
      code: code,
      input: inputText.value,
    },
  })
    .then((res) => {
      outputText.value = res.data;
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

<style scoped>
.input-con {
  width: 100%;
  height: 21vh;
  margin-top: 2vh;
}
.editor-con {
  width: 100%;
  height: 40vh;
  margin-top: 2vh;
}
#input-output-area {
  width: 100%;
  margin: 10px;
  display: flex;
  justify-content: space-between;
}
#input,
#output {
  width: 48%;
  margin: 5px;
}
#editor-container {
  width: 100%;
}
textarea {
  width: 100%;
  flex-grow: 1;
  resize: none;
}
#editor {
  height: 34.8vh;
  width: 100%;
  border-radius: 5px;
}

#inputText {
  height: 15vh;
}
#outputText {
  border: 1px solid black;
  height: 15vh; /* Adjust this value as needed */
  width: 90%;
  overflow: auto; /* Enable scrolling */
  white-space: pre-wrap; /* Preserve whitespace and wrap long lines */
}
</style>
