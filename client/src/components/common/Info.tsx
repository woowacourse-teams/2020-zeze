import React, {ChangeEvent} from "react";
import {User} from "../../pages/Me";
import {CardsLayout} from "./Cards";
import styled from "@emotion/styled";
import filesApi from "../../api/file";
import {useRecoilState} from "recoil";
import {userInfoState} from "../../store/atoms";
import {MOBILE_MAX_WIDTH} from "../../domains/constants";

const InfoBlock = styled.div`
  display: flex;
  flex-direction: column;
  color: #fff;
  
  > input {
    width: 60%;
    height: 2em;
    margin: 0.5em 0 1em;
    color: #fff;
    background-color: #222;
    border: none;
    
    @media(max-width: ${MOBILE_MAX_WIDTH}px) {
    flex-direction: column-reverse;
    width: 90%
    }
  }
  
  > button, label {
    text-decoration: none;
    display: inline-block;
    width: 60%;
    height: 2em;
    margin: 0.5em 0;
    border: 1px solid white;
    border-radius: 50px;
    background-color: #222;
    color: #fff;
    font-size: 1.1rem;
    word-break: break-all;
    
    &:hover {
    cursor: pointer;
    }
  }
  
  > label {
    text-align: center;
    line-height: 1.9em;
    width: 30%;
  }
  
  > img {
    margin: 0.5em 0 0;
    width: 200px;
  }
  
  @media(max-width: ${MOBILE_MAX_WIDTH}px) {
     > button, label {
      text-align: center;
      line-height: 1.9em;
      width: 90%;
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
      <div>
      <InfoBlock>
        <div>NAME</div>
        <input placeholder={user.name} onChange={changeName}/>
        <div>EMAIL</div>
        <input placeholder={user.email} onChange={changeEmail}/>
        <div>PROFILE IMAGE</div>
        <img src={userInfo.profileImage} alt={undefined}/>
        <label htmlFor="profile">upload profile</label>
        <input id="profile" type="file" style={{display:"none"}} onChange={changeProfileImage}/>
        <button onClick={event => updateInfo(userInfo)}>update</button>
      </InfoBlock>
      </div>
    </CardsLayout>
  );
};

export default Info;
