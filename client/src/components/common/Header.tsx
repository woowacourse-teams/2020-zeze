import React from "react";
import * as S from "./assets";

const Header: React.FC = () => (
  <S.Header>
    <nav>
      <div><S.HeaderIcon src={S.menu} alt="menu"/></div>
      <div><S.LogoIcon src={S.logo} alt="logo"/></div>
      <div><S.HeaderIcon src={S.user} alt="user"/></div>
    </nav>
  </S.Header>
);

export default Header;
