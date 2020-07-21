import React, {useState} from "react";
import marked from "marked";

const Preview: React.FC = () => {
  const [preview, setPreview] = useState<string>("");

  marked.setOptions({
    renderer: new marked.Renderer(),
  });

  const changeToHTML = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    const content: string = event.target.value;

    setPreview(marked(content));
  };

  return (
    <div>
      <div dangerouslySetInnerHTML={{__html: preview}}/>
      <textarea placeholder="여기에 마크다운으로 내용을 작성해주세요." onChange={event => changeToHTML(event)}/>
    </div>
  );
};

export default Preview;
