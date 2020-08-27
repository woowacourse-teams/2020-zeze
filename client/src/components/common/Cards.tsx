import React from "react";
import styled from "@emotion/styled";
import Card from "./Card";
import {MOBILE_MAX_WIDTH} from "../../domains/constants";
import {MetaDataResponse} from "../../api/slide";
import {Link} from "react-router-dom";
import Logo from "../../assets/logo192.png";

export const CardsLayout = styled.div`
  padding-bottom: 2rem;

  > h2 {
    color: #fff;
    margin: 30px 0 0;
    display: inline-block;
    font-size: 2rem;
  }
  
  > hr {
    border-bottom: 1px solid #777;
    width: 7rem;
    margin: 1rem 0 2rem;
  }
  
  a {
    display: flex;
    flex-direction: column;
    text-decoration-line: none;
    width: 300px;
    border: 1px #777 dashed;
    padding: 50px 0;
    border-radius: 10px;
    
    &:hover {
      opacity: 75%;
    }
    @media (max-width: ${MOBILE_MAX_WIDTH}px) {
      margin: auto;
    }
  }
  
  span {
    display: flex;
    color: #fff;
    margin: 10px auto;
  }
  
  img {
    display: flex;
    width: 40px;
    margin: auto;
  }
`;

const CardsBlock = styled.div`
  display: grid;
  grid-gap: 20px;
  grid-template-columns: repeat(4, 1fr);
  
  a {
    text-decoration: none;
    color: #000;
    width: auto;
    border: none;
    padding: 0;
    margin: 0;
    
    &:hover {
      opacity: 100%;
    }
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
  slides: MetaDataResponse[],
  author?: string,
  onClone?: (id: number) => void,
  onDelete?: (id: number) => void,
}

const Cards: React.FC<IProps> = ({title, slides, onClone, onDelete}) => (
  <CardsLayout>
    <h2>{title}</h2>
    <hr/>
    {slides.length === 0 ?
      <Link to="/editor">
        <img src={Logo} alt={"NEW SLIDES"}/><span>CREATE NEW SLIDES!</span>
      </Link> :
      <CardsBlock>
        {slides.map(slide => (
          <Card key={slide.id} id={slide.id} title={slide.title} subtitle={slide.subtitle} author={slide.author}
                presentedAt={slide.presentedAt} createdAt={slide.createdAt} onClone={onClone} onDelete={onDelete}/>))}
      </CardsBlock>
    }
  </CardsLayout>
);

export default Cards;
