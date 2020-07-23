import React from "react";
import marked from "marked";
import * as S from "./assets";

interface IProps {
  content: string
}

marked.setOptions({
  renderer: new marked.Renderer(),
});

const Preview: React.FC<IProps> = ({content}) => (
  <S.Preview>
    <div dangerouslySetInnerHTML={{__html: marked(content)}}/>
  </S.Preview>
);

export default Preview;
