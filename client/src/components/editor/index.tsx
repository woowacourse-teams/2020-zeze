import React, {useEffect, useState} from "react";
import * as S from "./assets";
import Preview from "./Preview";
import {marked, splitter} from "../../utils/markdown/renderer";

// Temporary fixture
import {sampleMarkdown} from "../../utils/fixtures";
import SidebarLayout from "../common/SidebarLayout";
import FullScreenMode from "../common/FullScreenMode";

const Editor: React.FC = () => {
  const [input, setInput] = useState<string>(sampleMarkdown.content);
  const [contents, setContents] = useState<string[]>();

  useEffect(() => {
    setContents(splitter({
      content: marked(input),
      delimiter: sampleMarkdown.delimiter,
    }));
  }, [input]);

  return (
    <SidebarLayout fluid>
      <S.Editor>
        <div>
          {/* Temporary textarea */}
          <textarea
            value={input}
            onChange={e => setInput(e.target.value)}
          />
          {contents && <FullScreenMode contents={contents}/>}
        </div>
        <Preview contents={contents}/>
      </S.Editor>
    </SidebarLayout>
  );
};

export default Editor;
