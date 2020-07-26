import React from "react";
import Markdown from "../common/markdown";

interface IProps {
  content: string
}

const Preview: React.FC<IProps> = ({content}) => (
  <div>
    <Markdown value={content} />
  </div>
);

export default Preview;
