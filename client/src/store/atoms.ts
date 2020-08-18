import {atom, selector} from "recoil";
import {AxiosResponse} from "axios";
import slideApi, {SlideResponses} from "../api/slide";
import {User} from "../pages/Me";
import usersApi from "../api/user";
import {Toast} from "../domains/constants";
import {checkIcon, errorIcon, infoIcon, warnIcon} from "../assets/icons";

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

export const toastMessages = atom<Array<Toast>>({
  key : "toastMessages",
  default: [
    {
      id: 1,
      title: 'Success',
      description: 'This is a success toast component',
      backgroundColor: '#5cb85c',
      icon: checkIcon
    },
    {
      id: 2,
      title: 'Danger',
      description: 'This is an error toast component',
      backgroundColor: '#d9534f',
      icon: errorIcon
    },
    {
      id: 3,
      title: 'Info',
      description: 'This is an info toast component',
      backgroundColor: '#5bc0de',
      icon: infoIcon
    },
    {
      id: 4,
      title: 'Warning',
      description: 'This is a warning toast component',
      backgroundColor: '#f0ad4e',
      icon: warnIcon
    }
  ],
})
