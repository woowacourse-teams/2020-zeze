/* eslint-disable react-hooks/exhaustive-deps */
import React, {useCallback, useEffect, useLayoutEffect, useMemo, useRef, useState} from "react";
import {useHistory, useParams} from "react-router-dom";
import {useRecoilValue, useSetRecoilState} from "recoil";
import styled from "@emotion/styled";
import moment from "moment";
import {isMobile} from "react-device-detect";

import Preview from "../components/editor/Preview";
import MarkdownEditor from "../components/editor/MarkdownEditor";
import FullScreenMode from "../components/common/FullScreenMode";

import SidebarLayout from "../components/common/SidebarLayout";
import slideApi from "../api/slide";
import filesApi from "../api/file";
import {AccessLevel, MOBILE_MAX_WIDTH, ToastType} from "../domains/constants";
import {createTemplate, parse, ParsedData} from "../utils/metadata";
import ToastFactory from "../domains/ToastFactory";
import {sidebarVisibility, userInfoQuery} from "../store/atoms";
import {googleAnalyticsEvent, googleAnalyticsException, googleAnalyticsPageView} from "../utils/googleAnalytics";
import EditorButtons from "../components/common/EditorButtons";
import Tutorial from "../components/common/Tutorial";

const EditorBlock = styled.main`
  display: flex;
  height: 100%;
  max-height: 100vh;

  > div {
    position: relative;
    overflow: auto;
  }

  @media(max-width: ${MOBILE_MAX_WIDTH}px) {
    flex-direction: column;
    height: 100vh;
  }
`;

const Edit = styled.div`
  flex: 1.5;
  display: flex;
  flex-direction: column;
`;

const SaveButton = styled.div`
  cursor: pointer;
  position: absolute;
  z-index: 3;
  top: 70px;
  right: 70px;
  > img {
    width: 20px;
    height: 20px;
  }
`;

const AccessLevelButton = styled.div`
  cursor: pointer; 
  position: absolute;
  z-index: 3;
  top: 70px;
  right: 110px;
  > img {
    width: 20px;
    height: 20px;
  }
`;

interface Params {
  id?: string
}

