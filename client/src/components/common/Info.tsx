import React, {ChangeEvent} from "react";
import {User} from "../../pages/Me";
import {CardsLayout} from "./Cards";
import styled from "@emotion/styled";

const InfoBlock = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 512px;
  
  > button {
    align-self: flex-end;
    margin-top: 20px;
    padding: 5px 20px;
    border: 0;
    border-radius: 8px;
    outline: none;
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
    user-select: none;
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
  editedUser: User,
  updateInfo: (user: User) => void,
  changeProfileImage: (event: ChangeEvent<HTMLInputElement>) => void,
  changeInput: (event: ChangeEvent<HTMLInputElement>) => void,
}

const Info: React.FC<IProps> = ({user, editedUser, updateInfo, changeProfileImage, changeInput}: IProps) => (
  <CardsLayout>
    <h2>{user.name}'s Info</h2>
    <hr/>
    <InfoBlock>
      <UserInfo>
        <Profile>
          <img src={editedUser.profileImage} alt={undefined}/>
          <label htmlFor="profile">CHANGE PROFILE</label>
          <input id="profile" type="file" style={{display: "none"}} onChange={changeProfileImage}/>
        </Profile>
        <Form>
          <Input>
            <div>NAME</div>
            <input autoComplete="off" name="name" value={editedUser.name} onChange={changeInput}/>
          </Input>
          <Input>
            <div>EMAIL</div>
            <input autoComplete="off" name="email" value={editedUser.email} onChange={changeInput}/>
          </Input>
        </Form>
      </UserInfo>
      <button onClick={() => updateInfo(editedUser)}>UPDATE</button>
    </InfoBlock>
  </CardsLayout>
);

export default Info;
