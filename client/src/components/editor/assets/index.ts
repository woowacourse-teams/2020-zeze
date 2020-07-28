import styled from "@emotion/styled";
import {MOBILE_MAX_WIDTH} from "../../../domains/constants";

export const Editor = styled.main`
  display: flex;
  height: 100%;
  
  > div {
    flex: 1;
    position: relative;
    overflow: auto;
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

