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
 * Code compiler controller class
 */
@RestController
public class CompilerController {

    /**
     * Method to display the index page
     *
     * @return ModelAndView object for the index page
     */
    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    /**
     * Method to compile and execute the code received from the user
     *
     * @param language Programming language of the code
     * @param code Source code to compile
     * @param input Input value to use during execution
     * @return Compilation and execution result or error message
     */
    @PostMapping("/compile")
    public String compileCode(@RequestParam String language, @RequestParam String code, @RequestParam String input) {
        String result = "";
        File sourceFile = null;  // Variable to store the source code file

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(System.getProperty("java.io.tmpdir"))); // Set the working directory to the temporary directory

            // Set different compile and run commands for each language
            switch (language) {
                case "python":
                    sourceFile = new File(processBuilder.directory(), "temp.py");
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), StandardCharsets.UTF_8))) {
                        writer.write(code);
                    }
                    processBuilder.command("python", sourceFile.getAbsolutePath());
                    processBuilder.environment().put("PYTHONIOENCODING", "UTF-8"); // Set UTF-8 encoding
                    break;

                case "java":
                    sourceFile = new File(processBuilder.directory(), "Temp.java");
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), StandardCharsets.UTF_8))) {
                        writer.write(code);
                    }
                    processBuilder.command("javac", "-encoding", "UTF-8", sourceFile.getName()); // Java compile command
                    runProcess(processBuilder); // Run the compilation
                    processBuilder.command("java", "-Dfile.encoding=UTF-8", "-cp", processBuilder.directory().getAbsolutePath(), "Temp"); // Java run command
                    break;

                case "c":
                    sourceFile = new File(processBuilder.directory(), "temp.c");
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), StandardCharsets.UTF_8))) {
                        writer.write(code);
                    }
                    processBuilder.command("gcc", "-o", "temp", sourceFile.getName()); // C compile command
                    runProcess(processBuilder); // Run the compilation
                    processBuilder.command(new File(processBuilder.directory(), "temp").getAbsolutePath()); // C executable run command
                    break;

                case "cpp":
                    sourceFile = new File(processBuilder.directory(), "temp.cpp");
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), StandardCharsets.UTF_8))) {
                        writer.write(code);
                    }
                    processBuilder.command("g++", "-o", "temp", sourceFile.getName()); // C++ compile command
                    runProcess(processBuilder); // Run the compilation
                    processBuilder.command(new File(processBuilder.directory(), "temp").getAbsolutePath()); // C++ executable run command
                    break;

                default:
                    result = "Unsupported language: " + language;
            }

            if (sourceFile != null) {
                Process process = processBuilder.start();
                
                // Provide input to the process
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8))) {
                    writer.write(input);
                }
                
                result = readProcessOutput(process); // Read the process output
                System.out.println(result);
                process.waitFor(); // Wait for the process to finish
                sourceFile.delete(); // Delete the source file
                new File(processBuilder.directory(), "temp").delete(); // Delete the generated executable file
                new File(processBuilder.directory(), "Temp.class").delete(); // Delete the Java class file
            }
        } catch (Exception e) {
            result = "Error: " + e.getMessage(); // Set error message if an exception occurs
        }
        return result;
    }

    /**
     * Method to run the given process and throw an exception if an error occurs
     *
     * @param processBuilder ProcessBuilder object with the process to execute
     * @throws IOException If an I/O error occurs
     * @throws InterruptedException If the process execution is interrupted
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
                throw new IOException(errorOutput.toString()); // Throw exception if error messages exist
            }
        }
    }

    /**
     * Method to read the output of the given process
     *
     * @param process Process to read the output from
     * @return Output of the process or error messages
     * @throws IOException If an I/O error occurs
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
        return result.toString(); // Return the process output
    }
}
