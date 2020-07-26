import React from "react";
import Youtube from "../extensions/Youtube";

interface IProps {
  language: string | undefined,
  value: string
}

const Code: React.FC<IProps> = ({ language, value }) => {
  if (language === "youtube") {
    return <Youtube code={value} />;
  }

  return (
    <pre>
      <code>{value}</code>
    </pre>
  );
};

export default Code;
