import React, {useState} from "react";
import Preview from "./Preview";

const EditMode: React.FC = () => {
  const [text, setText] = useState<string>("");

  return (
    <div>
      <Preview content={text}/>
    </div>
  );
};

export default EditMode;
