import React, {useCallback, useEffect, useState} from "react";
import styled from "@emotion/styled";

import {MENU_HEIGHT} from "../../domains/constants";

const TutorialLayout = styled.div`
  > div.dropback {
    display: block;
    position: absolute;
    z-index: 4;
    opacity: 0.8;
    background-color: rgba(0,0,0,0.6);
  }
 
  > div.top {
    top: 0;
    height: ${MENU_HEIGHT}px;
    width: 100%;
  }
  
  > div.left {
    top: ${MENU_HEIGHT}px;
    left: 0;
  }
  
  > div.right {
    right: 0;
    height: 100%;
  }
`;

const ExplanationBox = styled.div`
  display: block;
  position: absolute;
  z-index: 5;
  width: 300px;
  border-radius: 10px;
  background-color: #ffffff;
  padding: 15px;
`;

const Title = styled.div`
  display: flex;
  flex-direction: row;
  height: 36px;
  
  > div {
    font-size: 24px;
  }
  
  > button {
    position: absolute;
    width: 24px;
    height: 24px;
    border: 0;
    outline: none;
  }
`;

const LeftButton = styled.button`
  left: calc(100% - 80px);
  background: url("/assets/icons/left-arrow.svg");
`;

const RightButton = styled.button`
  left: calc(100% - 36px);
  background: url("/assets/icons/right-arrow.svg");
`;

const CloseButton = styled.button`
  border: none;
  background-color: transparent;
  padding: unset;
`;

interface IProps {
  editorWidth: number,
  endTutorial: (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void
}

const Tutorial: React.FC<IProps> = ({editorWidth, endTutorial}) => {
  const [count, setCount] = useState<number>(0);

  const [explanationLeft, setExplanationLeft] = useState<number>(editorWidth);
  const [explanationTop, setExplanationTop] = useState<number>(MENU_HEIGHT * 2);

  const [windowWidth, setWindowWidth] = useState<number>(window.innerWidth);
  const [windowHeight, setWindowHeight] = useState<number>(window.innerHeight);

  const [topWidth, setTopWidth] = useState<number>(editorWidth);
  const [rightWidth, setRightWidth] = useState<number>(windowWidth - editorWidth);
  const [leftWidth, setLeftWidth] = useState<number>(0);
  const [leftHeight, setLeftHeight] = useState<number>(windowHeight - MENU_HEIGHT);

  useEffect(() => {
    setDropback();
  }, [count, windowWidth, windowHeight, editorWidth]);

  useEffect(() => {
    setWindowWidth(window.innerWidth);
    setWindowHeight(window.innerHeight);
    setLeftHeight(windowHeight - MENU_HEIGHT);
    setDropback();
  }, [editorWidth]);

  const setDropback = useCallback(() => {
    switch (count) {
      case 0:
        setTopWidth(editorWidth);
        setRightWidth(windowWidth - editorWidth);
        setLeftWidth(0);
        setExplanationLeft(editorWidth * 0.7);
        setExplanationTop(MENU_HEIGHT * 2);
        return;
      case 1:
        setTopWidth(editorWidth);
        setRightWidth(0);
        setLeftWidth(editorWidth);
        setExplanationLeft(editorWidth - 300);
        setExplanationTop(leftHeight * 0.2);
        return;
      case 2:
        setTopWidth(0);
        setRightWidth(windowWidth - editorWidth);
        setLeftWidth(editorWidth);
        setExplanationLeft(editorWidth * 0.7);
        setExplanationTop(MENU_HEIGHT);
        return;
      default:
        return;
    }
  }, [count, editorWidth, topWidth, rightWidth, leftHeight, windowWidth]);

  const prev = useCallback(() => {
    setCount(count - 1);
  }, [count]);

  const next = useCallback(() => {
    setCount(count + 1);
  }, [count]);

  return (
    <TutorialLayout>
      <div className="dropback top" style={{width: topWidth}}/>
      <div className="dropback right" style={{width: rightWidth}}/>
      <div className="dropback left" style={{width: leftWidth, height: leftHeight}}/>
      <ExplanationBox style={{left: explanationLeft, top: explanationTop}}>
        <Title>
          <div>This is Title</div>
          {count > 0 && <LeftButton onClick={prev}/>}
          {count < 2 && <RightButton onClick={next}/>}
        </Title>
        <div>이건 에디터고 여기서 편집을 할 수 있으며 기본으로 제공되는 양식은 발표의 첫 페이지이며 없애면 이상해져요. 내용 생각해야하는데 생각하기 싫다.</div>
        <CloseButton onClick={endTutorial}>close</CloseButton>
      </ExplanationBox>
    </TutorialLayout>
  );
};
export default Tutorial;
