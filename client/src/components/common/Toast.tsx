import React from "react";
import styled from "@emotion/styled";

interface ToastProps {
  type: string;
}

const ToastBlock = styled.div<ToastProps>`
  background-color: ${({type}) => (type === "warn" ? "#121212" : "#fff")};
  border-radius: 5px;
  padding: 20px;
  margin: 0 0 20px;
  color: #fff;
  font-weight: 600;
  
  ::before {
    content: "⚠️";
    font-family: "Apple Color Emoji", serif;
    margin-right: 15px;
  }
`;

interface IProps {
  type: string;
  message: string;
}

const Toast: React.FC<IProps> = ({type, message}) => (
  <ToastBlock type={type}>{message}</ToastBlock>
);

export default Toast;
