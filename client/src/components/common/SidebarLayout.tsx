import React from "react";
import {Link, NavLink} from "react-router-dom";
import styled from "@emotion/styled";
import * as S from "../../assets/icons";
import SidebarMenu from "./SidebarMenu";
import SidebarHeader from "./SidebarHeader";
import {MOBILE_MAX_WIDTH, ZEZE_GRAY} from "../../domains/constants";

interface SidebarLayoutProps {
  fluid?: boolean
}

export const SidebarLayoutBlock = styled.div<SidebarLayoutProps>`
  display: flex;
  
  > nav {
    background-color: #222;
    color: #fff;
    box-sizing: border-box;
    padding: 30px;
    min-width: 275px; 
    height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    position: fixed;
    z-index: 1;
    
    a {
      color: #fff;
      text-decoration: none;
    }
    
    a.current {
      font-weight: bold;
    }
    
    @media(max-width: ${MOBILE_MAX_WIDTH}px ) {
      display: none;
    }
  }
  
  > main {
    background-color: ${ZEZE_GRAY};
    flex: 1;
    width: 100%;
    padding-left: 275px;
    box-sizing: border-box;
    min-height: 100vh;
    
    > div {
      height: 100%;
      padding: ${props => (props.fluid ? 0 : 30)}px;
    }
    
    @media (max-width: ${MOBILE_MAX_WIDTH}px) {
      padding-left: 0;
    }
  }
`;

interface IProps {
  fluid?: boolean
}

const SidebarLayout: React.FC<IProps> = ({children, fluid = false}) => (
  <SidebarLayoutBlock fluid={fluid}>
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
    <main>
      <div>
        {children}
      </div>
    </main>
  </SidebarLayoutBlock>
);

export default SidebarLayout;