const Editor: React.FC = () => {
  const editorRef = useRef<any>(null);
  const [editorWidth, setEditorWidth] = useState<number>(0);
  const [tutorial, setTutorial] = useState<boolean>(!localStorage.getItem("tutorialEnd"));

  const user = useRecoilValue(userInfoQuery);
  const setVisibility = useSetRecoilState(sidebarVisibility);
  const params = useParams<Params>();
  const id = parseInt(params?.id ?? "0", 10);
  const history = useHistory();
  const codemirrorRef = useRef<CodeMirror.Editor | null>(null);
  const toastFactory = ToastFactory();

  const [updatedAt, setUpdatedAt] = useState<string>("");
  const [content, setContent] = useState<string>("");
  const [accessLevel, setAccessLevel] = useState<AccessLevel>(AccessLevel.PRIVATE);

  const [isOwner, setIsOwner] = useState<boolean>(false);

  const parsed = useMemo<ParsedData>(() => parse(content), [content]);

  const slides = useMemo<string[]>(() => (
    parsed.content.split(/^---$/m)
      .filter((slideContent: string) => slideContent.trim())
  ), [parsed]);

  useLayoutEffect(() => {
    const handleResize = () => {
      setEditorWidth(editorRef.current.offsetWidth);
    };
    window.addEventListener("resize", handleResize);
    handleResize();
    return () => window.removeEventListener("resize", handleResize);
  }, [editorRef, setEditorWidth]);

  useEffect(() => {
    googleAnalyticsPageView("Editor");
    setVisibility(false);
    return () => {
      setVisibility(true);
    };
  }, []);

  useEffect(() => {
    if (id) {
      return;
    }
    setUpdatedAt("");
    codemirrorRef.current?.setValue(createTemplate({
      author: user!.name,
    }));
  }, [id, user]);

  useEffect(() => {
    id && slideApi.get(id)
      .then(({data}) => {
        setUpdatedAt(moment(data.updatedAt).fromNow());
        setAccessLevel(data.accessLevel);
        codemirrorRef.current?.setValue(data.content);
      })
      .catch(() => {
        googleAnalyticsException(`슬라이드 ${id} 불러오기 실패`);
        toastFactory.createToast("Fail to fetch data", ToastType.ERROR);
      }) && slideApi.ownSlide(id)
      .then(({data}) => {
        setIsOwner(data);
      })
      .catch(() => {
        toastFactory.createToast("Fail to fetch data", ToastType.ERROR);
      });
  }, []);

  const uploadFile = useCallback((file: File) => new Promise<string>(resolve => {
    filesApi.upload(file)
      .then(response => response.data.urls[0])
      .then(url => resolve(url))
      .catch(() => {
        googleAnalyticsException("파일 업로드 실패");
        toastFactory.createToast("Fail to upload", ToastType.ERROR);
      });
  }), []);

  const uploadExternalFile = useCallback((url: string, name: string) => (
    new Promise<string>(resolve => {
      filesApi.uploadExternal(url, name)
        .then(response => response.data.urls[0])
        .then(fileUrl => resolve(fileUrl));
    })), []);

  const create = useCallback(async () => {
    try {
      const {headers: {location}} = await slideApi.create({
        data: {
          title: parsed.metadata?.title ?? "Untitled",
          subtitle: parsed.metadata?.subtitle ?? "Untitled",
          author: parsed.metadata?.author ?? "Anonymous",
          presentedAt: parsed.metadata?.presentedAt ?? moment().format("YYYY-MM-DD"),
          content: codemirrorRef.current!.getValue(),
          accessLevel,
        },
      });
      const slideId = location.substring(location.lastIndexOf("/") + 1);

      googleAnalyticsEvent("슬라이드", `#${slideId} 저장 완료`);
      toastFactory.createToast("Successfully create", ToastType.SUCCESS);
      history.replace(`/editor/${slideId}`);
      setUpdatedAt(moment().fromNow());
      setIsOwner(true);
    } catch (error) {
      googleAnalyticsException("슬라이드 (신규) 저장 실패");
      toastFactory.createToast("Fail to create", ToastType.ERROR);
    }
  }, [history, parsed, accessLevel]);

  const update = useCallback(async (action: string, accessLevelChanger?: (prevAccessLevel: AccessLevel) => AccessLevel) => {
    const data = {
      id,
      data: {
        title: parsed.metadata?.title ?? "Untitled",
        subtitle: parsed.metadata?.subtitle ?? "Untitled",
        author: parsed.metadata?.author ?? "Anonymous",
        presentedAt: parsed.metadata?.presentedAt ?? moment().format("YYYY-MM-DD"),
        content: codemirrorRef.current!.getValue(),
        accessLevel: accessLevelChanger ? accessLevelChanger(accessLevel) : accessLevel,
      },
    };

    try {
      await slideApi.update(data);
      googleAnalyticsEvent("슬라이드", `#${id} 수정 완료`);
      toastFactory.createToast(`Successfully ${action}`, ToastType.SUCCESS);
      setUpdatedAt(moment().fromNow());
      accessLevelChanger && setAccessLevel(accessLevelChanger(accessLevel));
    } catch {
      googleAnalyticsException(`슬라이드 #{id} 수정 실패`);
      toastFactory.createToast(`Fail to ${action}`, ToastType.ERROR);
    }
  }, [id, parsed.metadata, toastFactory, accessLevel]);

  const save = useCallback(() => {
    id ? update("save") : create();
  }, [id, update, create]);

  const toggleAccessLevel = (prevAccessLevel: AccessLevel) => (prevAccessLevel === AccessLevel.PUBLIC ? AccessLevel.PRIVATE : AccessLevel.PUBLIC);

  const changeAccessLevel = useCallback(async () => {
    id ? await update("change access level", toggleAccessLevel) : setAccessLevel(toggleAccessLevel(accessLevel));
  }, [id, update]);

  const endTutorial = useCallback(() => {
    localStorage.setItem("tutorialEnd", "true");
    setTutorial(false);
  }, [tutorial]);

  return (
    <SidebarLayout fluid toggleable>
      {tutorial && !isMobile ? <Tutorial editorWidth={editorWidth} endTutorial={endTutorial}/> : <></>}
      <EditorBlock>
        <Edit ref={editorRef}>
          <EditorButtons inputRef={codemirrorRef} updatedAt={updatedAt}/>
          <MarkdownEditor
            inputRef={codemirrorRef}
            onChange={setContent}
            onDrop={uploadFile}
            onSaveKeyDown={save}
            onExternalDrop={uploadExternalFile}
          />
          {(!id || isOwner) &&
          <>
            <AccessLevelButton onClick={changeAccessLevel}>
              <img src={accessLevel === AccessLevel.PUBLIC ? "/assets/icons/public.svg" : "/assets/icons/private.svg"}
                alt="access level"/>
            </AccessLevelButton>
            <SaveButton onClick={save}><img src="/assets/icons/save.svg" alt="save"/></SaveButton>
          </>
          }
          <FullScreenMode contents={slides}/>
        </Edit>
        <Preview content={content}/>
      </EditorBlock>
    </SidebarLayout>
  );
};

export default Editor;
