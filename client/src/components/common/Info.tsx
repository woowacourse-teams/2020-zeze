import React, {ChangeEvent, useCallback} from "react";
import {User} from "../../pages/Me";
import {CardsLayout} from "./Cards";
import styled from "@emotion/styled";
import filesApi from "../../api/file";
import {useRecoilState} from "recoil";
import {userInfoState} from "../../store/atoms";

const InfoBlock = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  
  > button {
    align-self: flex-end;
    margin-top: 20px;
    padding: 5px 20px;
    border: 0;
    border-radius: 8px;
    color: #777;
    font-weight: bold;
    background-color: transparent;
    cursor: pointer;
    
    &:hover {
      color: #fff;
    }
  }
`;

const UserInfo = styled.div`
  display: flex;
  flex: 1;
`;

const Profile = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 5px;
  color: #fff;
  width: 200px;
  
  > img {
    width: 80px;
    border-radius: 80px;
    border : 1px solid #777;
    margin-bottom: 10px;
  }
  
  > label {
    cursor: pointer;
    color: #777;
    
    &:hover {
      color: #fff;
    }
  }
    
`;

const Form = styled.div`
  display: flex;
  flex-direction: column;
  padding: 25px 0 0 20px;
  justify-content: space-between;
  width: 100%;
`;

const Input = styled.div`
  display: flex;
  font-size: 10px;
  color: #777;
  border-bottom: 2px solid #777;
  padding-bottom: 5px;
  align-items: center;
  
  > div {
    margin-left: 5px;
  }
  
  > input {
    color: #fff;
    font-weight: bold;
    font-size: 16px;
    margin: 0 20px;
    padding: 0;
    border: 0;
    outline: none;
    width: 100%;
    background-color: transparent;
  }
`;

interface IProps {
  user: User,
  updateInfo: (user: User) => void
}

const Info: React.FC<IProps> = ({user, updateInfo}: IProps) => {
  const [userInfo, setUserInfo] = useRecoilState(userInfoState);

  const changeInput = useCallback((event: ChangeEvent<HTMLInputElement>) => {
    setUserInfo({...userInfo, [event.target.name]: event.target.value});
  }, [userInfo]);

  const changeProfileImage = useCallback(async (event: ChangeEvent<HTMLInputElement>) => {
    const file: File | undefined = event.target.files?.[0];
    const newProfileImage = file && await filesApi.upload(file);
    const newProfileImageUrl: string = newProfileImage?.data.urls[0] || userInfo.profileImage;

    setUserInfo({...userInfo, profileImage: newProfileImageUrl});
  }, [userInfo]);

  return (
    <CardsLayout>
      <h2>{user.name}'s Info</h2>
      <hr/>
      <InfoBlock>
        <UserInfo>
          <Profile>
            <img src={userInfo.profileImage} alt={undefined}/>
            <label htmlFor="profile">CHANGE PROFILE</label>
            <input id="profile" type="file" style={{display: "none"}} onChange={changeProfileImage}/>
          </Profile>
          <Form>
            <Input>
              <div>NAME</div>
              <input name="name" placeholder={user.name} onChange={changeInput}/>
            </Input>
            <Input>
              <div>EMAIL</div>
              <input name="email" placeholder={user.email} onChange={changeInput}/>
            </Input>
          </Form>
        </UserInfo>
        <button onClick={() => updateInfo(userInfo)}>UPDATE</button>
      </InfoBlock>
    </CardsLayout>
  );
};

export default Info;
