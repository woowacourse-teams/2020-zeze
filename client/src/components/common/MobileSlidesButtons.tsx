import React from "react";
import styled from "@emotion/styled";
import Next from "../../assets/icons/remote/next.svg";
import Back from "../../assets/icons/remote/back.svg";
import Exit from "../../assets/icons/remote/exit.svg";
import {MOBILE_MAX_WIDTH} from "../../domains/constants";

interface ButtonsProps {
  visible: boolean;
}

const Buttons = styled.div<ButtonsProps>`
  display: none;
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    display: ${({visible}) => (visible ? "flex" : "none")};
    position: absolute;
    width: 100%;
    bottom: 5%;
    z-index: 9999;
    justify-content: flex-end;
    align-items: center;
    padding: 0 30px;
    box-sizing: border-box;
    
    > div.button {
      margin: 0 20px;
      
      &:first-of-type {
        margin-left: 0;
        margin-right: auto;
      }
      &:last-of-type {
        margin-right: 0;
      }
      
      > img {
        opacity: 30%;
        width: 30px;
        height: 30px;
      }
    }
  }
`;

interface IProps {
  visible: boolean;
  next: () => void;
  prev: () => void;
  exit: () => void;
}

const MobileSlidesButtons: React.FC<IProps> = ({visible, next, prev, exit}) => (
  <Buttons visible={visible}>
    <div className="button" onClick={exit}><img src={Exit} alt="Exit"/></div>
    <div className="button" onClick={prev}><img src={Back} alt="Back"/></div>
    <div className="button" onClick={next}><img src={Next} alt="next"/></div>
  </Buttons>
);

export default MobileSlidesButtons;
