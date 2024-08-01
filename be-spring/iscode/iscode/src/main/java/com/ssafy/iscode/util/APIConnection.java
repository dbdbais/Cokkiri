package com.ssafy.iscode.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.iscode.problem.model.dto.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class APIConnection {
    //Call Exterior API IN Server
    @Autowired
    private WebCrawler webCrawler;
    //WebCrawler Dependency Injection

    //SECRET KEY
    @Value("${openai.api.key}")
    private String openaiApiKey;


    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    //get response of CHAT-GPT AI
    //query is for question & setting is for setting the role for AI
    public String useOpenAI(String query,String setting) throws JsonProcessingException {
        String url = "https://api.openai.com/v1/chat/completions";
        //openAI URL

        Map<String,Object> request = new HashMap<>();
        request.put("model","gpt-3.5-turbo");
        request.put("messages",new Object[]{
                new HashMap<String,String>(){{
                    put("role","system");
                    put("content",setting);
                }},
                new HashMap<String,String>(){{
                    put("role","user");
                    put("content",query);
                }}
        });

        WebClient webClient = WebClient.create(url);
        Mono<String> mono =   webClient
                .post()
                .header("Authorization", "Bearer " + openaiApiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);

        String response = mono.block();
        //System.out.println(response);

        // JSON PARSING
        ObjectMapper objectMapper = new ObjectMapper();

        // Root Node
        JsonNode rootNode = objectMapper.readTree(response);

        // Extract content from choices array
        JsonNode contentNode = rootNode.path("choices").get(0).path("message").path("content");

        return contentNode.asText();

    }
    public Problem getProblem(Long id) throws IOException {
        String baseurl = "https://solved.ac/api/v3";
        //SOLVED API url
        //String url = "/problem/class";
        String url = "/problem/show";
        List<String> type = new ArrayList<>();
        WebClient webClient = WebClient.create(baseurl);
        //create WEB Client

        //System.out.print("검색한 문제 번호를 입력 : ");

        Mono<String> mono = webClient.get()
                .uri(url+"?problemId="+id)
                .retrieve()
                .bodyToMono(String.class);
        //get URL from WebClient from

        // get response async
        String response = mono.block();
        //System.out.println(response);


        // JSON PARSING
        ObjectMapper objectMapper = new ObjectMapper();

        // read ROOT NODE
        JsonNode rootNode = objectMapper.readTree(response);

        // get key & value
        JsonNode titleNode = rootNode.path("titleKo");
        String title = titleNode.asText();
        //System.out.println("제목: "+title);
        JsonNode avgNode = rootNode.path("averageTries");
        // get average tries
        double avgTries = avgNode.asDouble();
        //System.out.println("평균 시도횟수: "+avgTries);
        JsonNode levelNode = rootNode.path("level");
        //get level of node
        int level = levelNode.asInt();
        //System.out.println("레벨: "+ level);
        JsonNode tagsNode = rootNode.path("tags");
        //System.out.print("유형 :");
        for (JsonNode tag : tagsNode) {
            JsonNode displayNamesNode = tag.path("displayNames");
            for (JsonNode displayName : displayNamesNode) {
                if (displayName.path("language").asText().equals("ko")) {
                    //get name of
                    String name = displayName.path("name").asText();
                    type.add(name);
                    //System.out.print(name+" ");
                }
            }
        }

        Problem prob = webCrawler.crawlProblem(id);
        prob.setAlgoType(type);
        prob.setTitle(title);
        prob.setLevel(level);

        System.out.println(prob);
        return prob;
        //return info of Problem
    }


    //for Test
    public static void main(String[] args) throws IOException {
        System.out.print("질문 입력하세요:");
        String query = in.readLine();

        String url = "https://api.openai.com/v1/chat/completions";
        //openAI key

        Map<String,Object> request = new HashMap<>();
        request.put("model","gpt-3.5-turbo");
        request.put("messages",new Object[]{
                new HashMap<String,String>(){{
                    put("role","system");
                    put("content","너는 JAVA언어를 PYTHON으로 바꿔주기 위한 비서야,");
                }},
                new HashMap<String,String>(){{
                    put("role","user");
                    put("content",query);
                }}
        });

        WebClient webClient = WebClient.create(url);
        Mono<String> mono =   webClient
                .post()
                .header("Authorization", "Bearer " + "YOUR API KEY")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);

        String response = mono.block();
        //System.out.println(response);

        // JSON PARSING
        ObjectMapper objectMapper = new ObjectMapper();

        // Root Node
        JsonNode rootNode = objectMapper.readTree(response);

        // Extract content from choices array
        JsonNode contentNode = rootNode.path("choices").get(0).path("message").path("content");
        String content = contentNode.asText();

        System.out.println(content);


    }
}