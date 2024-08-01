import axios from "axios";

const { VITE_VUE_API_URL } = import.meta.env;

// local vue api axios instance
const service = axios.create({
  baseURL: VITE_VUE_API_URL,
  headers: {
    common: {
      Authorization: "",
    },
    post: {
      "Content-Type": "application/json",
    },
    put: {
      "Content-Type": "application/json",
    },
  },
});

export default service;
