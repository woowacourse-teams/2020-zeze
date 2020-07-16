import {css, SerializedStyles} from "@emotion/core";
import styled from "@emotion/styled";

// sample code for styling

enum Mode {
  DARK,
  LIGHT
}

export const backgroundColor = (mode: Mode): SerializedStyles => css`
  background-color: ${mode === Mode.DARK ? "#000" : "#FFF"}
`;

export const Title = styled.h1`
  ${backgroundColor(Mode.DARK)};
  color: red;
`;
