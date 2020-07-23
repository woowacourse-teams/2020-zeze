import React, {useState} from "react";
import Preview from "./Preview";
import {sampleYoutubeMarkdown} from "../../utils/markdown/fixtures";

const EditMode: React.FC = () => {
  const [text, setText] = useState<string>(sampleYoutubeMarkdown);

  return (
    <div>
      <Preview content={text}/>
    </div>
  );
};

export default EditMode;
