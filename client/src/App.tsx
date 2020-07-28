import React, {Suspense} from "react";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Spinner from "./components/common/Spinner";

const Home = React.lazy(() => import("./components/home"));
const Me = React.lazy(() => import("./components/me"));
const Editor = React.lazy(() => import("./components/editor"));
const Archive = React.lazy(() => import("./components/archive"));
const Error = React.lazy(() => import("./components/error"));

const App: React.FC = () => (
  <BrowserRouter>
    <Suspense fallback={<Spinner/>}>
      <Switch>
        <Route exact path="/" component={Home}/>
        <Route path="/me" component={Me}/>
        <Route path="/editor" component={Editor}/>
        <Route path="/archive" component={Archive}/>
        <Route component={Error}/>
      </Switch>
    </Suspense>
  </BrowserRouter>
);

export default App;
