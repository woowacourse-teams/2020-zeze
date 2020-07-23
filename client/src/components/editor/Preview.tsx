import React from "react";
import * as S from "./assets";
import FullScreenMode from "../common/FullScreenMode";

interface IProps {
  contents: string[]
}

const Preview: React.FC<IProps> = ({contents}) => (
  <>
    {contents.map(content => (
      <S.PreviewSlide>
        <div dangerouslySetInnerHTML={{__html: content}}/>
      </S.PreviewSlide>
    ))}
    <FullScreenMode contents={contents}/>
  </>
);

export default Preview;
