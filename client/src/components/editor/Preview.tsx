import React from "react";
import marked from "marked";
import Renderer from "../../utils/markdown/renderer;"
import youtube from "../../utils/markdown/replacers/youtube;"

interface IProps {
  content: string
}

const renderer = new Renderer().setCodeReplacer("youtube", youtube);

marked.setOptions({
  renderer,
});

const Preview: React.FC<IProps> = ({content}) => (
  <div>
    <div dangerouslySetInnerHTML={{__html: marked(content)}}/>
  </div>
);

export default Preview;
