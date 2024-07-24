package com.ssafy.iscode.compiler.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * 코드 컴파일러 컨트롤러 클래스
 */
@RestController
public class CompilerController {

    /**
     * 인덱스 페이지를 표시하는 메서드
     *
     * @return 인덱스 페이지에 대한 ModelAndView 객체
     */
    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    /**
     * 사용자로부터 받은 코드를 컴파일하고 실행하는 메서드
     *
     * @param language 코드를 작성한 프로그래밍 언어
     * @param code 컴파일할 소스 코드
     * @param input 실행 시 사용할 입력 값
     * @return 컴파일 및 실행 결과 또는 오류 메시지
     */
    @PostMapping("/compile")
    public String compileCode(@RequestParam String language, @RequestParam String code, @RequestParam String input) {
        String result = "";
        File sourceFile = null;  // 소스 코드 파일을 저장할 변수

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(System.getProperty("java.io.tmpdir"))); // 임시 디렉토리를 작업 디렉토리로 설정

            // 언어별로 다른 컴파일 및 실행 명령어 설정
            switch (language) {
                case "python":
                    sourceFile = new File(processBuilder.directory(), "temp.py");
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), StandardCharsets.UTF_8))) {
                        writer.write(code);
                    }
                    processBuilder.command("python", sourceFile.getAbsolutePath());
                    processBuilder.environment().put("PYTHONIOENCODING", "UTF-8"); // UTF-8 인코딩 설정
                    break;

                case "java":
                    sourceFile = new File(processBuilder.directory(), "Temp.java");
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), StandardCharsets.UTF_8))) {
                        writer.write(code);
                    }
                    processBuilder.command("javac", "-encoding", "UTF-8", sourceFile.getName()); // 자바 컴파일 명령어
                    runProcess(processBuilder); // 컴파일 실행
                    processBuilder.command("java", "-Dfile.encoding=UTF-8", "-cp", processBuilder.directory().getAbsolutePath(), "Temp"); // 자바 실행 명령어
                    break;

                case "c":
                    sourceFile = new File(processBuilder.directory(), "temp.c");
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), StandardCharsets.UTF_8))) {
                        writer.write(code);
                    }
                    processBuilder.command("gcc", "-o", "temp", sourceFile.getName()); // C 컴파일 명령어
                    runProcess(processBuilder); // 컴파일 실행
                    processBuilder.command(new File(processBuilder.directory(), "temp").getAbsolutePath()); // C 실행 파일 실행 명령어
                    break;

                case "cpp":
                    sourceFile = new File(processBuilder.directory(), "temp.cpp");
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), StandardCharsets.UTF_8))) {
                        writer.write(code);
                    }
                    processBuilder.command("g++", "-o", "temp", sourceFile.getName()); // C++ 컴파일 명령어
                    runProcess(processBuilder); // 컴파일 실행
                    processBuilder.command(new File(processBuilder.directory(), "temp").getAbsolutePath()); // C++ 실행 파일 실행 명령어
                    break;

                default:
                    result = "Unsupported language: " + language;
            }

            if (sourceFile != null) {
                Process process = processBuilder.start();
                
                // 입력값을 프로세스에 전달
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8))) {
                    writer.write(input);
                }
                
                result = readProcessOutput(process); // 프로세스 출력값 읽기
                process.waitFor(); // 프로세스 종료 대기
                sourceFile.delete(); // 소스 파일 삭제
                new File(processBuilder.directory(), "temp").delete(); // 생성된 실행 파일 삭제
                new File(processBuilder.directory(), "Temp.class").delete(); // 자바 클래스 파일 삭제
            }
        } catch (Exception e) {
            result = "Error: " + e.getMessage(); // 예외 발생 시 오류 메시지 설정
        }
        return result;
    }

    /**
     * 주어진 프로세스를 실행하고, 에러가 발생하면 예외를 던지는 메서드
     *
     * @param processBuilder 실행할 프로세스가 설정된 ProcessBuilder 객체
     * @throws IOException I/O 오류가 발생한 경우
     * @throws InterruptedException 프로세스 실행이 중단된 경우
     */
    private void runProcess(ProcessBuilder processBuilder) throws IOException, InterruptedException {
        Process process = processBuilder.start();
        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))) {
            StringBuilder errorOutput = new StringBuilder();
            String line;
            while ((line = errorReader.readLine()) != null) {
                errorOutput.append("Error: ").append(line).append("\n");
            }
            process.waitFor();
            if (errorOutput.length() > 0) {
                throw new IOException(errorOutput.toString()); // 오류 메시지가 존재하면 예외 던짐
            }
        }
    }

    /**
     * 주어진 프로세스의 출력을 읽어오는 메서드
     *
     * @param process 출력값을 읽을 프로세스
     * @return 프로세스의 출력값 또는 오류 메시지
     * @throws IOException I/O 오류가 발생한 경우
     */
    private String readProcessOutput(Process process) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        }
        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = errorReader.readLine()) != null) {
                result.append("Error: ").append(line).append("\n");
            }
        }
        return result.toString(); // 프로세스 출력값 반환
    }
}
