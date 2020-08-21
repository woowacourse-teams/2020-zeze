import React, {useCallback} from "react";
import styled from "@emotion/styled";
import {useSetRecoilState} from "recoil";
import CodeMirror from "codemirror";

import EditorButton from "./EditorButton";
import menu from "../../assets/icons/hamburger.svg";
import LastModified from "./LastModified";
import {sidebarVisibility} from "../../store/atoms";
import templates from "../../domains/templates";


const Menu = styled.div`
  padding: 5px;
  cursor: pointer;
  margin-right: 15px;
  > img {
    width: 20px;
  }
`;

const EditorButtonsBlock = styled.div`
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background-color: #313335;
  
  > div.container {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    overflow-x: scroll;
  }
`;

interface IProps {
  inputRef: React.MutableRefObject<CodeMirror.Editor | null>
}

const EditorButtons: React.FC<IProps> = ({inputRef}) => {
  const setVisibility = useSetRecoilState(sidebarVisibility);
  const insertTemplate = useCallback((template: string) => {
    const editor = inputRef.current!;
    const cursor = editor.getCursor();

    editor.replaceRange(template, cursor);
    const searchCursor = editor.getSearchCursor(template, cursor);

    editor.focus();
    searchCursor.findNext() && editor.setCursor(searchCursor.to());
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <EditorButtonsBlock>
      <Menu onClick={() => setVisibility(true)}><img src={menu} alt={menu}/></Menu>
      <div className="container">{templates.map(data => <EditorButton {...data} handleClick={insertTemplate}/>)}</div>
      <LastModified at={new Date().toLocaleTimeString()}></LastModified>
    </EditorButtonsBlock>
  );
};

export default React.memo(EditorButtons);
