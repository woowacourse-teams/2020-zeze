import {css, SerializedStyles} from "@emotion/core";
import github from "./github";

export enum Theme {
  DEFAULT= "default",
  DARK = "dark",
  GITHUB = "github"
}

export const applyTheme = (theme: Theme): SerializedStyles => {
  switch (theme) {
  case Theme.DARK:
    return css`
      @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
      
      background-color: #222;
      color: #fff;
      
      h1, h2, h3, h4, h5, h6 {
        font-family: "Jua", sans-serif;
        font-weight: 300;
        margin: 1rem 0;
      }
      
      h1 + h2, h2 + h3 {
        color: #888;
      }
`;
  case Theme.GITHUB:
    return github;
  case Theme.DEFAULT:
  default:
    return css``;
  }
};

