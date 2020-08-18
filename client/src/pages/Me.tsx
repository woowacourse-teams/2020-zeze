import React, {ChangeEvent, useCallback, useState} from "react";
import {useRecoilValue, useResetRecoilState} from "recoil";
import SidebarLayout from "../components/common/SidebarLayout";
import Info from "../components/common/Info";
import Cards from "../components/common/Cards";
import usersApi from "../api/user";

import {
  getAllSlidesQuery, toastMessages,
  userInfoQuery,
} from "../store/atoms";
import filesApi from "../api/file";
import ToastAlarm from "../components/common/ToastAlarm";
import {Toast, ToastPosition} from "../domains/constants";
import {checkIcon, errorIcon, infoIcon, warnIcon} from "../assets/icons";

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
  const [toastMessages, setToastMessages] = useState<Array<Toast>>([]);

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
      .then(() => addToast({
        id: 1,
        title: "update information",
        description: "update success",
        backgroundColor: '#5cb85c',
        icon: checkIcon,
      }))
      .then(() => setUser());
  }, [setUser]);

  const deleteToast = (id: number) => {
    const index = toastMessages.findIndex(e => e.id === id);
    toastMessages.splice(index, 1);
    setToastMessages([...toastMessages]);
  }

  const addToast = (toast: Toast) => {
    setToastMessages([...toastMessages, toast]);
  }

  return (
    <SidebarLayout>
      {/* <Cards title="Recent"/>*/}
      <Info user={user!}
        editedUser={editedUser}
        updateInfo={updateInfo}
        changeInput={changeInput}
        changeProfileImage={changeProfileImage}/>
      <Cards title="My Drafts" slides={slides} author={user?.name}/>
      <ToastAlarm toasts={toastMessages} position={ToastPosition.BOTTOM_LEFT} deleteToast={deleteToast}/>
    </SidebarLayout>
  );
};

export default Me;
