import { localAxios } from "@/utils/request";

const axios = localAxios();
const baseURL = "/problemapi";

export function getAllProblems() {
  return axios({
    url: `${baseURL}/all`,
    method: "get",
  });
}

export function getProblem(id) {
  return axios({
    url: `${baseURL}/${id}`,
    method: "get",
  });
}

export function insertClass() {
  return axios({
    url: `${baseURL}/class`,
    method: "post",
  });
}

export function filterSearch(tier) {
  return axios({
    url: `${baseURL}/filter/${tier}`,
    method: "get",
  });
}

export function conditionSearch(keyword) {
  return axios({
    url: `${baseURL}/search`,
    method: "get",
    params: {
      keyword,
    },
  });
}

export function getHint(pId) {
  return axios({
    url: `${baseURL}/hint/${pId}`,
    method: "get",
  });
}
