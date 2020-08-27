import React from "react";
import {Link, NavLink} from "react-router-dom";
import SidebarHeader from "./SidebarHeader";
import SidebarMenu from "./SidebarMenu";
import {User} from "../../pages/Me";
import {useSetRecoilState} from "recoil";
import {sidebarVisibility} from "../../store/atoms";

interface IProps {
  user: User
}

const SidebarNav: React.FC<IProps> = ({user}: IProps) => {
  const setVisibility = useSetRecoilState(sidebarVisibility);

  return (
    <nav className="sidebar">
      <div>
        <SidebarHeader src={user.profileImage} title={user.name} />
        <NavLink to="/me" activeClassName="current">
          <SidebarMenu src="/assets/icons/slide.svg" title="My Slides" />
        </NavLink>
        <NavLink to="/archive" activeClassName="current">
          <SidebarMenu src="/assets/icons/publicSlides.svg" title="Public Slides" />
        </NavLink>
      </div>
      <Link to="/editor" onClick={() => setVisibility(false)}>
        <SidebarMenu src="/assets/icons/newSlides.svg" title="New Slides" />
      </Link>
    </nav>
  );
};

export default React.memo(SidebarNav);
