import React from "react";
import ReactMarkdown from "react-markdown/with-html";
import {Code, Image, Paragraph} from "./renderers";
import {parse} from "../../utils/metadata";

interface IProps {
  value: string
}

const MemoReactMarkdown = React.memo(ReactMarkdown);
const renderers = {
  code: Code,
  image: Image,
  paragraph: Paragraph,
};

const Markdown: React.FC<IProps> = ({value = ""}) => {
  const {content} = parse(value);

  const blocks = content?.split(/(^---$)/m)
    .flatMap(slide => slide.split(/(^```[a-z]*\n[\s\S]*?\n```)/m))
    .map((block, index) => ({
      key: index,
      source: block,
    }));

  return (
    <div id="themed">
      {blocks?.map(({key, source}) => (
        <MemoReactMarkdown
          key={key}
          source={source}
          escapeHtml={false}
          renderers={renderers}/>
      ))}
    </div>
  );
};

export default Markdown;
