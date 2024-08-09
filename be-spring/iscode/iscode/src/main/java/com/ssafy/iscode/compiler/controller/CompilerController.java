package com.ssafy.iscode.compiler.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.*;

@RestController
@RequestMapping("/api/compiler")
public class CompilerController {

    static class CompileRequest {
        public String language;
        public String code;
        public Map<String, Object> inputOutput;
        public long time; // 실행 시간 제한 (초 단위)
        public long memory; // 메모리 사용 제한 (메가바이트 단위)
    }

    @PostMapping("/run")
    public ResponseEntity<Map<String, String>> compileCode(@RequestBody CompileRequest request) {
        Map<String, String> result = new HashMap<>();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Map<String, String>> future = executor.submit(() -> {
            Map<String, String> output = new HashMap<>();
            for (Map.Entry<String, Object> entry : request.inputOutput.entrySet()) {
                String input = entry.getKey();
                String expectedOutput = entry.getValue().toString();

                try {
                    String actualOutput = compileAndRunCode(request.language, request.code, input, request.time, request.memory).trim();

                    if (expectedOutput.equals(actualOutput)) {
                        output.put(actualOutput, "correct");
                    } else {
                        output.put(actualOutput, "false");
                    }
                } catch (Exception e) {
                    output.put("Error: " + e.getMessage(), "false");
                }
            }
            return output;
        });

        Map<String, String> output;
        try {
            output = future.get(request.time * 1000 + 5000, TimeUnit.MILLISECONDS); // 시간 제한에 버퍼 추가
        } catch (TimeoutException e) {
            output = new HashMap<>();
            output.put("error", "Execution time exceeded the limit");
        } catch (InterruptedException | ExecutionException e) {
            output = new HashMap<>();
            output.put("error", "An error occurred during execution");
        } finally {
            executor.shutdown();
        }

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitCode(@RequestBody CompileRequest request) {
        Map<String, String> result = new HashMap<>();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Map<String, String>> future = executor.submit(() -> {
            Map<String, String> output = new HashMap<>();
            for (Map.Entry<String, Object> entry : request.inputOutput.entrySet()) {
                String input = entry.getKey();
                String expectedOutput = entry.getValue().toString();

                try {
                    String actualOutput = compileAndRunCode(request.language, request.code, input, request.time, request.memory).trim();

                    if (expectedOutput.equals(actualOutput)) {
                        output.put(actualOutput, "correct");
                    } else {
                        output.put(actualOutput, "false");
                    }
                } catch (Exception e) {
                    output.put("Error: " + e.getMessage(), "false");
                }
            }
            return output;
        });

        Map<String, String> output;
        try {
            output = future.get(request.time * 1000 + 5000, TimeUnit.MILLISECONDS); // 시간 제한에 버퍼 추가
        } catch (TimeoutException e) {
            output = new HashMap<>();
            output.put("error", "Execution time exceeded the limit");
        } catch (InterruptedException | ExecutionException e) {
            output = new HashMap<>();
            output.put("error", "An error occurred during execution");
        } finally {
            executor.shutdown();
        }

        // 결과 처리
        long correctCount = output.values().stream().filter("correct"::equals).count();
        long totalCases = output.size();

        if (output.containsValue("false")) {
            double ratio = (double) correctCount / totalCases;
            return new ResponseEntity<>(String.format("%.2f", ratio), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("correct", HttpStatus.OK);
        }
    }

    private String compileAndRunCode(String language, String code, String input, long time, long memory) throws Exception {
        String result = "";
        File sourceFile = null;

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(System.getProperty("java.io.tmpdir")));

            switch (language) {
                case "python":
                    sourceFile = new File(processBuilder.directory(), "temp.py");
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), StandardCharsets.UTF_8))) {
                        writer.write(code);
                    }
                    processBuilder.command("python", sourceFile.getAbsolutePath());
                    processBuilder.environment().put("PYTHONIOENCODING", "UTF-8");
                    break;

                case "java":
                    sourceFile = new File(processBuilder.directory(), "Temp.java");
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), StandardCharsets.UTF_8))) {
                        writer.write(code);
                    }
                    processBuilder.command("javac", "-encoding", "UTF-8", sourceFile.getName());
                    runProcess(processBuilder);
                    processBuilder.command("java", "-Dfile.encoding=UTF-8", "-cp", processBuilder.directory().getAbsolutePath(), "Temp");
                    break;

                case "cpp":
                    sourceFile = new File(processBuilder.directory(), "temp.cpp");
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), StandardCharsets.UTF_8))) {
                        writer.write(code);
                    }
                    processBuilder.command("g++", "-o", "temp", sourceFile.getName());
                    runProcess(processBuilder);
                    processBuilder.command(new File(processBuilder.directory(), "temp").getAbsolutePath());
                    break;

                default:
                    return "Unsupported language: " + language;
            }

            if (sourceFile != null) {
                Process process = processBuilder.start();

                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8))) {
                    writer.write(input);
                }

                // 메모리 모니터링 스레드 시작
                MemoryMonitor memoryMonitor = new MemoryMonitor(memory);
                memoryMonitor.start();

                // 프로세스 실행 및 결과 읽기
                result = readProcessOutput(process);
                boolean finishedInTime = process.waitFor(time, TimeUnit.SECONDS); // 실행 시간 제한

                // 메모리 모니터링 종료
                memoryMonitor.stopMonitoring();

                if (!finishedInTime) {
                    System.out.println("123");
                    process.destroy(); // 프로세스 종료
                    result = "Error: Time limit exceeded";
                }

                sourceFile.delete();
                new File(processBuilder.directory(), "temp").delete();
                new File(processBuilder.directory(), "Temp.class").delete();
            }
        } catch (OutOfMemoryError e) {
            result = "Error: Memory limit exceeded";
        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }
        return result;
    }


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
                throw new IOException(errorOutput.toString());
            }
        }
    }

    private String readProcessOutput(Process process) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        }
        return result.toString();
    }

    // 메모리 모니터링 클래스
    private static class MemoryMonitor extends Thread {
        private final long memoryLimitMB;
        private volatile boolean running = true;

        MemoryMonitor(long memoryLimitMB) {
            this.memoryLimitMB = memoryLimitMB;
        }

        @Override
        public void run() {
            while (running) {
                long usedMemoryMB = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
                if (usedMemoryMB > memoryLimitMB) {
                    throw new OutOfMemoryError("Memory limit exceeded");
                }
                try {
                    Thread.sleep(100); // 메모리 사용량을 주기적으로 확인
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        void stopMonitoring() {
            running = false;
        }
    }
}