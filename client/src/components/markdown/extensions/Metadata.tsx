import React from "react";
import {MetaProps} from "../../../utils/metadata";

const Metadata: React.FC<MetaProps> = ({title, subtitle, author, createdAt}) => (
  <div>
    <h1>{title}</h1>
    <h2>{subtitle}</h2>
    <h3>{author}</h3>
    <h4>{createdAt}</h4>
    <hr />
  </div>
);

export default Metadata;
