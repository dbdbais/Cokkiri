import { localAxios } from "@/utils/request";

const axios = localAxios();
const baseURL = "reviewapi";

/* *******************
       Review API
  ******************* */

export function create(data) {
  return axios({
    url: `${baseURL}/create`,
    method: "post",
    data,
  });
}

export function getAllReviews(pId) {
  return axios({
    url: `${baseURL}/${pId}`,
    method: "get",
  });
}

export function modify(data) {
  return axios({
    url: `${baseURL}/modify`,
    method: "put",
    data,
  });
}

export function delReview(rId) {
  return axios({
    url: `${baseURL}/delete/${rId}`,
    method: "delete",
  });
}

export function auto(data) {
  return axios({
    url: `${baseURL}/auto`,
    method: "post",
    data,
  });
}
