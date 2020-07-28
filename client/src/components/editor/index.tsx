import React from "react";
import * as S from "./assets";
import SidebarLayout from "../common/SidebarLayout";
import EditMode from "./EditMode";

const Editor: React.FC = () => (
  <SidebarLayout fluid>
    <S.Editor>
      <EditMode/>
    </S.Editor>
  </SidebarLayout>
);

export default Editor;
