import React, {LazyExoticComponent} from "react";
import {useRecoilValue} from "recoil";
import {userInfoQuery} from "../store/atoms";
import { Route, Redirect } from "react-router-dom";

interface IProps {
  path: string,
  exact?: boolean,
  component: LazyExoticComponent<React.FC<{}>>
}

const Auth: React.FC<IProps> = ({path, exact, component}: IProps) => {
  const userSelector = useRecoilValue(userInfoQuery);

  return (
      userSelector ? <Route path={path} exact={exact || false} component={component}/> : <Redirect to={"/"}/>
  );
};

export default Auth;
