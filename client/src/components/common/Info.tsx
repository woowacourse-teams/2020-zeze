import React, {ChangeEvent} from "react";
import {User} from "../../pages/Me";
import {CardsLayout} from "./Cards";
import styled from "@emotion/styled";
import filesApi from "../../api/file";
import {useRecoilState} from "recoil";
import {userInfoState} from "../../store/atoms";

const InfoBlock = styled.div`
  display: flex;
  flex-direction: column;
  color: #fff;
  
  > input {
  }
  
  > button {
  }
  
  > img {
    width: 30%;
  }
`;

interface Props {
  user: User,
  updateInfo: (user: User) => void
}

const Info: React.FC<Props> = ({user, updateInfo}: Props) => {
  const [userInfo, setUserInfo] = useRecoilState(userInfoState);

  const changeName = (event: ChangeEvent<HTMLInputElement>) => {
    setUserInfo({...userInfo, name: event.target.value});
  };

  const changeEmail = (event: ChangeEvent<HTMLInputElement>) => {
    setUserInfo({...userInfo, email: event.target.value});
  };

  const changeProfileImage = async (event: ChangeEvent<HTMLInputElement>) => {
    const file: File | undefined = event.target.files?.[0];
    const newProfileImage = file && await filesApi.upload(file);
    const newProfileImageUrl: string = newProfileImage?.data.urls[0] || userInfo.profileImage;

    setUserInfo({...userInfo, profileImage: newProfileImageUrl});
  };

  return (
    <CardsLayout>
      <h2>{user.name}'s Info</h2>
      <hr/>
      <InfoBlock>
        <div>NAME</div>
        <input placeholder={user.name} onChange={changeName}/>
        <div>EMAIL</div>
        <input placeholder={user.email} onChange={changeEmail}/>
        <div>PROFILE IMAGE</div>
        <img src={userInfo.profileImage} alt={undefined}/>
        <input type="file" onChange={changeProfileImage}/>
        <button onClick={event => updateInfo(userInfo)}>update</button>
      </InfoBlock>
    </CardsLayout>
  );
};

export default Info;
