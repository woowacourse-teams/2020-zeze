import styled from "@emotion/styled";
import {MOBILE_MAX_WIDTH} from "../../../domains/constants";

export const Editor = styled.main`
  display: flex;
  height: calc(100vh - 70px);
  
  
  @media(max-width: ${MOBILE_MAX_WIDTH}px) {
    flex-direction: column-reverse;
    height: calc(100vh - 50px);
  }
`;

export const Preview = styled.div`
  flex: 1;
  padding: 30px;
`;
