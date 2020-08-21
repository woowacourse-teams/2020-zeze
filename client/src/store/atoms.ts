import {atom, selector} from "recoil";
import {User} from "../pages/Me";
import usersApi from "../api/user";
import {Toast} from "../domains/constants";

export const userInfoTrigger = atom<number>({
  key: "userInfoTrigger",
  default: 0,
});

export const userInfoQuery = selector<User | null>({
  key: "userInfoQuery",
  get: ({get}) => {
    get(userInfoTrigger);
    return localStorage.getItem("accessToken") ? usersApi.get().then(response => response.data) : null;
  },
  set: ({set}) => {
    set(userInfoTrigger, v => v + 1);
  },
});

export const toastMessages = atom<Array<Toast>>({
  key: "toastMessages",
  default: [],
});
