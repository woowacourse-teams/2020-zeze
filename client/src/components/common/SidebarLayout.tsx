import React from "react";
import styled from "@emotion/styled";
import {MOBILE_MAX_WIDTH, ZEZE_GRAY} from "../../domains/constants";
import SidebarNav from "./SidebarNav";
import {useRecoilState, useRecoilValue} from "recoil";
import {sidebarVisibility, userInfoQuery} from "../../store/atoms";

interface SidebarLayoutProps {
  fluid?: boolean
  toggleable?: boolean
  visible?: boolean
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
    display: ${props => (props.visible ? "flex" : "none")};
    z-index: ${props => (props.toggleable ? 99999 : 1)};
    flex-direction: column;
    justify-content: space-between;
    position: fixed;
    
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
    padding-left: ${({toggleable}) => (toggleable ? 0 : 275)}px;
    box-sizing: border-box;
    min-height: 100vh;
    position: relative;
    
    > div.dropback {
      display: ${({toggleable, visible}) => (toggleable && visible ? "block" : "none")};
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      z-index: 4;
      background-color: rgba(0,0,0,0.9);
    }
    
    > div.content {
      height: 100%;
      padding: 0 ${props => (props.fluid ? 0 : 30)}px;
    }
    
    @media (max-width: ${MOBILE_MAX_WIDTH}px) {
      padding-left: 0;
    }
  }
`;

interface IProps {
  fluid?: boolean;
  toggleable?: boolean;
}

const SidebarLayout: React.FC<IProps> = ({children, fluid = false, toggleable = false}) => {
  const user = useRecoilValue(userInfoQuery);
  const [visibility, setVisibility] = useRecoilState(sidebarVisibility);

  return (
    <SidebarLayoutBlock fluid={fluid} toggleable={toggleable} visible={visibility}>
      <SidebarNav user={user!} />
      <main>
        <div className="dropback" onClick={() => setVisibility(false)}/>
        <div className="content">
          {children}
        </div>
      </main>
    </SidebarLayoutBlock>
  );
};

export default SidebarLayout;
