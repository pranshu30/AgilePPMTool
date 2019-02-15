import React, { Component } from "react";
//import logo from "./logo.svg";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import AddProject from "./components/Project/AddProject";
import { Provider } from "react-redux";
import store from "./store";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/ProjectTask/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTask/UpdateProjectTask";
import Landing from "./components/Layout/Landing";
import Register from "./components/UserManagement/Register";
import Login from "./components/UserManagement/Login";

import jwt_decode from "jwt-decode";
import setJWTToken from "./components/securityUtils/setJWTToken";
import { SET_CURRENT_USER } from "./actions/types";
import { logout } from "./actions/securityActions";
import SecuredRoute from "./components/securityUtils/SecureRoute";

const jwtToken = localStorage.jwtToken;

if (jwtToken) {
  setJWTToken(jwtToken);
  const decoded_jwtToken = jwt_decode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decoded_jwtToken
  });

  const currentTime = Date.now() / 1000;
  if (decoded_jwtToken.exp < currentTime) {
    //handle logout , store is redux store
    store.dispatch(logout());
    window.location.href = "/";
  }
}

class App extends Component {
  render() {
    return (
      //Provider uses a prop i.e store
      <Provider store={store}>
        <Router>
          <div className="App">
            <Header />
            {
              //Public route
            }

            <Route exact path="/" component={Landing} />
            <Route exact path="/signup" component={Register} />
            <Route exact path="/login" component={Login} />

            {
              //Private Route
            }
            <Switch>
              <SecuredRoute exact path="/dashboard" component={Dashboard} />
              <SecuredRoute exact path="/addProject" component={AddProject} />
              <SecuredRoute
                exact
                path="/updateProject/:id"
                component={UpdateProject}
              />
              <SecuredRoute
                exact
                path="/projectBoard/:id"
                component={ProjectBoard}
              />
              <SecuredRoute
                exact
                path="/addProjectTask/:id"
                component={AddProjectTask}
              />
              <SecuredRoute
                exact
                path="/updateProjectTask/:backlog_id/:pt_id"
                component={UpdateProjectTask}
              />
            </Switch>
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;
