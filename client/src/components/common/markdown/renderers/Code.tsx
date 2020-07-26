import React from "react";

interface IProps {
  language: string | undefined,
  value: string
}

const Code: React.FC<IProps> = ({ language, value }) => (
  <pre>
    <code>{value}</code>
  </pre>
);

export default Code;
