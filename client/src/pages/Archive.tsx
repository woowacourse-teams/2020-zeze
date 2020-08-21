import React, {useEffect} from "react";
import {useSetRecoilState} from "recoil";

import SidebarLayout from "../components/common/SidebarLayout";
import SlidesLayout from "../components/common/SlidesLayout";

import {sidebarVisibility} from "../store/atoms";
import {googleAnalyticsPageView} from "../utils/googleAnalytics";

const Archive: React.FC = () => {
  const setVisibility = useSetRecoilState(sidebarVisibility);

  useEffect(() => {
    googleAnalyticsPageView("Archive");
    setVisibility(true);
  }, [setVisibility]);

  return (
    <SidebarLayout>
      <SlidesLayout slidesCnt={9} title="Archive"/>
    </SidebarLayout>
  );
};

export default Archive;
