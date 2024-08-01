import { localAxios } from "@/utils/request";

const axios = localAxios();

export const getStudyList = (success, fail) => {
  console.log("get Study List");
  axios.get("/board").then(success).catch(fail);
};

export const getStudy = (success, fail) => {
  console.log("get Study");
  axios.get("/board/1").then(success).catch(fail);
};

export const createStudy = (regularData, success, fail) => {
  console.log("create Study");
  axios
    .post("/regular/create-regular", JSON.stringify(regularData))
    .then(success)
    .catch(fail);
};
