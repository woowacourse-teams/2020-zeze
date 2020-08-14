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

export const getUserInfoQuery = selector({
  key: "getUserInfoQuery",
  get: async () => {
    const response: AxiosResponse<User> = await usersApi.get();

    return response.data;
  },
});

export const userInfoState = atom<User>({
  key: "userInfoState",
  default: getUserInfoQuery,
});
