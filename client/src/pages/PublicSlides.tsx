import React, {useEffect} from "react";
import {useSetRecoilState} from "recoil";
import SidebarLayout from "../components/common/SidebarLayout";
import {googleAnalyticsPageView} from "../utils/googleAnalytics";
import SlidesLayout from "../components/common/SlidesLayout";
import slideApi from "../api/slide";
import {sidebarVisibility} from "../store/atoms";
import Toast from "../components/common/Toast";

const PublicSlides: React.FC = () => {
  const setVisibility = useSetRecoilState(sidebarVisibility);

  useEffect(() => {
    googleAnalyticsPageView("Public Slides");
    setVisibility(true);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <SidebarLayout>
      <Toast type="warn">Still in development. Please report bugs to our <a href="https://github.com/woowacourse-teams/2020-zeze/issues/new?assignees=&labels=🐞%20bug%20report&template=bug_report.md">Github Issues</a> !</Toast>
      <SlidesLayout getAllSlides={slideApi.getPublic} slidesCnt={9} title="Public Slides"/>
    </SidebarLayout>
  );
};

export default PublicSlides;
