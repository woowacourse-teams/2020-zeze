import React from "react";
import Card from "./Card";
import * as S from "./assets";

interface IProps {
  title: string;
}

const Cards: React.FC<IProps> = ({title}) => (
  <S.CardsLayout>
    <h2>{title}</h2>
    <hr/>
    <S.Cards>
      <Card id={1} title="Hello World!" subtitle="Introducing Limetree" author="Hodol" createdAt="2020-07-12"/>
      <Card id={1} title="Hello World!" subtitle="Introducing Limetree" author="Hodol" createdAt="2020-07-12"/>
      <Card id={1} title="Hello World!" subtitle="Introducing Limetree" author="Hodol" createdAt="2020-07-12"/>
      <Card id={1} title="Hello World!" subtitle="Introducing Limetree" author="Hodol" createdAt="2020-07-12"/>
      <Card id={1} title="Hello World!" subtitle="Introducing Limetree" author="Hodol" createdAt="2020-07-12"/>
      <Card id={1} title="Hello World!" subtitle="Introducing Limetree" author="Hodol" createdAt="2020-07-12"/>
      <Card id={1} title="Hello World!" subtitle="Introducing Limetree" author="Hodol" createdAt="2020-07-12"/>
    </S.Cards>
  </S.CardsLayout>
);

export default Cards;
