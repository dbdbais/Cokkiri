import { localAxios } from "@/utils/request";

const axios = localAxios();

export const getWaitingRoom = (roomId, success, fail) => {
  console.log("get Study Room");
  axios.get(`/api/waitingroom/${roomId}`).then(success).catch(fail);
};

export const getWaitingRoomList = (params, success, fail) => {
  console.log("get Study Room List");
  axios.get("/api/waitingroom?", { params: params }).then(success).catch(fail);
};

export const goWaitingRoom = (user, success, fail) => {
  console.log("go Study Room");
  axios
    .post("/api/waitingroom/enter-room", JSON.stringify(user))
    .then(success)
    .catch(fail);
};

export const createWaitingRoom = (roomData, success, fail) => {
  console.log("create Waiting Room");
  axios
    .post("/api/waitingroom/create-room", JSON.stringify(roomData))
    .then(success)
    .catch(fail);
};

export const exitWaitingRoom = (roomData, success, fail) => {
  console.log("exit Waiting Room");
  axios.post("/api/waitingroom/quit-room", JSON.stringify(roomData), success, fail);
};
