import { localAxios } from "@/utils/request";

const axios = localAxios();

export const getStudyList = (params, success, fail) => {
  console.log("get Study List");
  axios.get("/regular?", { params: params }).then(success).catch(fail);
};
export const getStudyDetail = (sessionId, success, fail) => {
  console.log("get Study Detail");
  axios.get(`/regular/${sessionId}`).then(success).catch(fail);
};

export const createStudy = (regularData, success, fail) => {
  console.log("create Study");
  axios
    .post("/regular/create-regular", JSON.stringify(regularData))
    .then(success)
    .catch(fail);
};

export const joinStudy = (joinData, success, fail) => {
  console.log("join Study");
  axios
    .post("/regular/send-join", JSON.stringify(joinData))
    .then(success)
    .catch(fail);
};

export const receiveRegular = (userName, success, fail) => {
  console.log("receive regular");
  axios
    .post("/regular/get-request", JSON.stringify(userName))
    .then(success)
    .catch(fail);
};
