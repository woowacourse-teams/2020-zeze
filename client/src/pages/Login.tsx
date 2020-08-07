import React from "react";
import GlobalLayout from "../components/common/GlobalLayout";
import styled from "@emotion/styled";
import {githubLogo} from "../assets/icons/index"
import {MOBILE_MAX_WIDTH} from "../domains/constants";

const GithubButton = styled.div`
  display: inline-block;
  justify-content: center;
  border: 2px solid white;
  border-radius: 50px;
  padding: 1rem 1.5rem;
  margin: 2rem 0 1.5rem;
  color: #fff;
  font-weight: 600;
  font-size: 1.4rem;
  background-size: contain;
  background-image: url(${githubLogo});
  
  &:hover {
    cursor: pointer;
  }
  
  @media(max-width: ${MOBILE_MAX_WIDTH}px) {
    flex-direction: column-reverse;
    height: 100vh;
  }
`;

const Login: React.FC = () => {
  const baseUrl: string = "https://github.com/login/oauth/authorize";
  const callbackUrl: string = "http://localhost:3000/callback";
  const clientId: string = "c47a3779bada189b7721";

  return (
    <GlobalLayout>
      <a href={baseUrl + "?client_id=" + clientId + "&redirect_uri=" + callbackUrl}>
        <GithubButton>login with github</GithubButton>
      </a>
    </GlobalLayout>
  )
};
export default Login;
