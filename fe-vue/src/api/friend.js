import { localAxios } from "@/utils/request";

const axios = localAxios();

const baseURL = "userapi/friends";

export function getFriend(id) {
  console.log("get Friends");
  return axios({
    url: `${baseURL}/${id}`,
    method: "get",
  });
}

export const inviteFriend = (data, success, fail) => {
  console.log("invite Friend");
  axios
    .post("/waitingroom/invite-room", JSON.stringify(data))
    .then(success)
    .catch(fail);
};
