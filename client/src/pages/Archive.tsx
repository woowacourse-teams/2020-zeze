import React from "react";
import SidebarLayout from "../components/common/SidebarLayout";
import Cards from "../components/common/Cards";
import Toast from "../components/common/Toast";

const Archive: React.FC = () => (
  <SidebarLayout>
    <Toast type="warn" message="Currently in development. Sorry for your inconvenience :("/>
    <Cards title="Archive"/>
  </SidebarLayout>
);

export default Archive;
