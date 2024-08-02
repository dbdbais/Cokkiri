import { localAxios } from "@/utils/request";

const axios = localAxios();

export const getStudyList = (success, fail) => {
  console.log("get Study List");
  axios.get("/regular?").then(success).catch(fail);
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
