import { localAxios } from "@/utils/request";

const axios = localAxios();
const baseURL = "userapi"

export function register(data) {
  return axios({
    url: `${baseURL}/register`,
    method: "post",
    data
  });
}

export function login(data) {
  return axios({
    url: `${baseURL}/login`,
    method: "post",
    data
  });
}

export function getUser(id) {
  return axios({
    url: `${baseURL}/${id}`,
    method: "get"
  });
}

export function modifyUser(data) {
  return axios({
    url: `${baseURL}`,
    method: "put",
    data
  });
}

export function deleteUser(id) {
  return axios({
    url: `${baseURL}/${id}`,
    method: "delete"
  });
}
