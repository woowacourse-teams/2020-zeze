/* eslint-disable react-hooks/exhaustive-deps */
import React, {ChangeEvent, useCallback, useEffect, useState} from "react";
import {useRecoilValue, useResetRecoilState, useSetRecoilState} from "recoil";
import styled from "@emotion/styled";
import SidebarLayout from "../components/common/SidebarLayout";
import Info from "../components/common/Info";
import usersApi from "../api/user";

import {sidebarVisibility, userInfoQuery} from "../store/atoms";
import filesApi from "../api/file";
import {ToastType} from "../domains/constants";
import ToastFactory from "../domains/ToastFactory";
import {googleAnalyticsEvent, googleAnalyticsPageView} from "../utils/googleAnalytics";
import SlidesLayout from "../components/common/SlidesLayout";
import slideApi from "../api/slide";
import Toast from "../components/common/Toast";

const MeBlock = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

export interface User {
  name: string,
  email: string,
  profileImage: string
}


const Me: React.FC = () => {
  const user = useRecoilValue(userInfoQuery);
  const setUser = useResetRecoilState(userInfoQuery);
  const setVisibility = useSetRecoilState(sidebarVisibility);
  const [editedUser, setEditedUser] = useState<User>(user!);
  const toastFactory = ToastFactory();

  useEffect(() => {
    googleAnalyticsPageView("My Page");
    setVisibility(true);
  }, []);

  const changeInput = useCallback((event: ChangeEvent<HTMLInputElement>) => {
    setEditedUser({...editedUser, [event.target.name]: event.target.value});
  }, [editedUser]);

  const changeProfileImage = useCallback(async (event: ChangeEvent<HTMLInputElement>) => {
    const file: File | undefined = event.target.files?.[0];
    const newProfileImage = file && await filesApi.upload(file);
    const newProfileImageUrl: string = newProfileImage?.data.urls[0] || editedUser.profileImage;

    setEditedUser({...editedUser, profileImage: newProfileImageUrl});
  }, [editedUser]);

  const updateInfo = useCallback((userInfo: User) => {
    usersApi.update(userInfo)
      .then(() => {
        googleAnalyticsEvent("유저", "정보 업데이트 성공");
        toastFactory.createToast("update success", ToastType.SUCCESS);
      })
      .then(() => setUser())
      .catch(() => toastFactory.createToast("update failure", ToastType.ERROR));
  }, [toastFactory]);

  return (
    <SidebarLayout>
      <MeBlock>
        <Toast type="warn">Still in development. Please report bugs to our <a href="https://github.com/woowacourse-teams/2020-zeze/issues/new?assignees=&labels=🐞%20bug%20report&template=bug_report.md" target="_blank" rel="noopener noreferrer">Github Issues</a> !</Toast>
        <Info user={user!}
          editedUser={editedUser}
          updateInfo={updateInfo}
          changeInput={changeInput}
          changeProfileImage={changeProfileImage}/>
        <SlidesLayout getAllSlides={slideApi.getAll} slidesCnt={6} title="My Drafts"/>
      </MeBlock>
    </SidebarLayout>
  );
};

export default Me;
