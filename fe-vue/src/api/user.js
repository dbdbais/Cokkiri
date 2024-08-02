import { localAxios } from "@/utils/request";

const axios = localAxios();

export const getUser = (nickname, success, fail) => {
  console.log("getUser");
  axios.get(`/userapi/1`).then(success).catch(fail);
};
