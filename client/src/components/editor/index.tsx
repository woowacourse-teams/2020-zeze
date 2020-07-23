import React, {useState} from "react";
import Layout from "../common/Layout";
import * as S from "./assets";
import Preview from "./Preview";

const Editor: React.FC = () => {
  const [input, setInput] = useState<string>(`---
title: Hello LimeTree!
author: Hodol
created_at: 2020-07-12
---

## hello world!
### this is subtitle

--- 

## another page
`);

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
        <Preview content={input}/>
      </S.Editor>
    </Layout>
  );
};

export default Editor;
