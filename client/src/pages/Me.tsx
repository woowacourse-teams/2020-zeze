import React, {useCallback, useState} from "react";
import {useRecoilState, useRecoilValue} from "recoil";
import SidebarLayout from "../components/common/SidebarLayout";
import Info from "../components/common/Info";
import Cards from "../components/common/Cards";
import usersApi from "../api/user";

import {
  getAllSlidesQuery,
  userInfoState,
} from "../store/atoms";

export interface User {
  name: string,
  email: string,
  profileImage: string
}

const Me: React.FC = () => {
  const slides = useRecoilValue(getAllSlidesQuery);
  const [user, setUser] = useRecoilState(userInfoState);
  const [userName, setUserName] = useState<string>(user.name);

  const updateInfo = useCallback((userInfo: User) => {
    usersApi.update(userInfo)
      .then(() => alert("update success"));
    setUser(userInfo);
    setUserName(userInfo.name);
  }, [setUser]);

  return (
    <SidebarLayout>
      {/* <Cards title="Recent"/>*/}
      <Info user={{...user, name: userName}} updateInfo={updateInfo}/>
      <Cards title="My Drafts" slides={slides} author={userName}/>
    </SidebarLayout>
  );
};

export default Me;
