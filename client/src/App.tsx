import React, {Suspense} from "react";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Spinner from "./components/common/Spinner";

const Home = React.lazy(() => import("./pages/Home"));
const Me = React.lazy(() => import("./pages/Me"));
const Editor = React.lazy(() => import("./pages/Editor"));
const Archive = React.lazy(() => import("./pages/Archive"));
const Login = React.lazy(() => import("./pages/Login"));
const Error = React.lazy(() => import("./pages/Error"));
const Callback = React.lazy(() => import("./pages/Callback"));

const App: React.FC = () => (
  <BrowserRouter>
    <Suspense fallback={<Spinner/>}>
      <Switch>
        <Route exact path="/" component={Home}/>
        <Route path="/me" component={Me}/>
        <Route exact path="/editor" component={Editor}/>
        <Route path="/editor/:id" component={Editor}/>
        <Route path="/archive" component={Archive}/>
        <Route path="/login" component={Login}/>
        <Route path="/callback" component={Callback}/>
        <Route component={Error}/>
      </Switch>
    </Suspense>
  </BrowserRouter>
);

export default App;
