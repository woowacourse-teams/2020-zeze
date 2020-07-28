import {css, SerializedStyles} from "@emotion/core";
import {MOBILE_MAX_WIDTH} from "../../../domains/constants";

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
      display: flex;
      flex-direction: column;
      
      * {
        font-size: 2.5em;
      }
      
      h1 {
        font-size: 7em;
        font-weight: 200;
      }
      
      h2 {
        font-size: 5em;
        font-weight: 200;
      }
      
      h3 {
        font-size: 4em;
        font-weight: 200;
        margin-bottom: 1em;
      }
      
      h1 + h2, h2 + h3 {
        color: #888;
      }
      
      h4 {
        font-size: 3em;
        font-weight: 200;
      }
      
      h5 {
        font-size: 2.5em;
        font-weight: 200;
      }
      
      h6 {
        font-size: 2em;
        font-weight: 200;
      }
      
      p {
        display: flex;
        line-height: 1.6;
        
        > span.image {
          display: flex;
          justify-content: center;
          align-items: center;
          flex: 1;
          padding: 0.2em 0.05em;
         
          &:first-of-type {
            padding-left: 0;
          }
          
          &:last-of-type {
            padding-right: 0;
          }
          
          &:only-child {
            height: 100%;
          }
          
          > img {
            max-width: 100%;
            max-height: 100%;
          }
        }
      }
      
      ul, ol {
        font-size: 1em;
        font-weight: 200;
        
        > li {
          margin: 0.5em 0;
        }
      }
      
      iframe.youtube {
        height: 15em;
        flex: 1;
      }
      
      blockquote {
        font-size: 1em;
        font-weight: 600;
        font-style: italic;
        padding: 6em;
        
      }
      
      pre {
        font-size: 0.6em;
        padding: 4em;
        background-color: rgba(0,0,0, 0.2);
        white-space: pre-wrap;
      }
      
      @media (max-width: ${MOBILE_MAX_WIDTH}px) {
     
      }
`;
  default:
    return css``;
  }
};

