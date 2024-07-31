<script setup>
import { ref } from "vue";

const problem1 = ref(`<div class="col-md-12">
 <section id="description" class="problem-section">
  <div class="headline">
   <h2>문제</h2>
  </div>
  <div id="problem_description" class="problem-text">
   <p>한수는 지금 (x, y)에 있다. 직사각형은 각 변이 좌표축에 평행하고,&nbsp;왼쪽 아래 꼭짓점은 (0, 0), 오른쪽 위 꼭짓점은 (w, h)에 있다. 직사각형의 경계선까지 가는 거리의 최솟값을 구하는 프로그램을 작성하시오.</p>
  </div>
 </section>
</div>
<div class="col-md-12">
 <section id="input" class="problem-section">
  <div class="headline">
   <h2>입력</h2>
  </div>
  <div id="problem_input" class="problem-text">
   <p>첫째 줄에 x, y, w, h가 주어진다.</p>
  </div>
 </section>
</div>
<div class="col-md-12">
 <section id="output" class="problem-section">
  <div class="headline">
   <h2>출력</h2>
  </div>
  <div id="problem_output" class="problem-text">
   <p>첫째 줄에 문제의 정답을 출력한다.</p>
  </div>
 </section>
</div>
<div class="col-md-12">
 <section id="limit" class="problem-section">
  <div class="headline">
   <h2>제한</h2>
  </div>
  <div id="problem_limit" class="problem-text">
   <ul>
    <li>1 ≤ w, h ≤ 1,000</li>
    <li>1 ≤ x ≤ w-1</li>
    <li>1 ≤ y ≤ h-1</li>
    <li>x, y, w, h는 정수</li>
   </ul>
  </div>
 </section>
</div>
<div class="col-md-12">
 <div class="row">
  <div class="col-md-6">
   <section id="sampleinput1">
    <div class="headline">
     <h2>예제 입력 1 </h2>
    </div>
    <pre class="sampledata" id="sample-input-1">6 2 10 3 4 4 44 4 44 44 44  44 4  44 44444 4 4  44 44
</pre>
   </section>
  </div>
  <div class="col-md-6">
   <section id="sampleoutput1">
    <div class="headline">
     <h2>예제 출력 1 </h2>
    </div>
    <pre class="sampledata" id="sample-output-1">1
</pre>
   </section>
  </div>
 </div>
</div>
<div class="col-md-12">
 <div class="row">
  <div class="col-md-6">
   <section id="sampleinput2">
    <div class="headline">
     <h2>예제 입력 2 </h2>
    </div>
    <pre class="sampledata" id="sample-input-2">1 1 5 5
</pre>
   </section>
  </div>
  <div class="col-md-6">
   <section id="sampleoutput2">
    <div class="headline">
     <h2>예제 출력 2 </h2>
    </div>
    <pre class="sampledata" id="sample-output-2">1
</pre>
   </section>
  </div>
 </div>
</div>
<div class="col-md-12">
 <div class="row">
  <div class="col-md-6">
   <section id="sampleinput3">
    <div class="headline">
     <h2>예제 입력 3 </h2>
    </div>
    <pre class="sampledata" id="sample-input-3">653 375 1000 1000
</pre>
   </section>
  </div>
  <div class="col-md-6">
   <section id="sampleoutput3">
    <div class="headline">
     <h2>예제 출력 3 </h2>
    </div>
    <pre class="sampledata" id="sample-output-3">347
</pre>
   </section>
  </div>
 </div>
</div>
<div class="col-md-12">
 <div class="row">
  <div class="col-md-6">
   <section id="sampleinput4">
    <div class="headline">
     <h2>예제 입력 4 </h2>
    </div>
    <pre class="sampledata" id="sample-input-4">161 181 762 375
</pre>
   </section>
  </div>
  <div class="col-md-6">
   <section id="sampleoutput4">
    <div class="headline">
     <h2>예제 출력 4 </h2>
    </div>
    <pre class="sampledata" id="sample-output-4">161
</pre>
   </section>
  </div>
 </div>
</div>
`);
const problem2 = ref(`<div id="problem-body" class="">
 <div class="col-md-12">
  <section id="description" class="problem-section">
   <div class="headline">
    <h2>문제</h2>
   </div>
   <div id="problem_description" class="problem-text">
    <p>마법사 상어는&nbsp;<a href="/problem/20056">파이어볼</a>,&nbsp;<a href="/problem/20057">토네이도</a>, <a href="/problem/20058">파이어스톰</a>, <a href="/problem/21610">물복사버그</a>, <a href="/problem/21610">비바라기</a> 마법을 할 수 있다. 오늘 새로 배운 마법은 블리자드이고, 크기가 N×N인 격자에서 연습하려고 한다. N은 항상 홀수이고, (r, c)는 격자의 r행 c열을 의미한다. 격자의 가장 왼쪽 윗 칸은 (1, 1)이고, 가장 오른쪽 아랫 칸은 (N, N)이며 마법사 상어는 ((N+1)/2, (N+1)/2)에 있다.</p>
    <p>일부 칸과 칸 사이에는 벽이 세워져 있으며,&nbsp;다음은 N = 3, 5, 7인 경우의 예시이다. 실선은 벽이고, 점선은 벽이 아니다.&nbsp;칸에 적혀있는 수는 칸의 번호이다.</p>
    <table class="table table-bordered td-center td-middle">
     <tbody>
      <tr>
       <td><img alt="" src="https://upload.acmicpc.net/443a20c6-fbd7-4951-9b68-bf78b12b27fb/-/preview/" style="width: 204px; height: 203px;"></td>
       <td><img alt="" src="https://upload.acmicpc.net/7781df31-3999-4234-a032-32cb6fd439e9/-/preview/" style="width: 204px; height: 204px;"></td>
       <td><img alt="" src="https://upload.acmicpc.net/50b901d5-47ec-4504-bce1-122e8282284a/-/preview/" style="width: 204px; height: 204px;"></td>
      </tr>
      <tr>
       <td>N = 3</td>
       <td>N = 5</td>
       <td>N = 7</td>
      </tr>
     </tbody>
    </table>
    <p>가장 처음에 상어가 있는 칸을 제외한 나머지 칸에는 구슬이 하나 들어갈 수 있다. 구슬은&nbsp;1번 구슬, 2번 구슬, 3번 구슬이 있다. 같은 번호를 가진 구슬이 번호가 연속하는 칸에 있으면, 그 구슬을 연속하는 구슬이라고 한다. 다음은 N = 7인 경우 예시이다.&nbsp;</p>
    <p style="text-align: center;"><img alt="" src="https://upload.acmicpc.net/2c31bd47-ddc9-40f2-9830-05bef469fb4a/-/preview/" style="width: 300px; height: 299px;"></p>
    <p>블리자드 마법을 시전하려면 방향 d<sub>i</sub>와&nbsp;거리 s<sub>i</sub>를 정해야 한다. 총 4가지 방향 ↑,&nbsp;↓,&nbsp;←,&nbsp;→가 있고, 정수 1, 2, 3, 4로 나타낸다. 상어는 d<sub>i</sub> 방향으로 거리가 s<sub>i</sub> 이하인 모든 칸에 얼음 파편을 던져 그 칸에 있는 구슬을 모두 파괴한다. 구슬이 파괴되면 그 칸은 구슬이 들어있지 않은 빈 칸이 된다. 얼음 파편은 벽의 위로 떨어지기 때문에, 벽은 파괴되지 않는다.</p>
    <p>다음 예시는 방향은 아래, 거리는 2인 경우이다.</p>
    <table class="table table-bordered td-center td-middle">
     <tbody>
      <tr>
       <td><img alt="" src="https://upload.acmicpc.net/ca05f21a-bd15-4fbd-80a5-899712db2beb/-/preview/" style="width: 300px; height: 299px;"></td>
       <td><img alt="" src="https://upload.acmicpc.net/4768a8c6-c935-430d-9ce3-7fde346b0830/-/preview/" style="width: 300px; height: 299px;"></td>
      </tr>
      <tr>
       <td>빨간색으로 표시된 칸에 얼음 파편이 떨어진다.</td>
       <td>구슬이 파괴된 후</td>
      </tr>
     </tbody>
    </table>
    <p>만약 어떤 칸 A의 번호보다 번호가 하나 작은 칸이 빈 칸이면, A에 있는 구슬은 그 빈 칸으로 이동한다. 이 이동은 더 이상 구슬이 이동하지 않을 때까지 반복된다. 따라서, 구슬이 파괴된 후에는 빈 칸이 생겨 구슬이 이동하고,&nbsp;구슬이 모두 이동한 결과는 다음과 같다.</p>
    <p style="text-align: center;"><img alt="" src="https://upload.acmicpc.net/28dcbbe3-7035-49ad-afed-642218adee39/-/preview/" style="width: 300px; height: 299px;"></p>
    <p>이제 구슬이 폭발하는 단계이다. 폭발하는 구슬은 4개 이상 연속하는 구슬이&nbsp;있을 때 발생한다. 다음은 왼쪽 그림은 위의 상태에서 폭발하는 구슬이 들어있는 칸을 파란색과 초록색으로 표시한 것이고, 오른쪽 그림은 구슬이 폭발한 후의 상태이다.</p>
    <table class="table table-bordered td-center td-middle">
     <tbody>
      <tr>
       <td><img alt="" src="https://upload.acmicpc.net/41c500e1-e82c-440c-afcc-f351af9ea1dc/-/preview/" style="width: 300px; height: 299px;"></td>
       <td><img alt="" src="https://upload.acmicpc.net/cf990414-2eb8-4f60-bd4c-bf8dfd35665d/-/preview/" style="width: 300px; height: 299px;"></td>
      </tr>
      <tr>
       <td>구슬이 폭발하기 전</td>
       <td>구슬이 폭발한 후</td>
      </tr>
     </tbody>
    </table>
    <p>구슬이 폭발해 빈 칸이 생겼으니 다시 구슬이 이동한다. 구슬이 이동한 후에는 다시 구슬이 폭발하는 단계이고, 이 과정은 더 이상 폭발하는 구슬이 없을때까지 반복된다. 구슬이 폭발한 후의 상태에서 구슬이 이동하면 다음과 같다.</p>
    <p style="text-align: center;"><img alt="" src="https://upload.acmicpc.net/6fe3b9cf-b6a2-4ad1-9014-3b99852996b5/-/preview/" style="width: 300px; height: 299px;"></p>
    <p>위의 상태는 4개 이상 연속하는 구슬이&nbsp;있기 때문에 구슬이 다시 폭발하게 된다.</p>
    <table class="table table-bordered td-center td-middle">
     <tbody>
      <tr>
       <td><img alt="" src="https://upload.acmicpc.net/5fb62a98-2cea-4c4d-9b6f-9c540b459290/-/preview/" style="width: 300px; height: 299px;"></td>
       <td><img alt="" src="https://upload.acmicpc.net/0f70b5e5-3111-4f16-a699-42638a17c540/-/preview/" style="width: 300px; height: 299px;"></td>
      </tr>
      <tr>
       <td>구슬이 폭발하기 전</td>
       <td>구슬이 폭발하고 이동한 후</td>
      </tr>
     </tbody>
    </table>
    <p>이제 더 이상 폭발한 구슬이 없기 때문에, 구슬이 변화하는 단계가 된다. 연속하는 구슬은&nbsp;하나의 그룹이라고 한다. 다음은 1번 구슬은 빨간색, 2번 구슬은 파란색, 3번 구슬은 보라색으로 표시한 그림이다.</p>
    <p style="text-align: center;"><img alt="" src="https://upload.acmicpc.net/0d0b2e68-960c-4bb7-a950-da389183ea88/-/preview/" style="width: 300px; height: 299px;"></p>
    <p>하나의 그룹은 두 개의 구슬 A와 B로 변한다. 구슬 A의 번호는 그룹에 들어있는 구슬의 개수이고, B는 그룹을 이루고 있는 구슬의 번호이다. 구슬은 다시 그룹의 순서대로 1번&nbsp;칸부터 차례대로 A, B의 순서로 칸에 들어간다. 다음 그림은 구슬이 변화한 후이고, 색은 구분하기 위해 위의 그림에 있는 그룹의 색을 그대로 사용했다. 만약, 구슬이 칸의 수보다 많아&nbsp;칸에 들어가지 못하는 경우 그러한 구슬은 사라진다.</p>
    <p style="text-align: center;">&nbsp;<img alt="" src="https://upload.acmicpc.net/c72823d6-95b2-424f-b9d8-84c423685b3d/-/preview/" style="width: 300px; height: 299px;"></p>
    <p>마법사 상어는 블리자드를 총 M번&nbsp;시전했다. 시전한 마법의 정보가 주어졌을 때, 1×(폭발한 1번 구슬의 개수) + 2×(폭발한 2번&nbsp;구슬의 개수) + 3×(폭발한 3번 구슬의 개수)를 구해보자.</p>
   </div>
  </section>
 </div>
 <div class="col-md-12">
  <section id="input" class="problem-section">
   <div class="headline">
    <h2>입력</h2>
   </div>
   <div id="problem_input" class="problem-text">
    <p>첫째 줄에 N, M이 주어진다. 둘째 줄부터 N개의 줄에는 격자에 들어있는 구슬의 정보가 주어진다. r번째 행의 c번째 정수는 (r, c)에 들어있는 구슬의 번호를 의미한다. 어떤 칸에 구슬이 없으면 0이 주어진다. 상어가 있는 칸도 항상 0이 주어진다.</p>
    <p>다음 M개의 줄에는 블리자드 마법의 방향 d<sub>i</sub>와 거리 s<sub>i</sub>가 한 줄에 하나씩 마법을 시전한 순서대로 주어진다.</p>
   </div>
  </section>
 </div>
 <div class="col-md-12">
  <section id="output" class="problem-section">
   <div class="headline">
    <h2>출력</h2>
   </div>
   <div id="problem_output" class="problem-text">
    <p>첫째 줄에 1×(폭발한 1번 구슬의 개수) + 2×(폭발한 2번&nbsp;구슬의 개수) + 3×(폭발한 3번 구슬의 개수)를 출력한다.</p>
   </div>
  </section>
 </div>
 <div class="col-md-12">
  <section id="limit" class="problem-section">
   <div class="headline">
    <h2>제한</h2>
   </div>
   <div id="problem_limit" class="problem-text">
    <ul>
     <li>3 ≤ N ≤ 49</li>
     <li>N은 홀수</li>
     <li>1 ≤ M ≤ 100</li>
     <li>1 ≤ d<sub>i</sub> ≤ 4</li>
     <li>1 ≤ s<sub>i</sub> ≤ (N-1)/2</li>
     <li>칸에 들어있는 구슬이 K개라면, 구슬이 들어있는 칸의 번호는 1번부터 K번까지이다.</li>
     <li>입력으로 주어진 격자에는&nbsp;4개 이상 연속하는 구슬이 없다.</li>
    </ul>
   </div>
  </section>
 </div>
 <div class="col-md-12">
  <div class="row">
   <div class="col-md-6">
    <section id="sampleinput1">
     <div class="headline">
      <h2>예제 입력 1 </h2>
     </div>
     <pre class="sampledata" id="sample-input-1">7 1
0 0 0 0 0 0 0
3 2 1 3 2 3 0
2 1 2 1 2 1 0
2 1 1 0 2 1 1
3 3 2 3 2 1 2
3 3 3 1 3 3 2
2 3 2 2 3 2 3
2 2
</pre>
    </section>
   </div>
   <div class="col-md-6">
    <section id="sampleoutput1">
     <div class="headline">
      <h2>예제 출력 1 </h2>
     </div>
     <pre class="sampledata" id="sample-output-1">28
</pre>
    </section>
   </div>
  </div>
 </div>
 <div class="col-md-12">
  <div class="row">
   <div class="col-md-6">
    <section id="sampleinput2">
     <div class="headline">
      <h2>예제 입력 2 </h2>
     </div>
     <pre class="sampledata" id="sample-input-2">7 4
0 0 0 2 3 2 3
1 2 3 1 2 3 1
2 3 1 2 3 1 2
1 2 3 0 2 3 1
2 3 1 2 3 1 2
3 1 2 3 1 2 3
1 2 3 1 2 3 1
1 3
2 2
3 1
4 3
</pre>
    </section>
   </div>
   <div class="col-md-6">
    <section id="sampleoutput2">
     <div class="headline">
      <h2>예제 출력 2 </h2>
     </div>
     <pre class="sampledata" id="sample-output-2">0
</pre>
    </section>
   </div>
   <div class="col-md-12">
    <section id="sample_explain_2" class="problem-section">
     <div id="problem_sample_explain_2" class="problem-text">
      <table class="table table-bordered td-center td-middle">
       <tbody>
        <tr>
         <td><img alt="" src="https://upload.acmicpc.net/06bbc8cd-ba47-4e76-9169-36165a168ba4/-/preview/" style="width: 300px; height: 299px;"></td>
         <td><img alt="" src="https://upload.acmicpc.net/9f8795c6-2013-439a-b539-6c785b59f7de/-/preview/" style="width: 300px; height: 299px;"></td>
        </tr>
        <tr>
         <td>첫 번째 블리자드 후</td>
         <td>두 번째 블리자드 후</td>
        </tr>
        <tr>
         <td><img alt="" src="https://upload.acmicpc.net/c8ed66f8-ae0f-40c6-9ff6-3638444086a2/-/preview/" style="width: 300px; height: 299px;"></td>
         <td><img alt="" src="https://upload.acmicpc.net/2416b631-2cde-4311-91e6-58ae5a278ea9/-/preview/" style="width: 300px; height: 299px;"></td>
        </tr>
        <tr>
         <td>세 번째 블리자드 후</td>
         <td>네 번째 블리자드 후</td>
        </tr>
       </tbody>
      </table>
     </div>
    </section>
   </div>
  </div>
 </div>
 <div class="col-md-12">
  <div class="row">
   <div class="col-md-6">
    <section id="sampleinput3">
     <div class="headline">
      <h2>예제 입력 3 </h2>
     </div>
     <pre class="sampledata" id="sample-input-3">7 4
1 1 1 2 2 2 3
1 2 2 1 2 2 3
1 3 3 2 3 1 2
1 2 2 0 3 2 2
3 1 2 2 3 2 2
3 1 2 1 1 2 1
3 1 2 2 2 1 1
1 3
2 2
3 1
4 3
</pre>
    </section>
   </div>
   <div class="col-md-6">
    <section id="sampleoutput3">
     <div class="headline">
      <h2>예제 출력 3 </h2>
     </div>
     <pre class="sampledata" id="sample-output-3">39
</pre>
    </section>
   </div>
   <div class="col-md-12">
    <section id="sample_explain_3" class="problem-section">
     <div id="problem_sample_explain_3" class="problem-text">
      <table class="table table-bordered td-center td-middle">
       <tbody>
        <tr>
         <td><img alt="" src="https://upload.acmicpc.net/153e7d87-0cad-439d-a0d0-b04a921f7c4f/-/preview/" style="width: 300px; height: 299px;"></td>
         <td><img alt="" src="https://upload.acmicpc.net/098a6ab4-b685-451e-8ae0-da2e56610aaa/-/preview/" style="width: 300px; height: 299px;"></td>
        </tr>
        <tr>
         <td>첫 번째 블리자드 후</td>
         <td>두 번째 블리자드 후</td>
        </tr>
        <tr>
         <td><img alt="" src="https://upload.acmicpc.net/af038778-572e-446a-9457-e2341756b4be/-/preview/" style="width: 300px; height: 299px;"></td>
         <td><img alt="" src="https://upload.acmicpc.net/896e814a-f780-4b75-b3c4-a234627f4c63/-/preview/" style="width: 300px; height: 299px;"></td>
        </tr>
        <tr>
         <td>세 번째 블리자드 후</td>
         <td>네 번째 블리자드 후</td>
        </tr>
       </tbody>
      </table>
     </div>
    </section>
   </div>
  </div>
 </div>
 <div class="col-md-12">
  <div class="row">
   <div class="col-md-6">
    <section id="sampleinput4">
     <div class="headline">
      <h2>예제 입력 4 </h2>
     </div>
     <pre class="sampledata" id="sample-input-4">7 7
1 1 1 2 2 2 3
1 2 2 1 2 2 3
1 3 3 2 3 1 2
1 2 2 0 3 2 2
3 1 2 2 3 2 2
3 1 2 1 1 2 1
3 1 2 2 2 1 1
1 3
2 2
3 1
4 3
1 3
1 1
1 3
</pre>
    </section>
   </div>
   <div class="col-md-6">
    <section id="sampleoutput4">
     <div class="headline">
      <h2>예제 출력 4 </h2>
     </div>
     <pre class="sampledata" id="sample-output-4">62
</pre>
    </section>
   </div>
  </div>
 </div>
 <div class="col-md-12">
  <section id="hint" style="display: none;" class="problem-section">
   <div class="headline">
    <h2>힌트</h2>
   </div>
   <div id="problem_hint" class="problem-text">
   </div>
  </section>
 </div>
</div>`);

