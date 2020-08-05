import React, {useEffect, useRef, useState} from "react";
import styled from "@emotion/styled";
import fscreen from "fscreen";
import {css, Global} from "@emotion/core";
import {Keys, MOBILE_MAX_WIDTH} from "../../domains/constants";
import Markdown from "../markdown";
import {applyTheme, Theme} from "../theme";
import play from "../../assets/icons/play.svg";

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
}

const FullScreenBlock = styled.div<FullScreenProps>`
  position: absolute;
  top: -9999px;
  left: -9999px;
  font-size: 100%;
  cursor: none;
  &:focus {
    outline: none;
  }
  
  > div#themed {
    height: 100%;
    overflow-y: scroll;
    box-sizing: border-box;
    padding: 7%;
    font-size: 1.75rem;
    display: flex;
    flex-direction: column;
    
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
    }
    
    ${({slideTheme}) => applyTheme(slideTheme)}
  }
`;


export const FullScreenButton = styled.button`
  background-image: url(${play});
  background-position: center;
  background-repeat: no-repeat;
  background-color: transparent;
  width: 25px;
  height: 25px;
  border: none;
  position: absolute;
  right: 20px;
  top: 20px;
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
  const [slides, setSlides] = useState<string[]>(contents);

  const [slide, setSlide] = useState<string>(contents[0]);

  useEffect(() => {
    setSlides(contents);
    setSlide(contents[0]);
  }, [contents]);

  const slideReference = useRef<HTMLDivElement>(null);

  const _slideExists = (_index: number) => slides[_index] !== undefined;
  const _changeSlide = (_index: number) => {
    setIndex(_index);
    setSlide(slides[_index]);
  };

  const toggleFullScreen = () => {
    if (slideReference.current) {
      fscreen.requestFullscreen(slideReference.current);
      slideReference.current.focus();
    }
  };
  const handleKeyDown = (event: React.KeyboardEvent) => {
    switch (event.key) {
    case Keys.ARROW_RIGHT:
      _slideExists(index + 1) && _changeSlide(index + 1);
      break;
    case Keys.ARROW_LEFT:
      _slideExists(index - 1) && _changeSlide(index - 1);
      break;
    default:
      break;
    }
  };

  return (
    <>
      <Global styles={fullScreenStyle} />
      <FullScreenBlock
        slideTheme={Theme.GITHUB}
        ref={slideReference}
        tabIndex={-1}
        onKeyDown={handleKeyDown}
      ><Markdown value={slide}/></FullScreenBlock>
      <FullScreenButton onClick={toggleFullScreen}/>
    </>
  );
};

export default FullScreenMode;
