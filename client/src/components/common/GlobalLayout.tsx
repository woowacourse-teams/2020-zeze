import React from "react";
import Header from "./Header";
import Footer from "./Footer";

interface IProps {
  header?: React.ReactElement | null;
  footer?: React.ReactElement | null;
}

const GlobalLayout: React.FC<IProps> = ({children, header = <Header/>, footer = <Footer/>}) => (
  <div>
    {header}
    {children}
    {footer}
  </div>
);

export default GlobalLayout;
