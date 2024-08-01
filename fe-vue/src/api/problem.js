import request from "@/utils/request";

function getProblems(id) {
  const config = {
    url: "/problemapi",
    method: "get",
  };

  if (id) {
    config.params = { id };
  }

  return request(config);
}

export default {
  getProblems,
};
