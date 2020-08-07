import React from "react";
import {Link, NavLink} from "react-router-dom";
import SidebarHeader from "./SidebarHeader";
import SidebarMenu from "./SidebarMenu";
import * as S from "../../assets/icons";

const SidebarNav: React.FC = React.memo(() => (
  <nav>
    <div>
      <SidebarHeader src={undefined} title="Hodol"/>
      <NavLink to="/me" activeClassName="current">
        <SidebarMenu src={S.slide} title="All Slides"/>
      </NavLink>
      <NavLink to="/archive" activeClassName="current">
        <SidebarMenu src={S.archive} title="Archive"/>
      </NavLink>
    </div>
    <Link to="/editor">
      <SidebarMenu src={S.newSlides} title="New Slides"/>
    </Link>
  </nav>
));

export default SidebarNav;
