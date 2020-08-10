import React, {ChangeEvent, ChangeEventHandler, useState} from "react";
import {User} from "../../pages/Me";
import {CardsLayout} from "./Cards";
import styled from "@emotion/styled";
import filesApi from "../../api/file";

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
  const [name, setName] = useState<string>(user.name);
  const [email, setEmail] = useState<string>(user.email);
  const [profileImage, setProfileImage] = useState<string>(user.profileImage);

  const changeProfileImage = (event: ChangeEvent<HTMLInputElement>) => {
    const file: File | undefined = event.target.files?.[0];
    file && filesApi.upload(file)
      .then(response => response.data.urls[0])
      .then(url => setProfileImage(url));
  };

  return (
    <CardsLayout>
      <h2>{user.name}'s Info</h2>
      <hr/>
      <InfoBlock>
        <div>NAME</div>
        <input placeholder={user.name}/>
        <div>EMAIL</div>
        <input placeholder={user.email}/>
        <div>PROFILE IMAGE</div>
        <img src={profileImage} alt={user.name}/>
        <input type="file" onChange={changeProfileImage}/>
        <button onClick={() => updateInfo({name, email, profileImage})}>update</button>
      </InfoBlock>
    </CardsLayout>
  );
}
export default Info
