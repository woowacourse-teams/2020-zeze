import React, {useEffect, useState} from "react";
import SidebarLayout from "../components/common/SidebarLayout";
import Toast from "../components/common/Toast";
import Info from "../components/common/Info";
import Cards from "../components/common/Cards";
import {useRecoilValue} from "recoil";
import {getAllSlidesQuery, getUserInfoQuery} from "../store/atoms";
import usersApi from "../api/user";

export interface User {
  name: string,
  email: string,
  profileImage: string
}

const Me: React.FC = () => {
  const slides = useRecoilValue(getAllSlidesQuery);
  const user = useRecoilValue(getUserInfoQuery);

  const updateUser = ({user}: User) => {

  };

  return (
    <SidebarLayout>
      <Toast type="warn" message="Currently in development. Sorry for your inconvenience :("/>
       {/*<Cards title="Recent"/>*/}
       <Cards title="My Drafts" slides={slides}/>
       {/*<Info user={user}/>*/}
    </SidebarLayout>
  );
};

export default Me;
