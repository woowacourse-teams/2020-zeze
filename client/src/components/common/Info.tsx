import React, {useState} from "react";
import {User} from "../../pages/Me";
import {useRecoilState} from "recoil";
import {CardsLayout} from "./Cards";
import styled from "@emotion/styled";

const InfoBlock = styled.div`
  display: flex;
  flex-direction: column;
  
  > input {
  }
  
  > button {
  
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

  const changeName = (target: {value: string}) => {
    setName(target.value);
  }

  return (
    <CardsLayout>
      <h2>{user.name}'s Info</h2>
      <hr/>
      <InfoBlock>
        <div>NAME</div>
        <input placeholder={user.name}/>
        <div>EMAIL</div>
        <input placeholder={user.email}/>
        <div>IMAGE</div>
        {/*upload*/}
        <button onClick={() => updateInfo({name, email, profileImage})}>update</button>
      </InfoBlock>
    </CardsLayout>
  );
}
export default Info
