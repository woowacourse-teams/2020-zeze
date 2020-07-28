import React, { useState } from "react";
import ReactMarkdown from "react-markdown";
import { Code, Image } from "./renderers";
import parseMetadata from "../../../utils/markdown/metadata";
import { Metadata } from "./extensions";

interface IProps {
  value?: string
}

const Markdown: React.FC<IProps> = ({ value }) => {
  const { metadata, content } = parseMetadata(value);

  return (
    <div id="themed">
      {metadata && <Metadata {...metadata} />}
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
