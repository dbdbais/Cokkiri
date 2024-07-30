import { localAxios } from "@/utils/request";

const axios = localAxios();

export const getUser = (nickname, success, fail) => {
  console.log("getUser");
  axios.get(`/user/${nickname}`).then(success).catch(fail);
};
