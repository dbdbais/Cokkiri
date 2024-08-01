import { localAxios } from "@/utils/request";

const axios = localAxios();

export const getWatingRoom = (roomId, success, fail) => {
  console.log("get Study Room");
  axios.get(`/watingroom/${roomId}`).then(success).catch(fail);
};

export const goWatingRoom = (user, success, fail) => {
  console.log("go Study Room");
  axios
    .post("/watingroom/enter-room", JSON.stringify(user))
    .then(success)
    .catch(fail);
};
