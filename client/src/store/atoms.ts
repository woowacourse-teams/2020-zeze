import {atom, selector} from "recoil";
import {User} from "../pages/Me";
import usersApi from "../api/user";
import {GITHUB_AUTH_URL, Toast} from "../domains/constants";

export const userInfoTrigger = atom<number>({
  key: "userInfoTrigger",
  default: 0,
});

export const userInfoQuery = selector<User | null>({
  key: "userInfoQuery",
  get: ({get}) => {
    get(userInfoTrigger);
    if (!localStorage.getItem("accessToken")) {
      return null;
    }
    return usersApi.get()
      .then(response => response.data)
      .catch(() => window.location.href = GITHUB_AUTH_URL);
  },
  set: ({set}) => {
    set(userInfoTrigger, v => v + 1);
  },
});

export const toastMessages = atom<Array<Toast>>({
  key: "toastMessages",
  default: [],
});

export const sidebarVisibility = atom<boolean>({
  key: "sidebarVisibility",
  default: false,
});
