import React from "react";
import dotenv from 'dotenv';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
const Home = React.lazy(() => import("./pages/Home"));
const Me = React.lazy(() => import("./pages/Me"));
const Editor = React.lazy(() => import("./pages/Editor"));
const Archive = React.lazy(() => import("./pages/Archive"));
const Callback = React.lazy(() => import("./pages/Callback"));
const Error = React.lazy(() => import("./pages/Error"));

dotenv.config();

const App: React.FC = () => {

  return(
    <Router>
      <Switch>
        <Route exact path="/" component={Home}/>
        <Route path="/me" component={Me}/>
        <Route exact path="/editor" component={Editor}/>
        <Route path="/editor/:id" component={Editor}/>
        <Route path="/archive" component={Archive}/>
        <Route path="/callback" component={Callback}/>
        <Route component={Error}/>
      </Switch>
    </Router>
  );
}

export default App;
