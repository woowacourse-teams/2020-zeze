import React, {useEffect, useState} from "react";
import Preview from "./Preview";
import Editor from "../common/editor";
import {sampleYoutubeMarkdown} from "../../utils/markdown/fixtures";
import FullScreenMode from "../common/FullScreenMode";

const EditMode: React.FC = () => {
  const [text, setText] = useState<string>(sampleYoutubeMarkdown);
  const [contents, setContents] = useState<string[]>(text.split("---"));

  useEffect(() => {
    setContents(text.split("---").filter(content => content.trim().length !== 0));
  }, [text]);

  const uploadFile = (file: File) => new Promise<string>(resolve => {
    setTimeout(() => {
      resolve(`http://localhost/${file.name}`);
    }, 3000);
  });

  return (
    <>
      <div>
        <Editor defaultValue={text} onChange={setText} onDrop={uploadFile}/>
        <FullScreenMode contents={contents}/>
      </div>
      <Preview contents={contents}/>
    </>
  );
};

export default EditMode;
