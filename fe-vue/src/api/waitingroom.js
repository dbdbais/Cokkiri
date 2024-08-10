import { localAxios } from "@/utils/request";

const axios = localAxios();

export const getWaitingRoom = (roomId, success, fail) => {
  axios.get(`/waitingroom/${roomId}`).then(success).catch(fail);
};

export const getWaitingRoomList = (params, success, fail) => {
  axios.get("/waitingroom?", { params: params }).then(success).catch(fail);
};

export function selectedProblem(problemList, roomId) {
  return axios({
    url: `/waitingroom/problem`,
    method: "post",
    data: { problems: problemList, sessionId: roomId },
  });
}

export function getProblemList(roomId) {
  return axios({
    url: `/waitingroom/problem/${roomId}`,
    method: "get",
  });
}

export const goWaitingRoom = (user, success, fail) => {
  console.log("go Study Room");
  axios
    .post("/waitingroom/enter-room", JSON.stringify(user))
    .then(success)
    .catch(fail);
};

export const createWaitingRoom = (roomData, success, fail) => {
  console.log("create Waiting Room");
  axios
    .post("/waitingroom/create-room", JSON.stringify(roomData))
    .then(success)
    .catch(fail);
};

export const exitWaitingRoom = (roomData, success, fail) => {
  console.log("exit Waiting Room");
  axios.post("waitingroom/quit-room", JSON.stringify(roomData), success, fail);
};
