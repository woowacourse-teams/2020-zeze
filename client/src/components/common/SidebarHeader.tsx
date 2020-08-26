import React from "react";
import styled from "@emotion/styled";

const SidebarHeaderBlock = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding-bottom: 1rem;
  border-bottom: 2px solid #aaa;
  margin-bottom: 1rem;
  
  > div {
    font-size: 1.4rem;
    display: flex;
    align-items: center;
    flex: 5;
    
    > img {
      width: 1.4rem;
      padding-right: 10px;
    }
  }
  
  > button {
    border: none;
    background: transparent;
    flex: 1;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    
    &:focus {
      outline: none;
    }
    
    > img {
      width: 0.75rem;
    } 
  }
`;

interface IProps {
  src?: string;
  title: string;
}

const SidebarHeader: React.FC<IProps> = ({src, title}) => (
  <SidebarHeaderBlock>
    <div>
      <img src={src || "/assets/icons/user.svg"} alt={title}/><span>{title}</span>
    </div>
    {/* <button>*/}
    {/*  <img src="/assets/icons/more.svg" alt="more"/>*/}
    {/* </button>*/}
  </SidebarHeaderBlock>
);

export default SidebarHeader;
