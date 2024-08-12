import { userStore } from "@/stores/user";
import "@/api/openvidu-browser-2.30.0";

// 유저 개인의 화면 객체를 저장할 변수
var publisher;
var OVCamera; // 카메라
var OVScreen; // 화면
var sessionCamera; // 카메라 정보를 담을 세션
var sessionScreen; // 화면 정보를 담을 세션

// const myUser = userStore();
var myUserName; //myUser.user.name; // 들어오기 전 설정하는 유저 이름
var mySessionId; // 카메라 정보를 담을 세션의 id
var mySessionIdScreen; // 화면 정보를 담을 세션의 id
var screensharing = false; // 화면 공유 상태

// 세션 == 유저들이 들어가 있는 방
// 세션 ID != 방 제목
// 토큰 == 유저
var token; // 카메라 세션의 유저 토큰
var tokenScreen; // 화면 세션의 유저 토큰
var numVideos = 0; // 카메라 영역 css 적용을 위한 변수

// 유저 이름, 세션 ID 입력 후 join 버튼 클릭시 동작
function joinSession(roomId, userName) {
  // join 버튼 비활성화
  // document.getElementById("join-btn").disabled = true;
  // document.getElementById("join-btn").innerHTML = "Joining...";

  // 입력받은 정보 저장
  mySessionId = "" + roomId;
  myUserName = userName;

  // 화면 세션 생성을 위한 id 생성 => 현재 "카메라 세션 ID + Screen" 이며 바꿔도 됨
  mySessionIdScreen = mySessionId + "Screen";

  // 카메라 세션에서 토큰 발행
  getToken(function () {
    // 세션 ID에 맞춰 세션 생성
    OVCamera = new OpenVidu();

    sessionCamera = OVCamera.initSession();

    // 카메라 세션에서 stream 생성시 (다른 사람 입장시)
    sessionCamera.on("streamCreated", (event) => {
      // 카메라인지 화면인지 체크
      if (event.stream.typeOfVideo == "CAMERA") {
        // stream을 index.html의 카메라 영역에 귀속
        var subscriber = sessionCamera.subscribe(event.stream, "members");
        // stream에서 video 태그의 객체 생성시
        subscriber.on("videoElementCreated", (event) => {
          // 해당 video 태그에 유저 정보 추가 (p 태그로 출력) -> 현재 백엔드에서 유저 정보를 보내지 않아 실행 X
          appendUserData(event.element, subscriber.stream.connection);
        });

        // numVideos += 1
        // updateNumVideos(1);
      }
    });

    // 카메라 세션에서 stream 제거시
    sessionCamera.on("streamDestroyed", (event) => {
      // video 태그에 추가했던 유저 정보 제거
      removeUserData(event.stream.connection);

      // numVideos -= 1
      // updateNumVideos(-1);
    });

    // 카메라 세션에 예외 발생시 -> console 출력
    sessionCamera.on("exception", (exception) => {
      console.warn(exception);
    });

    // token과 유저 이름을 이용해 카메라 세션에 연결 (.then() => 이전 동작을 완료한 뒤 수행하는 메소드)
    sessionCamera
      .connect(token, { userName: myUserName })
      .then(() => {
        // 세션에 연결됐으므로 join 창(유저 이름, 세션 이름 설정하던 창)을 숨기고 session 창(화상 회의 창) 공개
        // document.getElementById('join').style.display = 'none';
        // document.getElementById('session').style.display = 'block';

        // 미디어 서버로 보낼 카메라 영상 설정
        publisher = OVCamera.initPublisher("members", {
          audioSource: undefined, // 오디오 설정
          videoSource: undefined, // 비디오 설정 - 기본값 카메라
          publishAudio: true, // 오디오 on / off
          publishVideo: true, // 비디오 on / off
          resolution: "200x150", // 해상도 설정
          frameRate: 30, // 프레임 설정
          insertMode: "APPEND", // 카메라 영역에 추가하는 방법 - 현재 맨뒤에 추가하는 방식
          mirror: false, // 좌우 반전 설정
        });

        // video 태그 생성시
        publisher.on("videoElementCreated", function (event) {
          // initMainVideo(event.element, myUserName); // 메인 비디오 영역에 추가
          appendUserData(event.element, myUserName); // 카메라 영역에 유저 정보 추가
          event.element["muted"] = true; // 자신의 카메라 영상 음소거
        });

        // 카메라 세션에 자신의 카메라 데이터 전송
        sessionCamera.publish(publisher);

        sessionCamera.on("signal:myChat", (event) => {
          console.log(event.data);
        });
      })
      .catch((error) => {
        console.log(
          "There was an error connecting to the session:",
          error.code,
          error.message
        );
        // enableBtn(); // 접속 오류이므로 join 버튼 활성화
      });
  });

  // 화면 세션에서 토큰 발행
  getScreenToken(function () {
    // 세션 ID에 맞춰 세션 생성
    OVScreen = new OpenVidu();

    sessionScreen = OVScreen.initSession();

    // 화면 세션에 stream 생성시 (다른 사람이 화면 공유 시작)
    sessionScreen.on("streamCreated", (event) => {
      // 카메라인지 화면인지 체크
      if (event.stream.typeOfVideo == "SCREEN") {
        // stream을 index.html의 화면 영역에 귀속
        var subscriberScreen = sessionScreen.subscribe(
          event.stream,
          "container-screens"
        );
        // stream에서 video 태그의 객체 생성시
        subscriberScreen.on("videoElementCreated", (event) => {
          // 해당 video 태그에 유저 정보 추가 (p 태그로 출력) -> 현재 백엔드에서 유저 정보를 보내지 않아 실행 X
          appendUserData(event.element, subscriberScreen.stream.connection);
        });

        screensharing = true;
      }
    });

    // 화면 세션에서 stream 제거시
    sessionScreen.on("streamDestroyed", (event) => {
      // video 태그에 추가했던 유저 정보 제거
      removeUserData(event.stream.connection);

      screensharing = false;
    });

    // token과 유저 이름을 이용해 화면 세션에 연결 (.then() => 이전 동작을 완료한 뒤 수행하는 메소드)
    sessionScreen
      .connect(tokenScreen, { userName: myUserName })
      .then(() => {
        // 세션에 연결됐으므로 화면 공유 버튼 활성화
        // document.getElementById('buttonScreenShare').style.visibility = 'visible';
        console.log("Session screen connected");
      })
      .catch((error) => {
        console.warn(
          "There was an error connecting to the session for screen share:",
          error.code,
          error.message
        );
      });
  });

  // sessionCamera.on("connectionCreated", (event) => {
  //   console.log("connectionCreated", event);
  //   listConnectedUsers();
  // });
}

