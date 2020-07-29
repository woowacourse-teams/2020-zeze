import React from "react";
import ReactMarkdown from "react-markdown";
import {Code, Image} from "./renderers";
import parseMetadata from "../../../utils/markdown/metadata";

interface IProps {
  value?: string
}

const Markdown: React.FC<IProps> = ({value}) => {
  const {content} = parseMetadata(value);

  return (
    <div id="themed">
      <ReactMarkdown
        source={content}
        renderers={{
          code: Code,
          image: Image,
        }} />
    </div>
  );
};

export default Markdown;
