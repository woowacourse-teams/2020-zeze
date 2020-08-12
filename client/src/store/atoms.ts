import {atom, selector} from "recoil";
import {AxiosResponse} from "axios";
import slideApi, {SlideResponses} from "../api/slide";
import {AccessLevel} from "../domains/constants";
import parse, {MetaProps} from "../utils/metadata";
import {User} from "../pages/Me";
import usersApi from "../api/user";

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
    ?.split(("---"))
    .filter(content => content.trim().length !== 0),
});

export const slideAccessLevelState = atom<AccessLevel>({
  key: "slideAccessLevel",
  default: AccessLevel.PRIVATE,
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
