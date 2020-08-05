import React from "react";

const Paragraph: React.FC = ({children}: any) => {
  if (children.some(({props}: any) => props.src)) {
    return <p className="images">{children}</p>;
  }

  return <p>{children}</p>;
};

export default Paragraph;
