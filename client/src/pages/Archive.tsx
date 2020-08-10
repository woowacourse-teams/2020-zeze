import React from "react";
import {useRecoilValue} from "recoil";

import SidebarLayout from "../components/common/SidebarLayout";
import Cards from "../components/common/Cards";
import Toast from "../components/common/Toast";

import {getAllSlidesQuery} from "../store/atoms";

const Archive: React.FC = () => {
  const slides = useRecoilValue(getAllSlidesQuery);

  return (
    <SidebarLayout>
      <Toast type="warn" message="Currently in development. Sorry for your inconvenience :("/>
      <Cards title="Archive" slides={slides} author={"zeze"}/>
    </SidebarLayout>
  );
};

export default Archive;
