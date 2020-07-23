import React from "react";
import marked from "marked";
import * as S from "./assets";
import Renderer from "../../utils/markdown/renderer";
import youtube from "../../utils/markdown/replacers/youtube";

interface IProps {
  content: string
}

const renderer = new Renderer().setCodeReplacer("youtube", youtube);

marked.setOptions({
  renderer,
});

const Preview: React.FC<IProps> = ({content}) => (
  <S.Preview>
    <div dangerouslySetInnerHTML={{__html: marked(content)}}/>
  </S.Preview>
);

export default Preview;
