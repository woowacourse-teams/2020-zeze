import React, {ChangeEvent, useCallback, useEffect, useState} from "react";
import {useRecoilValue, useResetRecoilState} from "recoil";
import SidebarLayout from "../components/common/SidebarLayout";
import Info from "../components/common/Info";
import Cards from "../components/common/Cards";
import usersApi from "../api/user";

import {
  getAllSlidesQuery,
  userInfoQuery,
} from "../store/atoms";
import filesApi from "../api/file";
import {googleAnalyticsEvent, googleAnalyticsPageView} from "../utils/googleAnalytics";

export interface User {
  name: string,
  email: string,
  profileImage: string
}

const Me: React.FC = () => {
  const slides = useRecoilValue(getAllSlidesQuery);
  const user = useRecoilValue(userInfoQuery);
  const setUser = useResetRecoilState(userInfoQuery);
  const [editedUser, setEditedUser] = useState<User>(user!);

  useEffect(() => {
    googleAnalyticsPageView("My Page");
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
        alert("update success");
      })
      .then(() => setUser());
  }, [setUser]);

  return (
    <SidebarLayout>
      {/* <Cards title="Recent"/>*/}
      <Info user={user!}
        editedUser={editedUser}
        updateInfo={updateInfo}
        changeInput={changeInput}
        changeProfileImage={changeProfileImage}/>
      <Cards title="My Drafts" slides={slides} author={user?.name}/>
    </SidebarLayout>
  );
};

export default Me;
