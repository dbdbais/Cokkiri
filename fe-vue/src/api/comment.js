import { localAxios } from "@/utils/request";

const axios = localAxios();
const baseURL = "/comment";

export function insert(data) {
  return axios({
    url: `${baseURL}/insert`,
    method: "post",
    data,
  });
}

export function modify(data) {
  return axios({
    url: `${baseURL}/modify`,
    method: "post",
    data,
  });
}

export function deleteComment(cId) {
  return axios({
    url: `${baseURL}/${cId}`,
    method: "delete",
  });
}

export function getAllComment(rId) {
  return axios({
    url: `${baseURL}/${rId}`,
    method: "get",
  });
}
