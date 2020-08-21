import React, {useEffect} from "react";
import {useRecoilValue, useSetRecoilState} from "recoil";

import SidebarLayout from "../components/common/SidebarLayout";
import Cards from "../components/common/Cards";

import {getAllSlidesQuery, sidebarVisibility} from "../store/atoms";
import {googleAnalyticsPageView} from "../utils/googleAnalytics";

const Archive: React.FC = () => {
  const slides = useRecoilValue(getAllSlidesQuery);
  const setVisibility = useSetRecoilState(sidebarVisibility);

  useEffect(() => {
    googleAnalyticsPageView("Archive");
    setVisibility(true);
  }, [setVisibility]);

  return (
    <SidebarLayout>
      <Cards title="Archive" slides={slides} author={"zeze"}/>
    </SidebarLayout>
  );
};

export default Archive;
