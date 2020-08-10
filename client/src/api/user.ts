import axios from "axios";
import {User} from "../pages/Me";

const userInstance = axios.create({
  baseURL: "/api/me",
  headers: {authorization: localStorage.getItem("accessToken")},
});

const usersApi = {
  get() {
    return userInstance.get("/");
  },
  update(user: User) {
    return userInstance.put("/", user)
  }
};

export default usersApi;
