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

export function receiveRegular(userName) {
  console.log("receive regular");
  return axios({
    url: "/regular/get-request",
    method: "post",
    data: JSON.stringify(userName),
  });
}

export function acceptRegularJoin(data) {
  console.log("accept regular");
  return axios({
    url: "/regular/accept-join",
    method: "post",
    data: JSON.stringify(data),
  });
}

export function getFriends(id) {
  return axios({
    url: `${baseURL}/friends/${id}`,
    method: "get",
  });
}
