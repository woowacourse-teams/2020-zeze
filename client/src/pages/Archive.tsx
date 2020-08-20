import React from "react"
import SidebarLayout from "../components/common/SidebarLayout";
import SlidesLayout from "../components/common/SlidesLayout";

const Archive: React.FC = () => {
  return (
    <SidebarLayout>
      <SlidesLayout slidesCnt={9} title="Archive"/>
    </SidebarLayout>
  );
};

export default Archive;
