import React from "react";
import { MetaProps } from "../../../../utils/markdown/metadata";

const Metadata: React.FC<MetaProps> = ({ title, subtitle, author, created_at }) => {
    return (
        <div>
            <h1>{title}</h1>
            <h2>{subtitle}</h2>
            <h3>{author}</h3>
            <h4>{created_at}</h4>
            <hr />
        </div>
    );
};

export default Metadata;
