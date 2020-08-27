import React from "react";
import styled from "@emotion/styled";
import {MAX_WIDTH, ZEZE_GRAY} from "../../domains/constants";

const FooterBlock = styled.footer`
  padding: 40px 15px;
  background-color: ${ZEZE_GRAY};
  
  > div {
    max-width: ${MAX_WIDTH}px;
    margin: 0 auto;
    text-align: center;
    font-size: 0.75rem;
    color: #fff;
  }
`;


const Footer: React.FC = () => (
  <FooterBlock>
    <div>
      Copyright © 2020 Minguinho.
    </div>
  </FooterBlock>
);

export default Footer;
