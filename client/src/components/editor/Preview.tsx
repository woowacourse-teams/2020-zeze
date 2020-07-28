import React from "react";
import * as S from "./assets";
import Markdown from "../common/markdown";

interface IProps {
  content: string
}

const Preview: React.FC<IProps> = ({content}) => (
  <S.Preview>
    <Markdown value={content}/>
  </S.Preview>
);

export default Preview;
