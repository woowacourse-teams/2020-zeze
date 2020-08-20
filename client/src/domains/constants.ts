export const ZEZE_GRAY = "#1E1E1E";
export const PRIMARY_GREEN = "#76BA1B";
export const SECONDARY_GREEN = "#58C247";

export const MAX_WIDTH = 960;
export const MOBILE_MAX_WIDTH = 575.98;

export const PAGE_CNT = 5;

export const Keys = {
  ARROW_RIGHT: "ArrowRight",
  ARROW_LEFT: "ArrowLeft",
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

