import { localAxios } from "@/utils/request";

const axios = localAxios();
const baseURL = "userapi";

/* *******************
       User API
  ******************* */

export function register(data) {
  console.log(data);
  return axios({
    url: `${baseURL}/register`,
    method: "post",
    data,
  });
}

export function getAllUser() {
  return axios({
    url: `${baseURL}`,
    method: "get",
  });
}

export function login(data) {
  return axios({
    url: `${baseURL}/login`,
    method: "post",
    data,
  });
}

export function getUser(id) {
  return axios({
    url: `${baseURL}/${id}`,
    method: "get",
  });
}

export function modifyUser(data) {
  return axios({
    url: `${baseURL}`,
    method: "put",
    data,
  });
}

export function deleteUser(id) {
  return axios({
    url: `${baseURL}/${id}`,
    method: "delete",
  });
}

/* *******************
      Friend API
  ******************* */

export function addFriend(myId, friendId) {
  return axios({
    url: `${baseURL}/friends`,
    params: {
      userId: myId,
      friendUserId: friendId,
      status: "REQUEST",
    },
    method: "post",
  });
}

export function getFriends(id) {
  return axios({
    url: `${baseURL}/friends/${id}`,
    method: "get",
  });
}

export function deleteFriend(myId, friendId) {
  return axios({
    url: `${baseURL}/friends`,
    params: {
      userId: myId,
      friendUserId: friendId,
    },
    method: "delete",
  });
}
