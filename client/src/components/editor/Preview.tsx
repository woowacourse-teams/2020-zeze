import React from "react";
import marked from "marked";

interface IProps {
  content: string
}

  marked.setOptions({
    renderer: new marked.Renderer(),
  });

const Preview: React.FC<IProps> = ({content}) => (
  <div>
    <div dangerouslySetInnerHTML={{__html: marked(content)}}/>
  </div>
);

export default Preview;
