import {atom, selector} from "recoil";
import {AxiosResponse} from "axios";
import slideApi, {SlideResponses} from "../api/slide";
import {AccessLevel} from "../domains/constants";
import parse, {MetaProps} from "../utils/metadata";
import {User} from "../pages/Me";
import usersApi from "../api/user";
import {user} from "../assets/icons";

export const getAllSlidesQuery = selector({
  key: "getAllSlidesQuery",
  get: async () => {
    const response: AxiosResponse<SlideResponses> = await slideApi.getAll({id: 0, size: 5});

    return response.data.slides;
  },
});

export const slideIdState = atom<number | undefined>({
  key: "slideIdState",
  default: undefined,
});

export const slideContentState = atom<string>({
  key: "slideContentState",
  default: "",
});

export const slideMetadata = selector<MetaProps>({
  key: "slideTitleState",
  get: ({get}) => parse(get(slideContentState)).metadata ?? {title: ""},
});

export const parsedSlides = selector<string[]>({
  key: "parsedSlidesState",
  get: ({get}) => parse(get(slideContentState)).content
    ?.split(/^---$/m)
    .filter(content => content.trim().length !== 0),
});

export const slideAccessLevelState = atom<AccessLevel>({
  key: "slideAccessLevel",
  default: AccessLevel.PRIVATE,
});

export const userInfo = atom<User | null>( {
  key: "userInfo",
  default: null,
});

export const userInfoQuery = selector<User | null>({
  key: "userInfoQuery",
  get: async () => {
    return localStorage.getItem("accessToken") ? await usersApi.get().then(response => response.data) : null
  },
  set: ({set}, user) => set(userInfo, user),
});
