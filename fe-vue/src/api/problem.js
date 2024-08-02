import { localAxios } from "@/utils/request";

const axios = localAxios();

function getProblems(id) {
  const config = {
    url: "/problemapi",
    method: "get",
  };

  if (id) {
    config.params = { id };
  }

  return axios(config);
}

export default {
  getProblems,
};
