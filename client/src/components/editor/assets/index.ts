import styled from "@emotion/styled";
import {MOBILE_MAX_WIDTH} from "../../../domains/constants";

export const Editor = styled.main`
  display: flex;
  height: 100%;
  
  > div {
    flex: 1;
    position: relative;
  }
  
  // temporary
  textarea {
    resize: none;
    font-size: 1.1rem;
    box-sizing: border-box;
    width: 100%;
    height: 100%;
    padding: 30px;
    background-color: #333;
    color: #fff;
    font-family: monospace;
    border: none;
    
    &:focus {
      outline: none;
    }
  }
  
  @media(max-width: ${MOBILE_MAX_WIDTH}px) {
    flex-direction: column-reverse;
    height: 100vh;
  }
`;

export const Preview = styled.div`
  background-color: #fff;
  flex: 1;
  padding: 30px;
  overflow: auto;
`;

