import React from "react"
import ReactMarkdown from "react-markdown"

interface IProps {
    value?: string
}

const Markdown: React.FC<IProps> = ({ value }) => {
    return (<ReactMarkdown source={value} />);
};

export default Markdown