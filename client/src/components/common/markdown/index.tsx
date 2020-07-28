import React from "react";
import ReactMarkdown from "react-markdown";
import {Code, Image} from "./renderers";

interface IProps {
  value?: string
}

const Markdown: React.FC<IProps> = ({value}) => (
  <div id="themed">
    <ReactMarkdown
      source={value}
      renderers={{
        code: Code,
        // image: Image,
      }}/>
  </div>
);

export default Markdown;
