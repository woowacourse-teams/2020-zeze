import React from "react";
import { Prism as SyntaxHighlighter } from "react-syntax-highlighter";
import { darcula } from "react-syntax-highlighter/dist/esm/styles/prism";
import Language, { Youtube } from "../extensions";

interface IProps {
  language: string | undefined,
  value: string
}

const Code: React.FC<IProps> = ({ language, value = "" }) => {
  switch (language) {
    case Language.YOUTUBE:
      return <Youtube code={value} />;
    default:
      return (
        <SyntaxHighlighter language={language} style={darcula}>{value}</SyntaxHighlighter>
      );
  }
};

export default Code;
