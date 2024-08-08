package com.ssafy.iscode.problem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.problem.service.ProblemService;
import com.ssafy.iscode.util.APIConnection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/problemapi")
public class ProblemRestController {

    private final ProblemService ps;
    private APIConnection apiConnection;

    @Autowired
    public ProblemRestController(ProblemService ps, APIConnection apiConnection) {
        this.ps = ps;
        this.apiConnection = apiConnection;
    }

    @GetMapping("/all")
    public List<Problem> getProblems(){
        //if there is no id fetch all problem
        return ps.getAllProblem();
    }

    @GetMapping("{pid}")
    public Problem getProblem(@PathVariable Long pid){
        return ps.getProblem(pid);
    }
    //insert the problem

    @PostMapping("/class")
    public int insertClass() throws IOException {

        long [] class1 = {1000,1001,1008,1152,1330,2438,2439,2475,2557,2562,2577,2675,
        2739,2741,2753,2884,2920,3052,8958,9498,10171,10172,10250,10809,10818,
        10869,10871,10950,10951,10952,10998,11654,11720,25083,27866,31403};

        long[] class2 = {1018,1181,1259,1436,1546,1654,1676,1874,1920,1929,1966,
        1978,2108,2164,2231,2292,2609,2751,2775,2798,2839,2869,4153,4949,7568,
        9012,10773,10814,10816,10828,10845,10989,11050,11650,11651,11866,15829,
        18110,30802};

        long[] class3 = {1003,1012,1074,1260,1389,1463,1541,1620,1697,1764,1927,1931,
        2178,2579,2606,2630,2667,2805,5430,5525,6064,7569,7576,7662,9019,9095,
        9375,9461,10026,11047,11279,11286,11399,11403,11659,11723,11724,
        11726,11727,14500,14940,16928,17219,17626,18111,18870,21736,30804};

        long[] class4 = {1043,1149,1167,1238,1504,1629,1753,1865,1916,1918,1932,
        1967,1987,1991,2096,2206,2638,5639,9251,9465,9663,9935,10830,11053,
        11054,11404,11444,11660,11725,11779,12851,12865,13172,13549,14502,14938,
        15650,15652,15654,15663,15666,15686,16236,16953,17070,17144};

            for(long elem : class1){
                insert(elem);
            }
            for(long elem : class2){
                insert(elem);
            }
            for(long elem : class3){
                insert(elem);
            }
            for(long elem : class4){
                insert(elem);
            }




        return 1;
    }


    @PostMapping("/insert")
    public String insert(@RequestParam Long id) throws IOException {
        return ps.insertProblem(id).toString();
    }

    @PostMapping("/hidden/{pid}")
    public int insertProblem(@PathVariable Long pid, @RequestBody String data)  {
        return ps.getHiddenTestCase(pid,data);
    }


    @GetMapping("/hint/{pid}")
    public String getHint(@PathVariable Long pid) throws IOException {

        if(ps.getProblem(pid) == null){
          insert(pid);
          //add the problem
        }
        String plainText= ps.getProblem(pid).getText();
        // get only TEXT
        System.out.println(plainText);
        return apiConnection.useOpenAI(plainText,"너는 알고리즘을 가르쳐주는 선생님이야, 정답을 알려주지 말고 힌트만 제공해줘");
    }


}