const currentProblem = ref(2);
const showProblem1 = () => {
  currentProblem.value = 1;
};
const showProblem2 = () => {
  currentProblem.value = 2;
};

const problemActive = (problem) => {
  if (currentProblem.value === problem) {
    return "active";
  }
};
</script>

<template>
  <div class="md">
    <div class="problem-content box-w">
      <div class="flex-align">
        <div
          class="bold-text box-sb problem1 md"
          @click="showProblem1"
          :class="problemActive(1)"
        >
          문제 1
        </div>
        <div
          class="bold-text box-sb problem2 md"
          @click="showProblem2"
          :class="problemActive(2)"
        >
          문제 2
        </div>
      </div>
      <div class="nomal-text problem-box">
        <div v-if="currentProblem === 1" v-html="problem1"></div>
        <div v-else v-html="problem2"></div>
      </div>
    </div>
  </div>
</template>

<style>
.problem-content {
  width: 95%;
  height: 700px;
  position: relative;
  border-radius: 0px 10px 10px 10px;
  margin-top: 70px;
}
.problem-box {
  width: 100%;
  height: 680px;
  padding: 0px 35px;
  font-size: 20px;
  overflow-y: scroll;
}
.problem1,
.problem2 {
  width: 100px;
  height: 50px;

  border-radius: 10px 10px 0px 0px;
  font-size: 25px;
  position: absolute;
  top: -50px;
}
.problem1 {
  left: -5px;
}
.problem2 {
  left: 95px;
}
.nomal-text {
  font-size: 30px;
}
.active {
  background-color: blue;
}
.sampledata {
  background-color: #dbe7ff;
  border: 5px solid #3b72ff;
  overflow-y: scroll;
  border-radius: 10px;
  height: 300px;
  padding: 10px;
  white-space: pre-wrap;
  font-family: "yg-jalnan";
}
.problem-box li {
  margin-left: 20px;
}
.problem-box table {
  width: 100%;
  display: flex;
  justify-content: space-around;
  margin: 30px 0;
}
.problem-box table img {
  margin: 0 20px;
}

.problem-box tr {
  display: flex;
  justify-content: space-around;
  align-items: center;
  border: 1px solid black;
}

.problem-box td {
  width: 100%;
  text-align: center;
  font-size: 20px;
  border: 1px solid black;
}
</style>
