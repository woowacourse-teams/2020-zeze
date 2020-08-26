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
  
  p.images {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    align-items: center;
    flex: 1;
    width: 100%;
    height: 250px;
    
    .image {
      display: block;
      background-repeat: no-repeat;
      background-size: contain;
      background-position: center;
      height: 100%;
      flex: 1;
    }
  }
  
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
