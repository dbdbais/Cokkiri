import { localAxios } from "@/utils/request";

const axios = localAxios();

export const getWaitingRoom = (roomId, success, fail) => {
  console.log("get Study Room");
  axios.get(`/waitingroom/${roomId}`).then(success).catch(fail);
};

export const getWaitingRoomList = (success, fail) => {
  console.log("get Study Room List");
  axios.get("/waitingroom?").then(success).catch(fail);
};

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
