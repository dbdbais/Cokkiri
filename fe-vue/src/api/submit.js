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