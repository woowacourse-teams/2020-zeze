import React from "react";

interface IProps {
  href: string;
}

const Link: React.FC<IProps> = ({href, children}) => (
  <a href={href} target="_blank" rel="noopener noreferrer">{children}</a>
);

export default Link;
