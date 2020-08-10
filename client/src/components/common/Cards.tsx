import React from "react";
import styled from "@emotion/styled";
import Card from "./Card";
import {MOBILE_MAX_WIDTH} from "../../domains/constants";
import {SlideResponse} from "../../api/slide";

export const CardsLayout = styled.div`
  padding-bottom: 3rem;

  > h2 {
    color: #fff;
    margin: 0;
    display: inline-block;
    font-size: 2rem;
  }
  
  > hr {
    border-bottom: 1px solid #777;
    width: 7rem;
    margin: 1rem 0 2rem;
  }
`;

const CardsBlock = styled.div`
  display: grid;
  grid-gap: 20px;
  grid-template-columns: repeat(4, 1fr);
  
  a {
    text-decoration: none;
    color: #000;  
  }
  
  @media (max-width: 1440px) {
    grid-template-columns: repeat(3, 1fr);
  }
  
  @media (max-width: 1050px) {
    grid-template-columns: repeat(2, 1fr);
  }
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    grid-template-columns: 1fr;
  }
`;


interface IProps {
  title: string,
  slides: Array<SlideResponse>,
  author: string
}

const Cards: React.FC<IProps> = ({title, slides, author}) => (
  <CardsLayout>
    <h2>{title}</h2>
    <hr/>
    <CardsBlock>
      {slides.map(slide => (<Card key={slide.id} id={slide.id} title={slide.title} subtitle="subtitle" author={author}
        createdAt={slide.createdAt}/>))}
    </CardsBlock>
  </CardsLayout>
);

export default Cards;
