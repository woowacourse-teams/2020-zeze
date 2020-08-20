import React from "react";
import styled from "@emotion/styled";
import {PRIMARY_GREEN} from "../../domains/constants";

interface IProps {
  at: string
}

const LastModifiedBlock = styled.div`
  text-align: right;
  color: ${PRIMARY_GREEN};
  font-size: 0.815rem;
`;

const LastModified: React.FC<IProps> = ({at}) => (
  <LastModifiedBlock>Modified at <br/>{at}</LastModifiedBlock>
);

export default LastModified;
