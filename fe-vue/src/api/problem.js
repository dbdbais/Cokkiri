import { localAxios } from "@/utils/request";

const axios = localAxios();

export function getAllProblems() {
  return axios({
    url: "/problemapi/all",
    method: "get",
  });
}

export function getProblems(id) {
  return axios({
    url: `/problemapi/${id}`,
    method: "get",
  });
}

export function getAllReviews(id) {
  return axios({
    url: `/reviewapi/${id}`,
    method: "get",
  });
}
