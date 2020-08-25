import React from "react";
import styled from "@emotion/styled";
import Markdown from "../markdown";
import github from "../theme/github";
import {MOBILE_MAX_WIDTH} from "../../domains/constants";

interface IProps {
  content: string
}

const PreviewBlock = styled.div`
  background-color: #fff;
  flex: 1;
  padding: 30px;
  overflow: scroll;
  max-height: 100vh;
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    display: none;
  }
  
  > div#themed {
    ${github}
  }
`;

const Preview: React.FC<IProps> = ({content}) => (
  <PreviewBlock>
    <Markdown value={content}/>
  </PreviewBlock>
);

export default Preview;
