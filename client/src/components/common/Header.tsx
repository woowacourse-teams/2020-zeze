import React from "react";
import styled from "@emotion/styled";
import {MOBILE_MAX_WIDTH, ZEZE_GRAY} from "../../domains/constants";

const HeaderBlock = styled.header`
  position: sticky;
  height: 70px;
  background-color: ${ZEZE_GRAY};
  border-bottom: 0.5px solid ${ZEZE_GRAY};
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    height: 50px;
  }
  
  nav {
    height: 100%;
    box-sizing: border-box;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;

const LogoIcon = styled.img`
  cursor: pointer;
  width: 30px;
  padding: 20px;
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    padding: 10px;
  }
`;


const Header: React.FC = () => (
  <HeaderBlock>
    <nav>
      <div><LogoIcon src="/assets/icons/logo.svg" alt="logo" /></div>
    </nav>
  </HeaderBlock>
);

export default Header;
