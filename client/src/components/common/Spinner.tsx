import React from "react";
import styled from "@emotion/styled";
import {PRIMARY_GREEN} from "../../domains/constants";

const SpinnerBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: ${PRIMARY_GREEN};
  
  .loader,
  .loader:before,
  .loader:after {
    border-radius: 50%;
  }
  .loader {
    color: #ffffff;
    font-size: 11px;
    text-indent: -99999em;
    margin: 55px auto;
    position: relative;
    width: 10em;
    height: 10em;
    box-shadow: inset 0 0 0 1em;
    -webkit-transform: translateZ(0);
    -ms-transform: translateZ(0);
    transform: translateZ(0);
  }
  .loader:before,
  .loader:after {
    position: absolute;
    content: '';
  }
  .loader:before {
    width: 5.2em;
    height: 10.2em;
    background: ${PRIMARY_GREEN};
    border-radius: 10.2em 0 0 10.2em;
    top: -0.1em;
    left: -0.1em;
    -webkit-transform-origin: 5.1em 5.1em;
    transform-origin: 5.1em 5.1em;
    -webkit-animation: load2 2s infinite ease 1.5s;
    animation: load2 2s infinite ease 1.5s;
  }
  .loader:after {
    width: 5.2em;
    height: 10.2em;
    background: ${PRIMARY_GREEN};
    border-radius: 0 10.2em 10.2em 0;
    top: -0.1em;
    left: 4.9em;
    -webkit-transform-origin: 0.1em 5.1em;
    transform-origin: 0.1em 5.1em;
    -webkit-animation: load2 2s infinite ease;
    animation: load2 2s infinite ease;
  }
  @-webkit-keyframes load2 {
    0% {
      -webkit-transform: rotate(0deg);
      transform: rotate(0deg);
    }
    100% {
      -webkit-transform: rotate(360deg);
      transform: rotate(360deg);
    }
  }
  @keyframes load2 {
    0% {
      -webkit-transform: rotate(0deg);
      transform: rotate(0deg);
    }
    100% {
      -webkit-transform: rotate(360deg);
      transform: rotate(360deg);
    }
  }
`;

const Spinner: React.FC = () => (
  <SpinnerBox>
    <div className="loader">Loading...</div>
  </SpinnerBox>
);

export default Spinner;
