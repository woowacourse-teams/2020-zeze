import React from "react";
import * as S from "./assets";

interface IProps {
  src?: string;
  title: string;
}

const SidebarHeader: React.FC<IProps> = ({src, title}) => (
  <S.SidebarHeader>
    <div>
      <img src={src || S.user} alt={title}/><span>{title}</span>
    </div>
    <button>
      <img src={S.more} alt="more"/>
    </button>
  </S.SidebarHeader>
);

export default SidebarHeader;
