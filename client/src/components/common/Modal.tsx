import React, { useCallback } from "react";
import styled from "@emotion/styled";
import {css, Global} from "@emotion/core";

interface FullScreenProps {
  visibility: boolean
}

const FullScreen = styled.div<FullScreenProps>`
  position: absolute;
  top: 0;
  left: 0;
  z-index: 99999999;
  display: ${({visibility}) => visibility ? "flex" : "none"};
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.3);
  width: 100%;
  height: 100%;
`;

const ModalWindow = styled.div`
  display: flex;
  flex-direction: column;
  background: #fff;
  border-radius: 8px;
  padding: 5px;
  min-width: 200px;
  min-height: 100px;
  box-shadow: 0 0 20px #333;
`;

const GlobalStyles = css`
  body {
      overflow: hidden;
  }
`;

export interface IProps extends FullScreenProps {
  onBackdropClick: () => void,
}

const Modal: React.FC<IProps> = ({children, visibility, onBackdropClick}) => {
  const handleClick = useCallback(({currentTarget, target}: React.MouseEvent) => {
    target === currentTarget && onBackdropClick();
  }, [onBackdropClick]);

  return (
    <FullScreen visibility={visibility} onClick={handleClick}>
        <ModalWindow>
          {children}
        </ModalWindow>
        {visibility && <Global styles={GlobalStyles}/>}
    </FullScreen>
  );
};

export default Modal;
