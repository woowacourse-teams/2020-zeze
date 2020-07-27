import styled from "@emotion/styled";
import {MAX_WIDTH, MOBILE_MAX_WIDTH, PRIMARY_GREEN} from "../../../domains/constants";
import Markdown from "./markdown.png";

export {
  Markdown,
};

export const Home = styled.div`
  background-color: ${PRIMARY_GREEN};
`;

export const Layout = styled.main`
  padding: 15px;
  max-width: ${MAX_WIDTH}px;
  margin: 0 auto;
`;

export const Slogan = styled.h2`
  margin: 0;
  color: #fff;
  font-size: 6rem;
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    font-size: 2.5rem;
  }
`;

interface SectionProps {
  background: string;
}

export const Section = styled("div")<SectionProps>`
  background-color: ${({background}: SectionProps) => background};
  padding: 60px 15px;
  margin: 0 auto;
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    padding: 30px 15px;
  }
`;

export const Button = styled.div`
  display: inline-block;
  border: 2px solid white;
  border-radius: 50px;
  padding: 1rem 1.5rem;
  margin: 2rem 0 1.5rem;
  color: #fff;
  font-weight: 600;
  font-size: 1.4rem;
  
  &:hover {
    cursor: pointer;
  }
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    margin: 1rem 0 0.75rem;
    font-size: 1.1rem;
  }
`;
