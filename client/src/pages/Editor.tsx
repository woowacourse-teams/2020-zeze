import React, {useEffect} from "react";
import {useRecoilState, useRecoilValue} from "recoil";
import {useParams, useHistory} from "react-router-dom";
import styled from "@emotion/styled";

import Preview from "../components/editor/Preview";
import MarkdownEditor from "../components/editor/MarkdownEditor";
import FullScreenMode from "../components/common/FullScreenMode";
import SidebarLayout from "../components/common/SidebarLayout";

import slideApi from "../api/slide";
import filesApi from "../api/file";
import fixtures from "../utils/fixtures";
import {MOBILE_MAX_WIDTH} from "../domains/constants";

import {
  parsedSlides,
  slideAccessLevelState,
  slideContentState,
  slideIdState,
  slideMetadata,
} from "../store/atoms";

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

interface Params {
  id?: string
}

const Editor: React.FC = React.memo(() => {
  const params = useParams<Params>();
  const history = useHistory();

  const [id, setId] = useRecoilState(slideIdState);
  const [content, setContent] = useRecoilState(slideContentState);
  const slides = useRecoilValue(parsedSlides);
  const {title, subtitle, author, createdAt} = useRecoilValue(slideMetadata);
  const accessLevel = useRecoilValue(slideAccessLevelState);

  useEffect(() => {
    setId(parseInt(params?.id ?? "", 10));
  }, [params, setId]);

  useEffect(() => {
    id && slideApi.get(id)
      .then(({data}) => setContent(data.content))
      .catch(() => setContent(fixtures));
  }, [id, setContent]);

  const uploadFile = (file: File) => new Promise<string>(resolve => {
    filesApi.upload(file)
      .then(response => response.data.urls[0])
      .then(url => resolve(url));
  });

  const create = async () => {
    const response = await slideApi.create({
      data: {
        title,
        content,
        accessLevel,
      },
    });
    const slideId = response.headers.location.lastIndexOf("/") + 1;

    setId(parseInt(slideId, 10));
  };

  const update = () => {
    const data = {
      id,
      data: {
        title,
        content,
        accessLevel,
      },
    };

    slideApi.update(data)
      .then(() => alert("성공"))
      .catch(() => alert("실패"));
  };

  const save = () => {
    id ? update() : create();
  };

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
          <MarkdownEditor value={content} onChange={setContent} onDrop={uploadFile}/>
          <FullScreenMode contents={slides}/>
        </div>
        <Preview content={content}/>
      </EditorBlock>
    </SidebarLayout>
  );
});

export default Editor;
