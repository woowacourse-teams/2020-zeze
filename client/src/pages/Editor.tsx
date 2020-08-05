import React, {useEffect, useState} from "react";
import {useParams, useHistory} from "react-router-dom";
import styled from "@emotion/styled";
import Preview from "../components/editor/Preview";
import MarkdownEditor from "../components/editor/MarkdownEditor";
import FullScreenMode from "../components/common/FullScreenMode";
import parse from "../utils/metadata";
import SidebarLayout from "../components/common/SidebarLayout";
import {MOBILE_MAX_WIDTH} from "../domains/constants";
import slideApi from "../api/slide";
import filesApi from "../api/file";
import fixtures from "../utils/fixtures";

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

enum AccessLevel {
  PRIVATE = "PRIVATE",
  PUBLIC = "PUBLIC"
}

interface Params {
  id?: string
}

const Editor: React.FC = () => {
  const params = useParams<Params>();
  const [id, setId] = useState<number | undefined>(parseInt(params?.id ?? "", 10));
  const [text, setText] = useState<string>("");
  const [contents, setContents] = useState<string[]>(text.split("---"));
  const [accessLevel] = useState<AccessLevel>(AccessLevel.PRIVATE);
  const [title] = useState<string>("");

  const history = useHistory();

  useEffect(() => {
    slideApi.get({id})
      .then(({data}) => setText(data.content))
      .catch(() => setText(fixtures));
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    const {content: parsedContents} = parse(text);

    parsedContents && setContents(parsedContents
      .split("---")
      .filter(content => content.trim().length !== 0),
    );
  }, [text]);

  const uploadFile = (file: File) => new Promise<string>(resolve => {
    filesApi.upload(file)
      .then(response => response.data.urls[0])
      .then(url => resolve(url));
  });

  const create = () => {
    const data = {
      data: {
        title,
        content: text,
        accessLevel,
      },
    };

    slideApi.create(data)
      .then(response => response.headers.location)
      .then(location => location.substring(location.lastIndexOf("/") + 1))
      .then(generatedId => setId(parseInt(generatedId, 10)));
  };

  const update = () => {
    const data = {
      id,
      data: {
        title,
        content: text,
        accessLevel,
      },
    };

    slideApi.update(data)
      .then(() => alert("성공"))
      .catch(() => alert("실패"));
  };

  const save = () => (id ? update() : create());

  const deleteSlide = () => {
    id && slideApi.delete(id)
      .then(() => history.push("/archive"));
  };

  return (
    <SidebarLayout fluid>
      <EditorBlock>
        <div className="editor">
          <button onClick={save}>save</button>
          <button onClick={deleteSlide}>delete</button>
          <MarkdownEditor value={text} onChange={setText} onDrop={uploadFile}/>
          <FullScreenMode contents={contents}/>
        </div>
        <Preview content={text}/>
      </EditorBlock>
    </SidebarLayout>
  );
};

export default Editor;
