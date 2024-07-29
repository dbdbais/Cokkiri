import axios from "axios";

const { VITE_VUE_API_URL } = import.meta.env;

function localAxios() {
  const service = axios.create({
    baseURL: VITE_VUE_API_URL,
  });
  service.defaults.headers.common["Authorization"] = "";
  service.defaults.headers.post["Content-Type"] = "application/json";
  service.defaults.headers.put["Content-Type"] = "application/json";

  return service;
}

export { localAxios };
