import React, {useEffect, useRef, useState} from "react";
import styled from "@emotion/styled";
import fscreen from "fscreen";
import {css, Global} from "@emotion/core";
import {Keys, MOBILE_MAX_WIDTH} from "../../domains/constants";
import Markdown from "../markdown";
import {applyTheme, Theme} from "../theme";

const fullScreenStyle = css`
  :-webkit-full-screen {
    width: 100%;
    height: 100%;
  }
  
  :not(:root):fullscreen::backdrop {
    background: #fff;
  }
`;

interface FullScreenProps {
  slideTheme: Theme;
  cursor?: boolean;
}

export const FullScreenBlock = styled.div<FullScreenProps>`
  position: absolute;
  top: -9999px;
  left: -9999px;
  font-size: 100%;
  cursor: ${({cursor}) => (cursor ? "default" : "none")};
  &:focus {
    outline: none;
  }
  
  > div#themed {
    position: relative;
    height: 100%;
    overflow-y: scroll;
    box-sizing: border-box;
    padding: 7%;
    font-size: 1.75rem;
    display: flex;
    flex-direction: column;
    
    @media (max-width: ${MOBILE_MAX_WIDTH}px) {
      font-size: 2em;
    }
    
    div.first-page {
      display: flex;
      flex-direction: column;
      justify-content: center;
      height: 80%;
      padding: 30px;
      
      h1, h2, h3, h4 {
        border: none;
        padding: 0;
      }
      
      h1 {
        font-size: 4em;
      }
      
      h2.subtitle {
        font-size: 2.5em;
        color: #777; 
      }
      
      h3.author {
        margin-top: 1em !important; 
      }
    }
    
    p.images {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      align-items: center;
      min-height: 100%;
      width: 100%;
      
      img:only-child {
        object-fit: contain;
        height: 100%;
      }
      
      img:not(:only-child) {
        display: block;
        object-fit: contain;
        box-sizing: border-box;
        flex: 1 0 25%;
        padding: 10px; 
      }
    }
    
    iframe.youtube {
      flex: 1;
      
      &:only-child {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
      }
    }
    
    ${({slideTheme}) => applyTheme(slideTheme)}
  }
`;


export const FullScreenButton = styled.button`
  background-image: url("/assets/icons/play.svg");
  background-position: center;
  background-repeat: no-repeat;
  background-color: transparent;
  width: 20px;
  height: 20px;
  border: none;
  position: absolute;
  top: 70px;
  right: 30px;
  z-index: 3;
  cursor: pointer;
  
  &:focus {
    outline: none;
  }
`;

interface IProps {
  contents: string[]
}

const FullScreenMode: React.FC<IProps> = ({contents}) => {
  const [index, setIndex] = useState<number>(0);
  const [showCursor, setShowCursor] = useState<boolean>(false);

  useEffect(() => {
    if (showCursor) {
      setTimeout(() => {
        setShowCursor(false);
      }, 2000);
    }
  }, [showCursor]);

  const slideReference = useRef<HTMLDivElement>(null);

  const slideExists = (_index: number) => !!contents[_index];

  const toggleFullScreen = () => {
    if (slideReference.current) {
      fscreen.requestFullscreen(slideReference.current);
      slideReference.current.focus();
    }
  };

  const handleKeyDown = (event: React.KeyboardEvent) => {
    switch (event.key) {
    case Keys.ARROW_RIGHT:
      slideExists(index + 1) && setIndex(index + 1);
      break;
    case Keys.ARROW_LEFT:
      slideExists(index - 1) && setIndex(index - 1);
      break;
    default:
      break;
    }
  };

  return (
    <>
      <Global styles={fullScreenStyle}/>
      <FullScreenBlock
        slideTheme={Theme.GITHUB}
        ref={slideReference}
        tabIndex={-1}
        cursor={showCursor}
        onKeyDown={handleKeyDown}
        onMouseMove={() => setShowCursor(true)}
      ><Markdown value={contents[index]}/></FullScreenBlock>
      <FullScreenButton onClick={toggleFullScreen}/>
    </>
  );
};

export default React.memo(FullScreenMode);
