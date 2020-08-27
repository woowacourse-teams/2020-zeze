import React, {useEffect} from "react";
import {useHistory} from "react-router-dom";
import styled from "@emotion/styled";

import Logo from "../assets/logo192.png";
import {googleAnalyticsException} from "../utils/googleAnalytics";
import {PRIMARY_GREEN} from "../domains/constants";

const ErrorBlock = styled.div`
    box-sizing: border-box;
    width: 100%;
    height: 90vh;
    padding: 50px;
    color: #fff;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    
    > h1 {
      text-align: center;
    }
    
    > span {
      text-decoration: underline;
      color: ${PRIMARY_GREEN};
      font-size: 1.2rem;
      font-weight: 600;
      cursor: pointer;
    }
  `;

const Error: React.FC = () => {
  const history = useHistory();

  useEffect(() => {
    googleAnalyticsException("404 페이지 찾을 수 없음");
  }, []);

  return (
    <ErrorBlock>
      <img src={Logo} alt="logo"/>
      <h1>Oops... Page Not Found!</h1>
      <span onClick={() => history.goBack()}>Go back</span>
    </ErrorBlock>
  );
};

export default Error;
