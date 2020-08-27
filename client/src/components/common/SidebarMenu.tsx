import React from "react";
import styled from "@emotion/styled";

const SidebarMenuBlock = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  padding: 7.5px 0;
  cursor: pointer;
  
  &:hover {
    opacity: 75%;
  }
  
  > img {
    width: 1rem;
    padding-right: 10px;
  }
`;

interface IProps {
  src: string;
  title: string;
}

const SidebarMenu: React.FC<IProps> = ({src, title}) => (
  <SidebarMenuBlock><img src={src} alt={title}/><span>{title}</span></SidebarMenuBlock>
);

export default SidebarMenu;
