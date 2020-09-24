import React, {useCallback, useEffect, useState} from "react";
import styled from "@emotion/styled";

import {MENU_HEIGHT, TUTORIAL_CONTENTS, TUTORIAL_TITLE} from "../../domains/constants";

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

interface Window {
  windowWidth: number,
  windowHeight: number
}

interface Explanation {
  title: string
  explanationLeft: number,
  explanationTop: number,
  explanation: string
}

interface DropbackSize {
  topWidth: number,
  rightWidth: number,
  leftWidth: number,
  leftHeight: number
}

const Tutorial: React.FC<IProps> = ({editorWidth, endTutorial}) => {
  const [count, setCount] = useState<number>(0);

  const [windowSize, setWindowSize] = useState<Window>({
    windowWidth: window.innerWidth,
    windowHeight: window.innerHeight
  });

  const [explanation, setExplanation] = useState<Explanation>({
    title: TUTORIAL_TITLE.EDITOR,
    explanationLeft: editorWidth,
    explanationTop: MENU_HEIGHT * 2,
    explanation: TUTORIAL_CONTENTS.EDITOR
  });

  const [dropbackSize, setDropbackSize] = useState<DropbackSize>({
    topWidth: editorWidth,
    rightWidth: windowSize.windowWidth - editorWidth,
    leftWidth: 0,
    leftHeight: windowSize.windowHeight - MENU_HEIGHT
  });

  useEffect(() => {
    setDropback();
  }, [count, windowSize, editorWidth]);

  useEffect(() => {
    setWindowSize({
      windowWidth: window.innerWidth,
      windowHeight: window.innerHeight
    });
    setDropbackSize({...dropbackSize, leftHeight: windowSize.windowHeight - MENU_HEIGHT});
    setDropback();
  }, [editorWidth]);

  const setDropback = useCallback(() => {
    switch (count) {
      case 0:
        setDropbackSize({
          ...dropbackSize,
          rightWidth: windowSize.windowWidth - editorWidth,
          leftWidth: 0
        });
        setExplanation({
          ...explanation,
          title: TUTORIAL_TITLE.EDITOR,
          explanationLeft: editorWidth * 0.7,
          explanationTop: MENU_HEIGHT * 2,
          explanation: TUTORIAL_CONTENTS.EDITOR
        });
        return;
      case 1:
        setDropbackSize({
          ...dropbackSize,
          topWidth: editorWidth,
          rightWidth: 0,
          leftWidth: editorWidth
        });
        setExplanation({
          ...explanation,
          title: TUTORIAL_TITLE.PREVIEW,
          explanationLeft: editorWidth - 300,
          explanationTop: dropbackSize.leftHeight * 0.2,
          explanation: TUTORIAL_CONTENTS.PREVIEW
        });
        return;
      case 2:
        setDropbackSize({
          ...dropbackSize,
          topWidth: 0,
          rightWidth: windowSize.windowWidth - editorWidth,
        });
        setExplanation({
          ...explanation,
          title: TUTORIAL_TITLE.MENU,
          explanationLeft: editorWidth * 0.7,
          explanationTop: MENU_HEIGHT,
          explanation: TUTORIAL_CONTENTS.MENU
        });
        return;
      default:
        return;
    }
  }, [count, editorWidth, dropbackSize, windowSize]);

  const prev = useCallback(() => {
    setCount(count - 1);
  }, [count]);

  const next = useCallback(() => {
    setCount(count + 1);
  }, [count]);

  return (
    <TutorialLayout>
      <div className="dropback top" style={{width: dropbackSize.topWidth}}/>
      <div className="dropback right" style={{width: dropbackSize.rightWidth}}/>
      <div className="dropback left" style={{width: dropbackSize.leftWidth, height: dropbackSize.leftHeight}}/>
      <ExplanationBox style={{left: explanation.explanationLeft, top: explanation.explanationTop}}>
        <Title>
          <div>{explanation.title}</div>
          {count > 0 && <LeftButton onClick={prev}/>}
          {count < 2 && <RightButton onClick={next}/>}
        </Title>
        <div>{explanation.explanation}</div>
        <CloseButton onClick={endTutorial}>close</CloseButton>
      </ExplanationBox>
    </TutorialLayout>
  );
};
export default Tutorial;
