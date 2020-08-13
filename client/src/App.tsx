import React, {useEffect} from "react";
import dotenv from 'dotenv';
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import {useRecoilState} from "recoil";
import {userInfoState} from "./store/atoms";
import usersApi from "./api/user";

const Home = React.lazy(() => import("./pages/Home"));
const Me = React.lazy(() => import("./pages/Me"));
const Editor = React.lazy(() => import("./pages/Editor"));
const Archive = React.lazy(() => import("./pages/Archive"));
const Callback = React.lazy(() => import("./pages/Callback"));
const Error = React.lazy(() => import("./pages/Error"));

dotenv.config();

const App: React.FC = () => {
  const [user, setUser] = useRecoilState(userInfoState);

  useEffect(() => {
    usersApi.get()
      .then(response => setUser(response.data))
      .catch(() => setUser(null));
  }, []);

  return(
    <Router>
      <Switch>
        <Route exact path="/" component={Home}/>
        <Route path="/callback" component={Callback}/>
        { user && <Route path="/me" component={Me}/> || <Redirect to={"/"}/>}
        { user && <Route exact path="/editor" component={Editor}/> || <Redirect to={"/"}/>}
        { user && <Route path="/editor/:id" component={Editor}/> || <Redirect to={"/"}/>}
        { user && <Route path="/archive" component={Archive}/> || <Redirect to={"/"}/>}
        <Route component={Error}/>
      </Switch>
    </Router>
  );
};

export default App;
