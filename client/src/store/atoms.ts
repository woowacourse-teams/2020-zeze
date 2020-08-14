import {atom, selector} from "recoil";
import {AxiosResponse} from "axios";
import slideApi, {SlideResponses} from "../api/slide";
import {User} from "../pages/Me";
import usersApi from "../api/user";

export const getAllSlidesQuery = selector({
  key: "getAllSlidesQuery",
  get: async () => {
    const response: AxiosResponse<SlideResponses> = await slideApi.getAll({id: 0, size: 5});

    return response.data.slides;
  },
});

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
