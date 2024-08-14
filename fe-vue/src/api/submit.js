import { localAxios } from "@/utils/request";

const axios = localAxios();
const baseURL = "submitapi";

/* *******************
       Submit API
  ******************* */

export function insert(data) {
  return axios({
    url: `${baseURL}`,
    method: "post",
    data,
  });
}

export function getSolved(userId) {
  return axios({
    url: `${baseURL}`,
    method: "get",
    params: { userId },
  });
}

export function getFilteredSubmit(userId, problemId) {
  return axios({
    url: `${baseURL}/filter`,
    method: "get",
    params: { userId, problemId },
  });
}

export function canReview(userId, problemId) {
  return axios({
    url: `${baseURL}/view`,
    method: "get",
    params: { userId, problemId },
  });
}
