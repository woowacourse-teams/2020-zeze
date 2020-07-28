import {css, SerializedStyles} from "@emotion/core";

export enum Theme {
  DEFAULT = "default",
}

export const applyTheme = (theme: Theme): SerializedStyles => {
  switch (theme) {
  case Theme.DEFAULT:
    return css`
      background-color: #222;
      color: #fff;
      padding: 4em;
      
      * {
        font-size: 2.5em;
      }
      
      h1 {
        font-size: 7em;
      }
      
      h2 {
        font-size: 5em;
      }
      
      h3 {
        font-size: 4em;
        margin-bottom: 1em;
      }
      
      h2 + h3 {
        color: #888;
      }
      
      h4 {
        font-size: 3em;
      }
      
      h5 {
        font-size: 2.5em;
      }
      
      h6 {
        font-size: 2em;
      }
      
      p {
        display: flex;
        line-height: 1.3;
        
        > img {
          flex: 1;
          width: 0;
          padding: 0.05em;
          
          &:first-of-type {
            padding-left: 0;
          }
          
          &:last-of-type {
            padding-right: 0;
          }
        }
      }
      
      ul, ol {
        font-size: 1em;
        
        > li {
          margin: 0.5em 0;
        }
      }
      
      iframe.youtube {
        height: 15em;
      }
      
      blockquote {
        font-size: 1em;
        font-weight: 600;
        font-style: italic;
        text-align: center;
        padding: 6em;
      }
      
      pre {
        font-size: 0.9em;
        padding: 4em;
        background-color: rgba(0,0,0, 0.2);
      }
`;
  default:
    return css``;
  }
};

