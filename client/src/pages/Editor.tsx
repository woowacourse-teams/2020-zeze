import React, {useEffect, useState} from "react";
import styled from "@emotion/styled";
import Preview from "../components/editor/Preview";
import MarkdownEditor from "../components/editor/MarkdownEditor";
import sample from "../utils/fixtures";
import FullScreenMode from "../components/common/FullScreenMode";
import parse from "../utils/metadata";
import SidebarLayout from "../components/common/SidebarLayout";
import {MOBILE_MAX_WIDTH} from "../domains/constants";

const EditorBlock = styled.main`
  display: flex;
  height: 100%;
  max-height: 100vh;
  
  > div {
    flex: 1;
    position: relative;
    overflow: auto;
  }
  
  @media(max-width: ${MOBILE_MAX_WIDTH}px) {
    flex-direction: column-reverse;
    height: 100vh;
  }
`;

const Editor: React.FC = () => {
  const [text, setText] = useState<string>(sample);
  const [contents, setContents] = useState<string[]>(text.split("---"));

  useEffect(() => {
    const {content: parsedContents} = parse(text);

    parsedContents && setContents(parsedContents
      .split("---")
      .filter(content => content.trim().length !== 0),
    );
  }, [text]);

  const uploadFile = (file: File) => new Promise<string>(resolve => {
    setTimeout(() => {
      resolve(`http://localhost/${file.name}`);
    }, 3000);
  });

  return (
    <SidebarLayout fluid>
      <EditorBlock>
        <div className="editor">
          <MarkdownEditor defaultValue={text} onChange={setText} onDrop={uploadFile}/>
          <FullScreenMode contents={contents}/>
        </div>
        <Preview content={text}/>
      </EditorBlock>
    </SidebarLayout>
  );
};

export default Editor;
