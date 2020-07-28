import React, {useEffect, useRef, useState} from "react";
import fscreen from "fscreen";
import {css, Global} from "@emotion/core";
import * as S from "./assets";
import {Keys} from "../../domains/constants";
import Markdown from "./markdown";
import {Theme} from "./theme";


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

  // const _endSlide = () => {
  //   if (slideReference.current) {
  //     slideReference.current.blur();
  //   }
  //   fscreen.exitFullscreen();
  // };

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
      <Global styles={css`
          :-webkit-full-screen {
            width: 100%;
            height: 100%;
          }
          
          :not(:root):fullscreen::backdrop {
            background: #fff;
          }
        `}
      />
      <S.FullScreen
        slideTheme={Theme.DEFAULT}
        ref={slideReference}
        tabIndex={-1}
        onKeyDown={handleKeyDown}
      ><Markdown value={slide}/></S.FullScreen>
      <S.FullScreenButton onClick={toggleFullScreen}/>
    </>
  );
};

export default FullScreenMode;