// function listConnectedUsers() {
//   const connections = sessionCamera.connections;
//   connections.forEach((connection) => {
//     console.log("connectionCreated", connection);
//   });
// }

// 화면 공유 기능
function publishScreenShare() {
  // 다른 사람이 화면 공유 중
  if (screensharing) {
    alert("공유는 한 사람만 가능합니다.");
    return;
  }

  // 미디어 서버로 보낼 화면 영상 설정
  var publisherScreen = OVScreen.initPublisher("container-screens", {
    audioSource: undefined, // 오디오 설정
    videoSource: "screen", // 비디오 설정 - 기본값 카메라
    publishAudio: true, // 오디오 on / off
    publishVideo: true, // 비디오 on / off
    resolution: "300x200", // 해상도 설정
    frameRate: 30, // 프레임 설정
    insertMode: "APPEND", // 카메라 영역에 추가하는 방법 - 현재 맨뒤에 추가하는 방식
    mirror: false, // 좌우 반전 설정 - screen일 경우 false 고정
  });

  // 화면 객체 처음 생성시
  publisherScreen.once("accessAllowed", (event) => {
    // 화면 공유 버튼 비활성화
    // document.getElementById('buttonScreenShare').style.visibility = 'hidden';
    screensharing = true; // 화면 공유 상태 변수 수정

    // 화면 공유 종료시
    publisherScreen.stream
      .getMediaStream()
      .getVideoTracks()[0]
      .addEventListener("ended", () => {
        console.log('User pressed the "Stop sharing" button');
        sessionScreen.unpublish(publisherScreen); // 화면 영상 전송 중지
        // document.getElementById('buttonScreenShare').style.visibility = 'visible'; // 화면 공유 버튼 활성화
        screensharing = false; // 화면 공유 상태 변수 수정
      });

    // 화면 세션에 자신의 화면 데이터 전송
    sessionScreen.publish(publisherScreen);
  });

  // video 태그 생성시
  publisherScreen.on("videoElementCreated", function (event) {
    appendUserData(event.element, sessionScreen.connection); // 유저 정보 추가
    event.element["muted"] = true; // 자신의 화면 영상 음소거
  });

  // 화면 공유 기능 오류 발생시
  publisherScreen.once("accessDenied", (event) => {
    console.error("Screen Share: Access Denied");
  });
}

