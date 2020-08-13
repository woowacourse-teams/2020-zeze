import React from "react";
import {useRecoilValue} from "recoil";

import SidebarLayout from "../components/common/SidebarLayout";
import Cards from "../components/common/Cards";

import {getAllSlidesQuery} from "../store/atoms";

const Archive: React.FC = () => {
  const slides = useRecoilValue(getAllSlidesQuery);

  return (
    <SidebarLayout>
      <Cards title="Archive" slides={slides} author={"zeze"}/>
    </SidebarLayout>
  );
};

export default Archive;
