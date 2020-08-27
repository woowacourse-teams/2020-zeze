import React from "react";
import styled from "@emotion/styled";
import {PRIMARY_GREEN} from "../../domains/constants";

interface IProps {
  at: string
}

interface StyleProps {
  color?: string;
}

const LastModifiedBlock = styled.div<StyleProps>`
  text-align: right;
  color: ${props => props.color || PRIMARY_GREEN};
  font-size: 0.815rem;
  min-width: 100px;
`;

const LastModified: React.FC<IProps> = ({at}) => (at.length === 0 ?
  <LastModifiedBlock color="#F66566">Unsaved<br/>Ctrl / Cmd + S to save</LastModifiedBlock> :
  <LastModifiedBlock>Last modified:<br/>{at}</LastModifiedBlock>);

export default LastModified;
