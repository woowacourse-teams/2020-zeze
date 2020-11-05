import React from "react";
import styled from "@emotion/styled";

interface IProps {
  handleClick: (template: string) => void;
  title: string;
  src: string;
  template: string;
  className?: string;
}

const EditorButtonBlock = styled.button`
  display: flex;
  height: 35px;
  min-width: 55px;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  outline: none;
  border: none;
  color: #777;
  font-weight: bold;
  background-color: transparent;
  cursor: pointer;
  opacity: 0.6;
  
  > img {
    width: 13px;
  }
  
  > span {
    color: #fff;
    font-size: 0.75rem;
    font-weight: 500;
  }
  
  &:hover {
    opacity: 1;
  }
  
  &.active {
    opacity: 1;
   }
`;

const EditorButton: React.FC<IProps> = ({title, src, template, handleClick, className}) => (
  <EditorButtonBlock onClick={() => handleClick(template)} className={className}>
    <img src={src} alt={src}/>
    <span>{title}</span>
  </EditorButtonBlock>
);

export default React.memo(EditorButton);
