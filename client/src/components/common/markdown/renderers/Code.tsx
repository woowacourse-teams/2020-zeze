import React from "react";
import Language, {Youtube} from "../extensions";

interface IProps {
  language: string | undefined,
  value: string
}

const Code: React.FC<IProps> = ({language, value}) => {
  switch (language) {
  case Language.YOUTUBE:
    return <Youtube code={value}/>;
  default:
    return (
      <pre>
        <code>{value}</code>
      </pre>
    );
  }
};

export default Code;
