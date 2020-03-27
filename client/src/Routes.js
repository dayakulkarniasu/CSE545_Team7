import React, { Component } from "react";
import { Router, Switch, Route } from "react-router-dom";

import Home from "./Home";
import Accounts from "./Accounts";
import AccountsView from "./AccountsView";
import Users from "./Users";
import Login from "./Login";
import Transfers from './Transfers';
import history from './history';

export default class Routes extends Component {
    render() {
        return (
            <Router history={history}>
                <Switch>
                    <Route path="/" exact component={Home} />
                    <Route path="/Accounts" component={Accounts} />
                    <Route path="/Users" component={Users} />
                    <Route path="/Login" component={Login} />
                    <Route path="/Transfers" component={Transfers} />
                    <Route path="/AccountsView" component={AccountsView} />
                </Switch>
            </Router>
        )
    }
}