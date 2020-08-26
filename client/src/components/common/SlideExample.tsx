import React from "react";
import {Theme} from "../theme";
import Markdown from "../markdown";
import {FullScreenBlock} from "./FullScreenMode";

interface IProps {
  content: string
}

const SlideExample: React.FC<IProps> = ({content}) => (
  <FullScreenBlock mobileVisible={true} style={{
    position: "unset",
    top: 0,
    left: 0,
    cursor: "auto",
  }} slideTheme={Theme.GITHUB}>
    <Markdown value={content}/>
  </FullScreenBlock>
);

export default SlideExample;
