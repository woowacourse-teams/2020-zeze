import React from "react";
import * as S from "./assets";

interface IProps {
  src: string;
  title: string;
}

const SidebarMenu: React.FC<IProps> = ({src, title}) => (
  <S.SidebarMenu><img src={src} alt={title}/><span>{title}</span></S.SidebarMenu>
);

export default SidebarMenu;
