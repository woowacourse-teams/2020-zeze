import React, {useCallback, useState} from "react";
import {useRecoilState, useRecoilValue} from "recoil";
import SidebarLayout from "../components/common/SidebarLayout";
import Info from "../components/common/Info";
import Cards from "../components/common/Cards";
import usersApi from "../api/user";

import {
  getAllSlidesQuery, userInfo,
  userInfoQuery,
} from "../store/atoms";

export interface User {
  name: string,
  email: string,
  profileImage: string
}

const Me: React.FC = () => {
  const slides = useRecoilValue(getAllSlidesQuery);
  const [user, setUser] = useRecoilState(userInfoQuery);

  const updateInfo = useCallback((userInfo: User) => {
    usersApi.update(userInfo)
      .then(() => alert("update success"));
    setUser(userInfo);
  }, []);

  return (
    <SidebarLayout>
      {/* <Cards title="Recent"/>*/}
      <Info user={user!} updateInfo={updateInfo}/>
      <Cards title="My Drafts" slides={slides} author={user!.name}/>
    </SidebarLayout>
  );
};

export default Me;
