import React, {useEffect, useRef, useState} from "react";
import styled from "@emotion/styled";
import fscreen from "fscreen";
import {css, Global} from "@emotion/core";
import {isMobile} from "react-device-detect";

import {Keys, MOBILE_MAX_WIDTH} from "../../domains/constants";
import Markdown from "../markdown";
import {applyTheme, Theme} from "../theme";
import MobileSlidesButtons from "./MobileSlidesButtons";

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
  showCursor?: boolean;
  mobileVisible?: boolean;
  isFirstPage?: boolean;
  isFullscreen?: boolean;
}

export const FullScreenBlock = styled.div<FullScreenProps>`
  position: absolute;
  z-index: -1;
  top: ${({isFullscreen}) => (isFullscreen ? 0 : "-9999px")};
  left: ${({isFullscreen}) => (isFullscreen ? 0 : "-9999px")};
  font-size: 100%;
  cursor: ${({showCursor}) => (showCursor ? "default" : "none")};
  
  &:focus {
    outline: none;
  }
  
  @media (max-width: 600px) {
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 9999;
    background-color: #fff;
    display: ${({mobileVisible}) => (mobileVisible ? "block" : "none")};
  }
  
  > div.index {
    display: flex;
    font-size: 24px;
    position: absolute; 
    transform:translate(-50%, -50%);
    bottom: 15px;
    left: 50%;
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
      font-size: 1rem;
    }
    
    p.images {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      align-items: center;
      flex: 1;
      width: 100%;
      
      .image {
        display: block;
        background-repeat: no-repeat;
        background-size: contain;
        background-position: center;
        height: 100%;
        flex: 1;
      }
      
      //img {
      //  background-color: transparent;
      //}
      //
      //img:only-child {
      //  object-fit: contain;
      //  height: 100%;
      //}
      //
      //img:not(:only-child) {
      //  display: block;
      //  object-fit: contain;
      //  box-sizing: border-box;
      //  flex: 1 0 25%;
      //  padding: 10px; 
      //}
    }
    
    iframe.youtube {
      flex: 1;
      
      &:only-child {
        position: absolute;
        left: 0;
        top: 5%;
        width: 100%;
        height: 90%;
        
        @media (max-width: ${MOBILE_MAX_WIDTH}px) {
          top: 15%;
          height: 70%;
        }
      }
    }
    
    ${({slideTheme}) => applyTheme(slideTheme)}
    
    ${({isFirstPage}) => isFirstPage && css`
      display: flex;
      flex-direction: column;
      justify-content: center;
      height: 80%;
      padding: 7%;
      
      h1, h2, h3, h4 {
        border: none;
        padding: 0;
      }
      
      h1 {
        font-size: 4em;
      }
      
      h2 {
        font-size: 2.5em;
        color: #777; 
      }
      
      h3 {
        margin-top: 1em !important; 
      }
    `}
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
  pageNumberVisible?: boolean
}

const FullScreenMode: React.FC<IProps> = ({contents, pageNumberVisible}) => {
  const [index, setIndex] = useState<number>(0);
  const [isFullscreen, setIsFullscreen] = useState<boolean>(false);
  const [showCursor, setShowCursor] = useState<boolean>(false);
  const [mobileVisible, setMobileVisible] = useState<boolean>(false);

  useEffect(() => {
    const fullscreenHandler = () => !fscreen.fullscreenElement && setIsFullscreen(false);

    fscreen.addEventListener("fullscreenchange", fullscreenHandler);
    return () => {
      fscreen.removeEventListener("fullscreenchange", fullscreenHandler);
    };
  }, []);

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
    if (mobileVisible) {
      setMobileVisible(false);
      return;
    }
    if (slideReference.current) {
      if (isMobile) {
        setMobileVisible(true);
        return;
      }

      fscreen.requestFullscreen(slideReference.current);
      slideReference.current.focus();
      setIsFullscreen(true);
    }
  };

  const handleKeyDown = (event: React.KeyboardEvent) => {
    switch (event.key) {
      case Keys.ARROW_RIGHT:
      case Keys.SPACE_BAR:
      case Keys.ENTER:
        event.preventDefault();
        next();
        break;
      case Keys.ARROW_LEFT:
        event.preventDefault();
        prev();
        break;
      default:
        break;
    }
  };

  const next = () => {
    slideExists(index + 1) && setIndex(index + 1);
  };

  const prev = () => {
    slideExists(index - 1) && setIndex(index - 1);
  };

  return (
    <>
      <Global styles={fullScreenStyle}/>
      <FullScreenBlock
        slideTheme={Theme.GITHUB}
        ref={slideReference}
        tabIndex={-1}
        showCursor={showCursor}
        onKeyDown={handleKeyDown}
        onMouseMove={() => setShowCursor(true)}
        mobileVisible={mobileVisible}
        isFirstPage={index === 0}
        isFullscreen={isFullscreen}
      >
        <Markdown value={contents[index]}/>
        {pageNumberVisible && index !== 0 ? <div className="index">{index}</div> : <></>}
      </FullScreenBlock>
      <FullScreenButton onClick={toggleFullScreen}/>
      <MobileSlidesButtons
        visible={mobileVisible}
        next={next}
        prev={prev}
        exit={() => setMobileVisible(false)}
      />
    </>
  );
};

export default React.memo(FullScreenMode);
