import {atom, selector} from "recoil";
import {AxiosResponse} from "axios";
import slideApi, {SlideResponses} from "../api/slide";
import {User} from "../pages/Me";
import usersApi from "../api/user";
import {Toast} from "../domains/constants";
import {googleAnalyticsException} from "../utils/googleAnalytics";

export const getAllSlidesQuery = selector({
  key: "getAllSlidesQuery",
  get: async () => {
    try {
      const response: AxiosResponse<SlideResponses> = await slideApi.getAll({id: 0, size: 5});

      return response.data.slides;
    } catch (error) {
      googleAnalyticsException("슬라이드 목록 조회 API 호출 실패");
      throw error;
    }
  },
});

export const getPublicSlidesQuery = selector({
  key: "getPublicSlidesQuery",
  get: async () => {
    const response: AxiosResponse<SlideResponses> = await slideApi.getPublic({id: 0, size: 5});

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

export const toastMessages = atom<Array<Toast>>({
  key: "toastMessages",
  default: [],
});
