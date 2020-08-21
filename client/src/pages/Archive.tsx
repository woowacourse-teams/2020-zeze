import React, {useEffect} from "react";
import SidebarLayout from "../components/common/SidebarLayout";
import {googleAnalyticsPageView} from "../utils/googleAnalytics";
import SlidesLayout from "../components/common/SlidesLayout";
import slideApi from "../api/slide";

const Archive: React.FC = () => {
  useEffect(() => {
    googleAnalyticsPageView("Archive");
  }, []);

  return (
    <SidebarLayout>
      <SlidesLayout getAllSlides={slideApi.getPublic} slidesCnt={9} title="Archive"/>
    </SidebarLayout>
  );
};

export default Archive;
