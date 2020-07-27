import React from "react";
import SidebarLayout from "../common/SidebarLayout";
import Cards from "../common/Cards";
import Toast from "../common/Toast";

const Archive = () => (
  <SidebarLayout>
    <Toast type="warn" message="Currently in development. Sorry for your inconvenience :("/>
    <Cards title="Archive"/>
  </SidebarLayout>
);

export default Archive;
