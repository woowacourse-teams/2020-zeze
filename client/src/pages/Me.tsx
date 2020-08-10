import React, {useEffect, useState} from "react";
import SidebarLayout from "../components/common/SidebarLayout";
import Toast from "../components/common/Toast";
import usersApi from "../api/user";
import slideApi, {SlideResponse} from "../api/slide";
import Info from "../components/common/Info";

export interface User {
  user: {
    name: string,
    email: string,
    profileImage: string
  }
}

const Me: React.FC = () => {
  const [slides, setSlides] = useState<Array<SlideResponse>>([]);
  const [user, setUser] = useState<User>();
  //
  useEffect(() => {
    usersApi.get()
      .then(response => console.log(response.data))
      // .then(response => setUser(response.data ? response.data : null));
    slideApi.getAll({id: 0, size: 5})
      .then(res => setSlides(res.data.values));
  },[]);

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
