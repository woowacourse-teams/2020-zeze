import React, {useEffect} from "react";
import {useSetRecoilState} from "recoil";
import SidebarLayout from "../components/common/SidebarLayout";
import {googleAnalyticsPageView} from "../utils/googleAnalytics";
import SlidesLayout from "../components/common/SlidesLayout";
import slideApi from "../api/slide";
import {sidebarVisibility} from "../store/atoms";

const PublicSlides: React.FC = () => {
  const setVisibility = useSetRecoilState(sidebarVisibility);

  useEffect(() => {
    googleAnalyticsPageView("Public Slides");
    setVisibility(true);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <SidebarLayout>
      <SlidesLayout getAllSlides={slideApi.getPublic} slidesCnt={9} title="Public Slides"/>
    </SidebarLayout>
  );
};

export default PublicSlides;
