import React from "react";
import SidebarLayout from "../common/SidebarLayout";
import Cards from "../common/Cards";
import Toast from "../common/Toast";

const Me = () => (
  <SidebarLayout>
    <Toast type="warn" message="Currently in development. Sorry for your inconvenience :("/>
    <Cards title="Recent"/>
    <Cards title="My Drafts"/>
  </SidebarLayout>
);

export default Me;
