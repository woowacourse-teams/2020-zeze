import React from "react";
import {BrowserRouter, Switch, Route} from "react-router-dom";

import Home from "./components/home";
import Me from "./components/me";
import Editor from "./components/editor";
import Archive from "./components/archive";
import Error from "./components/error";

const App: React.FC = () => (
  <BrowserRouter>
    <Switch>
      <Route exact path="/" component={Home} />
      <Route path="/me" component={Me} />
      <Route path="/editor" component={Editor} />
      <Route path="/archive" component={Archive} />
      <Route component={Error} />
    </Switch>
  </BrowserRouter>
);

export default App;
