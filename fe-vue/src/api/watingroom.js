import { localAxios } from "@/utils/request";

const axios = localAxios();

export const getWatingRoom = (roomId, success, fail) => {
  console.log("get Study Room");
  axios.get(`/watingroom/${roomId}`).then(success).catch(fail);
};
