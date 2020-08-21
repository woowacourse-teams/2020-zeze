import React, {useEffect} from "react";
import {useSetRecoilState} from "recoil";
import SidebarLayout from "../components/common/SidebarLayout";
import {googleAnalyticsPageView} from "../utils/googleAnalytics";
import SlidesLayout from "../components/common/SlidesLayout";
import slideApi from "../api/slide";
import {sidebarVisibility} from "../store/atoms";

const Archive: React.FC = () => {
  const setVisibility = useSetRecoilState(sidebarVisibility);

  useEffect(() => {
    googleAnalyticsPageView("Archive");
    setVisibility(true);
  }, [setVisibility]);

  return (
    <SidebarLayout>
      <SlidesLayout getAllSlides={slideApi.getPublic} slidesCnt={9} title="Archive"/>
    </SidebarLayout>
  );
};

export default Archive;
