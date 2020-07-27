import React from "react";
import * as S from "./assets";
import Markdown from "../common/markdown";

interface IProps {
  contents?: string[]
}

const Preview: React.FC<IProps> = ({contents}) => (
  <S.Preview>
    {contents?.map(content => (
      // TODO: Should be fixed with real keys
      <S.PreviewSlide key={Math.random()}>
        <Markdown value={content}/>
      </S.PreviewSlide>
    ))}
  </S.Preview>
);

export default Preview;
