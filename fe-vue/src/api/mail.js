import { localAxios } from "@/utils/request";

const axios = localAxios();
const baseURL = "mail";

export function registerSend(mail) {
  return axios({
    url: `${baseURL}/register`,
    method: "post",
    params: {
      mail,
    },
  });
}

export function passwordSend(mail) {
  return axios({
    url: `${baseURL}/password`,
    method: "post",
    params: {
      mail,
    },
  });
}

export function resetPW(mail) {
  return axios({
    url: `${baseURL}/reset`,
    method: "get",
    params: {
      mail,
    },
  });
}

export function mailCheck(mail, userNumber) {
  return axios({
    url: `${baseURL}/check`,
    method: "get",
    params: {
      mail: mail,
      userNumber: userNumber,
    },
  });
}