// 화면 개수에 따른 css 설정
// function updateNumVideos(i) {
// 	numVideos += i;
// 	$('video').removeClass();
// 	switch (numVideos) {
// 		case 1:
// 			$('video').addClass('two');
// 			break;
// 		case 2:
// 			$('video').addClass('two');
// 			break;
// 		case 3:
// 			$('video').addClass('three');
// 			break;
// 		case 4:
// 			$('video').addClass('four');
// 			break;
// 	}
// }

// 세션 연결 종료 기능
function leaveSession() {
  sessionCamera.disconnect(); // 카메라 세션 연결 종료
  sessionScreen.disconnect(); // 화면 세션 연결 종료
  // enableBtn(); // join 버튼 활성화
}

// join 버튼 활성화 함수
// function enableBtn (){
// 	document.getElementById("join-btn").disabled = false;
// 	document.getElementById("join-btn").innerHTML = "Join!";
// }

// 유저 정보 생성 함수
function appendUserData(videoElement, connection) {
  var userData;
  var nodeId;

  // 정보가 string 으로 전송시
  if (typeof connection === "string") {
    userData = connection;
    nodeId = connection;
  }
  // 정보가 string이 아닐시
  else {
    try {
      userData = JSON.parse(connection.data).userName;
    } catch (e) {
      console.error("Error parsing connection data:", connection.data, e);
      return;
    }
    nodeId = connection.connectionId;
  }
  // index.html에 정보 추가
  var dataNode = document.createElement("div");
  dataNode.className = "data-node";
  dataNode.id = "data-" + nodeId;
  dataNode.innerHTML = "<p>" + userData + "</p>";
  videoElement.parentNode.insertBefore(dataNode, videoElement.nextSibling);
  // addClickListener(videoElement, userData);
}

// 유저 정보 제거 함수
function removeUserData(connection) {
  var dataNodeToRemove = document.getElementById(
    "data-" + connection.connectionId
  );
  if (dataNodeToRemove) {
    dataNodeToRemove.parentNode.removeChild(dataNodeToRemove);

    // 카메라 세션에서 token 제거
    httpRequest(
      "POST",
      `${process.env.VITE_VUE_API_URL}session/remove-user`,
      {
        sessionId: mySessionId,
        token: token,
      },
      "User couldn't be removed from session",
      (res) => {
        console.warn("You have been removed from session " + mySessionId);
      }
    );

    // 화면 세션에서 token 제거
    httpRequest(
      "POST",
      `${process.env.VITE_VUE_API_URL}session/remove-user`,
      {
        sessionId: mySessionIdScreen,
        token: tokenScreen,
      },
      "User couldn't be removed from session",
      (res) => {
        console.warn("You have been removed from session " + mySessionIdScreen);
      }
    );
  }
}

