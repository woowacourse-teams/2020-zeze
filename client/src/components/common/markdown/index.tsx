import React from "react";
import ReactMarkdown from "react-markdown";

interface IProps {
  value?: string
}

const Markdown: React.FC<IProps> = ({ value }) => <ReactMarkdown source={value} />;

export default Markdown;
