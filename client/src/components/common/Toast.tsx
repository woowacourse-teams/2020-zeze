import React from "react";
import styled from "@emotion/styled";
import {PRIMARY_GREEN} from "../../domains/constants";

interface ToastProps {
  type: string;
}

const ToastBlock = styled.div<ToastProps>`
  background-color: ${({type}) => (type === "warn" ? "#121212" : "#fff")};
  border-radius: 5px;
  padding: 20px;
  margin: 20px 0 20px;
  color: #fff;
  font-size: 1.25rem;
  font-weight: 600;
  word-spacing: 4px;
  
  ::before {
    content: "⚠️";
    font-family: "Apple Color Emoji", serif;
    margin-right: 15px;
  }
  
  > a {
    text-decoration: underline;
    color: ${PRIMARY_GREEN};
  }
`;

interface IProps {
  type: string;
}

const Toast: React.FC<IProps> = ({type, children}) => (
  <ToastBlock type={type}>{children}</ToastBlock>
);

export default Toast;
