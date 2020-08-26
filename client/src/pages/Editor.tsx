/* eslint-disable react-hooks/exhaustive-deps */
import React, {useCallback, useEffect, useMemo, useRef, useState} from "react";
import {useHistory, useParams} from "react-router-dom";
import {useRecoilValue, useSetRecoilState} from "recoil";
import styled from "@emotion/styled";
import moment from "moment";

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
  const isInitialMount = useRef(true);

  const [isOwner, setIsOwner] = useState<boolean>(false);

  const parsed = useMemo<ParsedData>(() => parse(content), [content]);

  const slides = useMemo<string[]>(() => (
    parsed.content.split(/^---$/m)
      .filter((slideContent: string) => slideContent.trim())
  ), [parsed]);

  useEffect(() => {
    googleAnalyticsPageView("Editor");
    setVisibility(false);
    return () => {
      setVisibility(true);
    };
  }, []);

  useEffect(() => {
    if (isInitialMount.current) {
      isInitialMount.current = false;
    } else {
      id && update("change access level");
    }
  }, [accessLevel]);

  useEffect(() => {
    if (id) {
      return;
    }
    setUpdatedAt("");
    if (accessLevel === AccessLevel.PUBLIC) {
      isInitialMount.current = true;
      setAccessLevel(AccessLevel.PRIVATE);
    }
    codemirrorRef.current?.setValue(createTemplate({
      author: user!.name,
    }));
  }, [id, user]);

  useEffect(() => {
    id && slideApi.get(id)
      .then(({data}) => {
        if (data.accessLevel === AccessLevel.PUBLIC && !isInitialMount.current) {
          isInitialMount.current = true;
          setAccessLevel(AccessLevel.PUBLIC);
        }
        setUpdatedAt(moment(data.updatedAt).fromNow());
        codemirrorRef.current?.setValue(data.content);
      })
      .catch(() => {
        googleAnalyticsException(`슬라이드 ${id} 불러오기 실패`);
        toastFactory.createToast("couldn't fetch data", ToastType.ERROR);
      })
    && slideApi.ownSlide(id)
      .then(({data}) => {
        setIsOwner(data);
      })
      .catch(() => {
        toastFactory.createToast("couldn't fetch data", ToastType.ERROR);
      })
  }, []);

  const uploadFile = useCallback((file: File) => new Promise<string>(resolve => {
    filesApi.upload(file)
      .then(response => response.data.urls[0])
      .then(url => resolve(url))
      .catch(() => {
        googleAnalyticsException("파일 업로드 실패");
        toastFactory.createToast("uploads failure", ToastType.ERROR);
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
      toastFactory.createToast("create success", ToastType.SUCCESS);
      history.replace(`/editor/${slideId}`);
      setUpdatedAt(moment().fromNow());
      setIsOwner(true);
    } catch (error) {
      googleAnalyticsException("슬라이드 (신규) 저장 실패");
      toastFactory.createToast("create failure", ToastType.ERROR);
    }
  }, [history, parsed, accessLevel]);

  const update = useCallback(async (action: string) => {
    const data = {
      id,
      data: {
        title: parsed.metadata?.title ?? "Untitled",
        subtitle: parsed.metadata?.subtitle ?? "Untitled",
        author: parsed.metadata?.author ?? "Anonymous",
        presentedAt: parsed.metadata?.presentedAt ?? moment().format("YYYY-MM-DD"),
        content: codemirrorRef.current!.getValue(),
        accessLevel,
      },
    };

    try {
      await slideApi.update(data);
      googleAnalyticsEvent("슬라이드", `#${id} 수정 완료`);
      toastFactory.createToast(`${action} success`, ToastType.SUCCESS);
      setUpdatedAt(moment().fromNow());
    } catch {
      googleAnalyticsException(`슬라이드 #{id} 수정 실패`);
      toastFactory.createToast(`${action} failure`, ToastType.ERROR);
    }
  }, [id, parsed.metadata, toastFactory, accessLevel]);

  const save = useCallback(() => {
    id ? update("save") : create();
  }, [id, update, create]);

  const changeAccessLevel = useCallback(() => {
    setAccessLevel(accessLevel === AccessLevel.PUBLIC ? AccessLevel.PRIVATE : AccessLevel.PUBLIC);
  }, [accessLevel]);

  return (
    <SidebarLayout fluid toggleable>
      <EditorBlock>
        <Edit>
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
                <img src={accessLevel === AccessLevel.PUBLIC ? "/assets/icons/public.svg" : "/assets/icons/private.svg"} alt="access level"/>
              </AccessLevelButton>
              <SaveButton onClick={save}><img src="/assets/icons/save.svg" alt="save" /></SaveButton>
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
