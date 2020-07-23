import React, {useState} from "react";
import Layout from "../common/Layout";
import * as S from "./assets";
import Preview from "./Preview";
import {marked, splitter} from "../../utils/markdown/renderer";

// Temporary fixture
import {sampleMarkdown} from "../../utils/fixtures";

const Editor: React.FC = () => {
  const [input, setInput] = useState<string>(sampleMarkdown.content);

  return (
    <Layout footer={false}>
      <S.Editor>
        {/* Temporary textarea */}
        <textarea style={{
          flex: 1,
          boxSizing: "border-box",
          height: "100%",
          padding: 30,
          backgroundColor: "#333",
          color: "#fff",
          fontFamily: "monospace",
        }}
        value={input}
        onChange={e => setInput(e.target.value)}
        />
        <S.Preview>
          <Preview contents={
            splitter({
              content: marked(input),
              delimiter: sampleMarkdown.delimiter,
            })
          }/>
        </S.Preview>
      </S.Editor>
    </Layout>
  );
};

export default Editor;
