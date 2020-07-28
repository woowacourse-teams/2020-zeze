import React from "react";
import {Link, NavLink} from "react-router-dom";
import * as S from "./assets";
import SidebarMenu from "./SidebarMenu";
import SidebarHeader from "./SidebarHeader";

interface IProps {
  fluid?: boolean
}

const SidebarLayout: React.FC<IProps> = ({children, fluid = false}) => (
  <S.SidebarLayout fluid={fluid}>
    <nav>
      <div>
        <SidebarHeader src={undefined} title="Hodol"/>
        {/* <SidebarMenu to="/me" src={S.recent} title="Recent"/>*/}
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
    <main>
      <div>
        {children}
      </div>
    </main>
  </S.SidebarLayout>
);

export default SidebarLayout;
