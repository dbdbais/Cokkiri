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

export function deleteComment(cid) {
  return axios({
    url: `${baseURL}/${cid}`,
    method: "delete",
  });
}

export function getListByReviewId(rid) {
  return axios({
    url: `${baseURL}/${rid}`,
    method: "get",
  });
}
