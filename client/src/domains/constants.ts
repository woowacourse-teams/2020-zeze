export const ZEZE_GRAY = "#1E1E1E";
export const PRIMARY_GREEN = "#76BA1B";
export const SECONDARY_GREEN = "#58C247";

export const MAX_WIDTH = 960;
export const MOBILE_MAX_WIDTH = 575.98;

export const PAGE_CNT = 5;

export const MENU_HEIGHT = 50;
export const TUTORIAL = {
  EDITOR: "1. 에디터고 여기서 편집을 할 수 있으며 기본으로 제공되는 양식은 발표의 첫 페이지이며 없애면 이상해져요.",
  PREVIEW: "2. 마크다운 적용 결과고요 미리 볼 수 있어요.",
  MENU: "3. 각종 기능들인데요, 기존 마크다운 문법과 동일해요. 근데 사진은 드래그 앤 드롭이 가능하구요! 유튭이랑 차트는 설명이 좀 필요할 것 같아요.",
};

export const Keys = {
  ARROW_RIGHT: "ArrowRight",
  ARROW_LEFT: "ArrowLeft",
  SPACE_BAR: " ",
  ENTER: "Enter",
};

const GithubAuth = {
  baseUrl: "https://github.com/login/oauth/authorize",
  callbackUrl: process.env.REACT_APP_CALLBACK_URL,
  clientId: process.env.REACT_APP_CLIENT_ID,
};

export const GITHUB_AUTH_URL = `${GithubAuth.baseUrl}?client_id=${GithubAuth.clientId}&redirect_uri=${GithubAuth.callbackUrl}`;

export enum AccessLevel {
  PRIVATE = "PRIVATE",
  PUBLIC = "PUBLIC"
}

export type Toast = {
  title: string,
  backgroundColor: string,
  icon: string,
}

export enum ToastType {
  SUCCESS = "SUCCESS",
  ERROR = "ERROR",
  WARN = "WARN",
  INFO ="INFO",
}
