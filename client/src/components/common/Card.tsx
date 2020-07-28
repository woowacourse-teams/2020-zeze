import React from "react";
import * as S from "./assets";
import {Link} from "react-router-dom";

interface IProps {
  id: number,
  title: string,
  subtitle: string,
  author: string,
  createdAt: string
}

const Card: React.FC<IProps> = ({id, title, subtitle, author, createdAt}) => (
  <Link to={`/editor/${id}`}>
    <S.Card>
      <header/>
      <main>
        <div className="title">{title}</div>
        <div className="subtitle">{subtitle}</div>
        <div className="author">{author}</div>
        <div className="created_at">{createdAt}</div>
      </main>
    </S.Card>
  </Link>
);

export default Card;
