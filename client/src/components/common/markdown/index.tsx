import React from "react";
import ReactMarkdown from "react-markdown";
import { Code } from "./renderers";

interface IProps {
    value?: string
}

const Markdown: React.FC<IProps> = ({ value }) => {
    return <ReactMarkdown source={value} renderers={{
        code: Code
    }} />;
};

export default Markdown