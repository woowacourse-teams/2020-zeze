import styled from "@emotion/styled";
import {MAX_WIDTH, MOBILE_MAX_WIDTH, PRIMARY_GREEN} from "../../../domains/constants";
import logo from "./logo.svg";
import menu from "./menu.svg";
import user from "./user.svg";

export {
  logo,
  menu,
  user,
};

export const Layout = styled.main`
  max-width: ${MAX_WIDTH}px;
  margin: 0 auto;
`;

export const Header = styled.header`
  position: sticky;
  height: 70px;
  background-color: ${PRIMARY_GREEN};
  border-bottom: 0.5px solid ${PRIMARY_GREEN};
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    height: 50px;
  }
  
  nav {
    height: 100%;
    //max-width: ${MAX_WIDTH + 25}px;
    box-sizing: border-box;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
`;

export const LogoIcon = styled.img`
  cursor: pointer;
  width: 30px;
  padding: 20px;
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    padding: 10px;
  }
`;

export const HeaderIcon = styled.img`
  cursor: pointer;
  width: 20px;
  padding: 20px 15px;
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    padding: 15px;
  }
`;

export const Footer = styled.footer`
  padding: 40px 15px;
  background-color: ${PRIMARY_GREEN};
  
  > div {
    max-width: ${MAX_WIDTH}px;
    margin: 0 auto;
    text-align: center;
    font-weight: 600;
    color: #fff;
  }
`;
