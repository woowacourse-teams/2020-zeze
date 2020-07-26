import React, { useState } from "react";
import Preview from "./Preview";
import Editor from "../common/editor";

const EditMode: React.FC = () => {
  const [text, setText] = useState<string>("");

  return (
    <div>
      <Editor onChange={setText} />
      <Preview content={text} />
    </div>
  );
};

export default EditMode;
