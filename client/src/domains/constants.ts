export const ZEZE_GRAY = "#1E1E1E";
export const PRIMARY_GREEN = "#76BA1B";
export const SECONDARY_GREEN = "#58C247";

export const MAX_WIDTH = 960;
export const MOBILE_MAX_WIDTH = 575.98;

export const PAGE_CNT = 5;

export const MENU_HEIGHT = 50;

export const TUTORIAL_CONTENTS = {
  EDITOR: "This is the editor. You can write freely with Markdown syntax.\n" +
    "You can set the privacy by clicking the lock button.\n" +
    "You can save the content you are creating by clicking Cmd/Ctrl + s or a button.\n" +
    "Press the start slideshow button to start the presentation.\n" +
    "You can add images by dragging them into the editor.",
  PREVIEW: "You can check the rendered content entered in the editor on the preview screen in real time.\n" +
    "Keep in mind that the pages are divided with a thick line(<hr>).",
  MENU: "Try out example buttons at the top to check supported features.\n" +
    "You can use all the GFM style syntax, and additional support such as Youtube and Chart are currently available.\n" +
    "We will support more features in the future, so stay tuned!",
};

export const TUTORIAL_TITLE = {
  EDITOR: "Editor",
  PREVIEW: "Preview",
  MENU: "Other menus",
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
