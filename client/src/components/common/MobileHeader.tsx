import React from "react";
import styled from "@emotion/styled";
import {Link, NavLink} from "react-router-dom";

import {MOBILE_MAX_WIDTH} from "../../domains/constants";
import Plus from "../../assets/icons/plus.svg";

const MobileHeaderBlock = styled.nav`
  display: none;
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    padding: 20px 30px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    
    > div.menu {
      display: flex;
      align-items: center;
      
      > a {
        display: block;
        font-size: 2rem;
        text-decoration: none;
        padding-right: 1rem;
        margin-right: 1rem;
        padding-bottom: 0.5rem;
        color: #555;
        font-weight: 700;
        
        &.active {
          color: #fff; 
          border-bottom: 3px solid #fff
        }
      }
    }
    
    > a {
      text-decoration: none;
      display: block;
      
      > img {
        width: 30px;
      }
    }
  }
`;

const MobileHeader: React.FC = () => (
  <MobileHeaderBlock>
    <div className="menu">
      <NavLink to="/me" activeClassName="active">My</NavLink>
      <NavLink to="/archive" activeClassName="active">Public</NavLink>
    </div>
    <Link to="/editor"><img src={Plus} alt="new slide"/></Link>
  </MobileHeaderBlock>
);

export default React.memo(MobileHeader);
