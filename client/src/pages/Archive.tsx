import React from "react";
import {useRecoilValue} from "recoil";

import SidebarLayout from "../components/common/SidebarLayout";
import Cards from "../components/common/Cards";

import {getPublicSlidesQuery} from "../store/atoms";

const Archive: React.FC = () => {
  const slides = useRecoilValue(getPublicSlidesQuery);

  return (
    <SidebarLayout>
      <Cards title="Archive" slides={slides} author={"zeze"}/>
    </SidebarLayout>
  );
};

export default Archive;
