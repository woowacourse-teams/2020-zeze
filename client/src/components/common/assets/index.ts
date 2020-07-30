import styled from "@emotion/styled";
import {MAX_WIDTH, MOBILE_MAX_WIDTH, PRIMARY_GREEN} from "../../../domains/constants";
import logo from "./logo.svg";
import menu from "./menu.svg";
import user from "./user.svg";
import play from "./play.svg";
import recent from "./recent.svg";
import slide from "./slide.svg";
import archive from "./archive.svg";
import newSlides from "./newSlides.svg";
import more from "./more.svg";
import {applyTheme, Theme} from "../theme";


export {
  logo,
  menu,
  user,
  recent,
  slide,
  archive,
  newSlides,
  more,
};

export const Layout = styled.main`
  max-width: ${MAX_WIDTH}px;
  margin: 0 auto;
`;

export const Header = styled.header`
  position: sticky;
  height: 70px;
  background-color: ${PRIMARY_GREEN};
  border-bottom: 0.5px solid ${PRIMARY_GREEN};
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    height: 50px;
  }
  
  nav {
    height: 100%;
    // max-width: ${MAX_WIDTH + 25}px;
    box-sizing: border-box;
    margin: 0 auto;
    display: flex;
    //justify-content: space-between;
    justify-content: center;
    align-items: center;
  }
`;

export const LogoIcon = styled.img`
  cursor: pointer;
  width: 30px;
  padding: 20px;
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    padding: 10px;
  }
`;

export const HeaderIcon = styled.img`
  cursor: pointer;
  width: 20px;
  padding: 20px 30px;
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    padding: 15px;
  }
`;

interface SidebarLayoutProps {
  fluid?: boolean
}

export const SidebarLayout = styled.div<SidebarLayoutProps>`
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
    background-color: ${PRIMARY_GREEN};
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

export const SidebarHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding-bottom: 1rem;
  border-bottom: 2px solid #aaa;
  margin-bottom: 1rem;
  
  > div {
    font-size: 1.4rem;
    display: flex;
    align-items: center;
    flex: 5;
    
    > img {
      width: 1.4rem;
      padding-right: 10px;
    }
  }
  
  > button {
    border: none;
    background: transparent;
    flex: 1;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    
    &:focus {
      outline: none;
    }
    
    > img {
      width: 0.75rem;
    } 
  }
`;

export const SidebarMenu = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  padding: 7.5px 0;
  cursor: pointer;
  
  &:hover {
    opacity: 75%;
  }
  
  > img {
    width: 1rem;
    padding-right: 10px;
  }
`;

export const Footer = styled.footer`
  padding: 40px 15px;
  background-color: ${PRIMARY_GREEN};
  
  > div {
    max-width: ${MAX_WIDTH}px;
    margin: 0 auto;
    text-align: center;
    font-size: 0.75rem;
    color: #fff;
  }
`;

interface FullScreenProps {
  slideTheme: Theme;
}

export const FullScreen = styled.div<FullScreenProps>`
  position: absolute;
  top: -9999px;
  left: -9999px;
  font-size: 100%;
  cursor: none;
  
  > div#themed {
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    font-size: 1em;
    
    &:focus {
      outline: 0 solid transparent;
    }
    
    * {
      margin: 0;
    }
    
    @media (max-width: ${MOBILE_MAX_WIDTH}px) {
      font-size: 0.5em;
    }
    
    ${({slideTheme}) => applyTheme(slideTheme)}
  }
`;

export const CardsLayout = styled.div`
  padding-bottom: 3rem;

  > h2 {
    color: #fff;
    margin: 0;
    display: inline-block;
    font-size: 2rem;
  }
  
  > hr {
    border-bottom: 1px solid #777;
    width: 7rem;
    margin: 1rem 0 2rem;
  }
`;

export const Cards = styled.div`
  display: grid;
  grid-gap: 20px;
  grid-template-columns: repeat(4, 1fr);
  
  a {
    text-decoration: none;
    color: #000;  
  }
  
  @media (max-width: 1050px) {
    grid-template-columns: repeat(3, 1fr);
  }
  
  @media (max-width: ${MOBILE_MAX_WIDTH}px) {
    grid-template-columns: repeat(2, 1fr);
  }
`;

export const Card = styled.div`
  background: #fff;
  border-radius: 5px;
  height: 200px;
  position: relative;
  box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
  transition: all 0.3s cubic-bezier(.25,.8,.25,1);
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  
  &:hover {
    box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
    cursor: pointer;
  }
  
  > header {
    border-radius: 5px 5px 0 0;
    height: 12px;
    background-color: springgreen;
  }
  
  > main {
    padding: 15px;
    
    > div.title {
      font-size: 1.75rem;
      margin-bottom: 0.25rem;
    }
    
    > div.subtitle {
      font-size: 0.815rem;
      margin-bottom: 1.5rem;
      color: ${PRIMARY_GREEN};
    }
    
    > div.author {
      font-size: 0.815rem;
    }
    
    > div.created_at {
      font-size: 0.815rem;
      color: gray;
    }
  }
`;

export const FullScreenButton = styled.button`
  background-image: url(${play});
  background-position: center;
  background-repeat: no-repeat;
  background-color: transparent;
  width: 25px;
  height: 25px;
  border: none;
  position: absolute;
  right: 20px;
  top: 20px;
  z-index: 3;
  cursor: pointer;
  
  &:focus {
    outline: none;
  }
`;

interface ToastProps {
  type: string;
}

export const Toast = styled.div<ToastProps>`
  background-color: ${({type}) => (type === "warn" ? "#121212" : "#fff")};
  border-radius: 5px;
  padding: 20px;
  margin: 0 0 20px;
  color: #fff;
  font-weight: 600;
  
  ::before {
    content: "⚠️";
    font-family: "Apple Color Emoji", serif;
    margin-right: 15px;
  }
`;
