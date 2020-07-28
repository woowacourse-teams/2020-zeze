import React from "react";

interface IProps {
  src: string;
  alt: string;
}
const Image: React.FC<IProps> = ({src, alt}) => (
  <img src={src} alt={alt}/>
);

export default Image;
