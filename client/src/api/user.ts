import axios from "axios";

const userInstance = axios.create({
  baseURL: "/api/me",
  headers: {authorization: localStorage.getItem("accessToken")},
});

const usersApi = {
  get() {
    return userInstance.get("/");
  },
  update() {
    return userInstance.put("/")
  }
};

export default usersApi;