// 비디오 클릭시 이벤트 함수 (클릭시 메인 비디오 화면 변경)
// function addClickListener(videoElement, userData) {
// 	videoElement.addEventListener('click', function () {
// 		var mainVideo = $('#main-video video').get(0);
// 		if (mainVideo.srcObject !== videoElement.srcObject) {
// 			$('#main-video').fadeOut("fast", () => {
// 				$('#main-video p').html(userData);
// 				mainVideo.srcObject = videoElement.srcObject;
// 				$('#main-video').fadeIn("fast");
// 			});
// 		}
// 	});
// }

// 메인 비디오 화면 초기화
// function initMainVideo(videoElement, userData) {
// 	document.querySelector('#main-video video').srcObject = videoElement.srcObject;
// 	document.querySelector('#main-video p').innerHTML = userData;
// 	document.querySelector('#main-video video')['muted'] = true;
// }

// 백엔드에 요청을 보내는 함수
function httpRequest(method, url, body, errorMsg, callback) {
  // $('#textarea-http').text('');
  var http = new XMLHttpRequest();
  http.open(method, url, true);
  http.setRequestHeader("Content-type", "application/json"); // 요청의 header 설정
  http.addEventListener("readystatechange", processRequest, false); // 요청의 상태에 따른 동작 함수 설정
  http.send(JSON.stringify(body)); // 요청 전송

  // 결과에 따른 동작을 설정한 함수
  function processRequest() {
    // 요청 완료시
    if (http.readyState == 4) {
      // 정상적으로 요청 수행시
      if (http.status == 200) {
        try {
          // 인자로 받은 콜백 함수 실행
          callback(JSON.parse(http.responseText));
        } catch (e) {
          callback(e);
        }
      }
      // 요청에 오류 발생시
      else {
        console.warn(errorMsg + " (" + http.status + ")");
        console.warn(http.responseText);
        // $('#textarea-http').text(errorMsg + ": HTTP " + http.status + " (" + http.responseText + ")");
      }
    }
  }
}

// 토큰 발행 함수 (인자로 콜백함수) - 카메라 세션
function getToken(callback) {
  httpRequest(
    "POST",
    `${process.env.VITE_VUE_API_URL}api/session/get-token`,
    // 세션 ID와 유저 정보 전송
    {
      sessionId: mySessionId,
      userName: myUserName,
    },
    "Request of TOKEN gone WRONG:",
    (res) => {
      token = res[0]; // 발행된 토큰 저장
      console.warn("Request of TOKEN gone WELL (TOKEN:" + token + ")");
      callback(token); // 토큰을 이용해 콜백 함수 실행
    }
  );
}

// 토큰 발행 함수 (인자로 콜백함수) - 화면 세션
function getScreenToken(callback) {
  httpRequest(
    "POST",
    `${process.env.VITE_VUE_API_URL}api/session/get-token`,
    // 세션 ID와 유저 정보 전송
    {
      sessionId: mySessionIdScreen,
      userName: myUserName,
    },
    "Request of TOKEN(screen) gone WRONG:",
    (res) => {
      console.log(res);
      tokenScreen = res[0]; // 발행된 토큰 저장
      console.warn("Request of TOKEN gone WELL (TOKEN:" + tokenScreen + ")");
      callback(tokenScreen); // 토큰을 이용해 콜백 함수 실행
    }
  );
}

function changeVideo(video) {
  publisher.publishVideo(video);
}

function changeAudio(audio) {
  publisher.publishAudio(audio);
}

function sendChat() {
  let text = document.querySelector("#chatText").value;

  sessionCamera
    .signal({
      data: myUserName + ": " + text, // Any string (optional)
      to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
      type: "myChat", // The type of message (optional)
    })
    .then(() => {
      console.log("Message successfully sent");
    })
    .catch((error) => {
      console.error(error);
    });
}

function sendHint() {
  sessionCamera
    .signal({
      data: "hint", // Any string (optional)
      to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
      type: "hint", // The type of message (optional)
    })
    .then(() => {
      console.log("Message successfully sent");
    })
    .catch((error) => {
      console.error(error);
    });
}

export {
  joinSession,
  publishScreenShare,
  leaveSession,
  sendChat,
  changeVideo,
  changeAudio,
};
