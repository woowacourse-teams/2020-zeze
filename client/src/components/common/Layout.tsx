import React from "react";
import Header from "./Header";
import Footer from "./Footer";

interface IProps {
  header?: boolean;
  footer?: boolean;
}
const Layout: React.FC<IProps> = ({children, header = true, footer = true}) => (
  <div>
    <Header/>
    {children}
    {footer ? <Footer/> : null}
  </div>
);

export default Layout;
