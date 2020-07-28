import React from "react";
import * as S from "./assets";

interface IProps {
  type: string;
  message: string;
}

const Toast: React.FC<IProps> = ({type, message}) => (
  <S.Toast type={type}>{message}</S.Toast>
);

export default Toast;
