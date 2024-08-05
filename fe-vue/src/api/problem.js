import { localAxios } from "@/utils/request";

const axios = localAxios();

export function getProblems(id) {
  const config = {
    url: "/problemapi",
    method: "get",
  };

  if (id) {
    config.params = { id };
  }

  return axios(config);
}
