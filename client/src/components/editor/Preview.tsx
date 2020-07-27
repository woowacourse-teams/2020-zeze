import React from "react";
import * as S from "./assets";

interface IProps {
  contents?: string[]
}

const Preview: React.FC<IProps> = ({contents}) => (
  <S.Preview>
    {contents?.map(content => (
      <S.PreviewSlide>
        <div dangerouslySetInnerHTML={{__html: content}}/>
      </S.PreviewSlide>
    ))}
  </S.Preview>
);

export default Preview;
