import React, {useState} from "react";
import Preview from "./Preview";
import Editor from "../common/editor";
import {sampleYoutubeMarkdown} from "../../utils/markdown/fixtures";

const EditMode: React.FC = () => {
  const [text, setText] = useState<string>(sampleYoutubeMarkdown);

  const uploadFile = (file: File) => new Promise<string>(resolve => {
    setTimeout(() => {
      resolve(`http://localhost/${file.name}`);
    }, 3000);
  });

  return (
    <div>
      <Editor onChange={setText} onDrop={uploadFile}/>
      <Preview content={text}/>
    </div>
  );
};

export default EditMode;
