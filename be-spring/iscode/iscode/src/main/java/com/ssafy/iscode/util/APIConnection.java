package com.ssafy.iscode.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.iscode.problem.model.dto.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class APIConnection {
    //Call Exterior API IN Server
    @Autowired
    public WebCrawler webCrawler;
    //WebCrawler Dependency Injection

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public Problem run(Long id) throws IOException {

        String baseurl = "https://solved.ac/api/v3";
        //SOLVED API url
        //String url = "/problem/class";
        String url = "/problem/show";
        List<String> type = new ArrayList<>();
        WebClient webClient = WebClient.create(baseurl);
        //create WEB Client

        System.out.print("검색한 문제 번호를 입력 : ");

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

        String info = webCrawler.getProblemInfo(id);
        Problem prob = new Problem(id,type,title,level,info, avgTries);
        System.out.println(prob);
        return prob;
        //return info of Problem
    }


    //for Test
    public static void main(String[] args) throws IOException {

        WebCrawler webCrawler = new WebCrawler();

        String baseurl = "https://solved.ac/api/v3";
        //SOLVED API
        //String url = "/problem/class";
        String url = "/problem/show";
        Long no;
        List<String> type = new ArrayList<>();
        WebClient webClient = WebClient.create(baseurl);

        System.out.print("검색한 문제 번호를 입력 : ");
        no = Long.parseLong(in.readLine());

        Mono <String> mono = webClient.get()
                .uri(url+"?problemId="+no)
                .retrieve()
                .bodyToMono(String.class);

        // get result
        String response = mono.block();
        //System.out.println(response);


        // parse JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        // get key & value
        JsonNode titleNode = rootNode.path("titleKo");
        String title = titleNode.asText();
        //System.out.println("제목: "+title);
        JsonNode avgNode = rootNode.path("averageTries");
        double avgTries = avgNode.asDouble();
        //System.out.println("평균 시도횟수: "+avgTries);
        JsonNode levelNode = rootNode.path("level");
        int level = levelNode.asInt();
        //System.out.println("레벨: "+ level);
        JsonNode tagsNode = rootNode.path("tags");
        //System.out.print("유형 :");
        for (JsonNode tag : tagsNode) {
            JsonNode displayNamesNode = tag.path("displayNames");
            for (JsonNode displayName : displayNamesNode) {
                if (displayName.path("language").asText().equals("ko")) {
                    String name = displayName.path("name").asText();
                    type.add(name);
                    //System.out.print(name+" ");
                }
            }
        }

//            public Problem(Long no, List<String> algoType, String title, int level, String info, double tries) {
//            this.no = no;
//            this.algoType = algoType;
//            this.title = title;
//            this.level = level;
//            this.info = info;
//            this.tries = tries;


        String info = webCrawler.getProblemInfo(no);
        Problem prob = new Problem(no,type,title,level,info, avgTries);
        System.out.println(prob);

    }
}