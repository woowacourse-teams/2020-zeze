import axios from "axios";
import {User} from "../pages/Me";

const userInstance = axios.create({
  baseURL: `/api/me`,
});

const usersApi = {
  get() {
    return userInstance.get("/", {
      headers: {
        authorization: localStorage.getItem("accessToken"),
      },
    });
  },
  update(user: User) {
    return userInstance.put("/", user, {
      headers: {
        authorization: localStorage.getItem("accessToken"),
      },
    });
  },
};

export default usersApi;
