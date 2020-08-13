import React, { useEffect, useCallback, useRef } from "react";
import { useRecoilState, useRecoilValue, atom } from "recoil";
import { useParams, useHistory } from "react-router-dom";
import styled from "@emotion/styled";

import Preview from "../components/editor/Preview";
import MarkdownEditor from "../components/editor/MarkdownEditor";
import FullScreenMode from "../components/common/FullScreenMode";
import SidebarLayout from "../components/common/SidebarLayout";

import slideApi from "../api/slide";
import filesApi from "../api/file";
import fixtures from "../utils/fixtures";
import { MOBILE_MAX_WIDTH } from "../domains/constants";
import {clear, save} from "../assets/icons";

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

const Edit = styled.div`
  display: flex;
  flex-direction: column;
`;

const ButtonMenu = styled.div`
  display: flex;
  align-items: center;
  padding-left: 15px;
  background-color: #313335;
  
  > button {
    background-position: center;
    background-repeat: no-repeat;
    height: 16px;
    width: 50px;
    margin: 5px;
    border: 0;
    border-radius: 8px;
    outline: none;
    color: #777;
    font-weight: bold;
    background-color: transparent;
    cursor: pointer;
  }
`;

const SaveButton = styled.button`
  background-image: url(${save});
`;

const DeleteButton = styled.button`
  background-image: url(${clear});
`;

interface Params {
  id?: string
}

const Editor: React.FC = () => {
  const params = useParams<Params>();
  const history = useHistory();
  const codemirrorRef = useRef<CodeMirror.Editor | null>(null);

  const [id, setId] = useRecoilState(slideIdState);
  const [content, setContent] = useRecoilState(slideContentState);
  const slides = useRecoilValue(parsedSlides);
  const { title } = useRecoilValue(slideMetadata);
  const accessLevel = useRecoilValue(slideAccessLevelState);

  useEffect(() => {
    setId(parseInt(params?.id ?? "", 10));
  }, [params]);

  useEffect(() => {
    id && slideApi.get(id)
      .then(({ data }) => {
        setContent(data.content);
        codemirrorRef.current?.setValue(data.content);
      })
      .catch(() => {
        setContent(fixtures);
        codemirrorRef.current?.setValue(fixtures);
      });
  }, [id]);

  const uploadFile = useCallback((file: File) => new Promise<string>(resolve => {
    filesApi.upload(file)
      .then(response => response.data.urls[0])
      .then(url => resolve(url));
  }), []);

  const create = useCallback(async () => {
    const {headers: { location }} = await slideApi.create({
      data: {
        title,
        content,
        accessLevel,
      },
    });
    const slideId = location.substring(location.lastIndexOf("/") + 1);

    setId(parseInt(slideId, 10));
  }, [title, content, accessLevel]);

  const update = useCallback(() => {
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
  }, [id, title, content, accessLevel]);

  const save = useCallback(() => {
    id ? update() : create();
  }, [id, content]);

  const deleteSlide = useCallback(() => {
    id && slideApi.delete(id)
      .then(() => history.push("/archive"));
  }, [id, history]);

  return (
    <SidebarLayout fluid>
      <EditorBlock>
        <Edit>
          <ButtonMenu>
            <SaveButton onClick={save} />
            <DeleteButton onClick={deleteSlide} />
          </ButtonMenu>
          <MarkdownEditor inputRef={codemirrorRef} onChange={setContent} onDrop={uploadFile} />
          <FullScreenMode contents={slides} />
        </Edit>
        <Preview content={content} />
      </EditorBlock>
    </SidebarLayout>
  );
};

export default Editor;
