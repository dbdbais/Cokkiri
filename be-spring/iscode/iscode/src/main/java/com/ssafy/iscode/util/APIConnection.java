package com.ssafy.iscode.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.submit.model.dto.CompileRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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

    public Map<Integer,String> compileCode(CompileRequestDTO compileRequestDTO,boolean sbmit){
        String url = "https://i11e108.p.ssafy.io/compiler/";
        if(sbmit){
            url += "submit";
        }
        else{
            url += "run";
        }
        System.out.println(compileRequestDTO);
        WebClient webClient = WebClient.create(url);

        Map<Integer, String> response = webClient.post()
                .bodyValue(compileRequestDTO)  // Request Body에 compileRequest 객체를 포함
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<Integer, String>>() {})  // 응답을 Map<Integer, String> 형태로 받기
                .block();  // 동기적으로 요청을 보내고 응답을 기다림

        return response;
    }


    //for Test
    public static void main(String[] args) throws IOException {

        String language = "python";
        String code = "print(input()+123)";
        String ipt = null;
        Map<Integer, String> input = new HashMap<>();
        long time = 1;
        long memory = 128;

        String[] st = new String[11];
        st[1] ="a";
        st[2] ="b";
        st[3] ="c";
        st[4] ="d";
        st[5] ="e";
        st[6] ="f";
        st[7] ="g";
        st[8] ="h";
        st[9] ="i";
        st[10] ="j";

        for(int i=1;i<=10;i++){
            input.put(i,st[i]);
        }

        System.out.println("run : 1 or submit : 2");
        int pos = Integer.parseInt(in.readLine());

        String url = "https://i11e108.p.ssafy.io/compiler/";

        if(pos == 1){
            url += "run";
        }
        else{
            url += "submit";
        }
        CompileRequestDTO compileRequest = new CompileRequestDTO(
                language,
                code,
                ipt,
                input,
                time,
                memory
        );
        System.out.println(compileRequest);
        // WebClient 생성 및 요청 보내기
        WebClient webClient = WebClient.create(url);

        Map<Integer, String> response = webClient.post()
                .bodyValue(compileRequest)  // Request Body에 compileRequest 객체를 포함
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<Integer, String>>() {})  // 응답을 Map<Integer, String> 형태로 받기
                .block();  // 동기적으로 요청을 보내고 응답을 기다림

        System.out.println("Response from server: " + response);

    }
}