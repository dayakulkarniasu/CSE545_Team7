import React from 'react';
import PropTypes from "prop-types";
import "./App.css";
import { withStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";
import Paper from "@material-ui/core/Paper";

import Home from './Home';
import Users from './Users';
import Accounts from './Accounts';
import CreateAccount from './CreateAccount';
import Register from './Register';
import RequestTransfer from './RequestTransfer';

import { Link as RouterLink } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import { MemoryRouter as Router } from 'react-router';

const styles = theme => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
        marginTop: theme.spacing(3),
        marginBottom: theme.spacing(2),
    }
});

class App extends React.Component {
    state = {
        value: 0
    };

    constructor(props) {
        super(props);
        return this;
    }

    handleChange = (event, value) => {
        this.setState({ value });
    };

    render() {
        const { classes } = this.props;
        const { value } = this.state;

        return (
            <div className="App">
                <Paper elevation={0} className={classes.root}>
                    <Typography variant="h3">Sparky's Secure Bank</Typography>
                </Paper>
                {/* <Router>
                    <div>
                        <Button color="primary" component={Home} to="/">
                            Home
                        </Button>
                    </div>
                </Router> */}
                <AppBar position="static">
                    <Tabs value={value} onChange={this.handleChange} centered>
                        <Tab label="Home"/>
                        <Tab label="Accounts" />
                        {/* <Tab label="Create New Account" /> */}
                        <Tab label="Users" />
                        <Tab label="Request Transfer" />
                        <Tab label="Approve Transfer" />
                        <Tab label="Login" />
                        {/* <Tab label="Register" /> */}
                    </Tabs>
                </AppBar>
                {value === 0 && <Paper component={Home}></Paper>}
                {value === 1 && <Paper component={Accounts}></Paper>}
                {/* {value === 2 && <Paper component={CreateAccount}></Paper>} */}
                {value === 2 && <Paper component={Users}></Paper>}
                {value === 3 && <Paper component={RequestTransfer}></Paper>}
                {/* {value === 7 && <Paper component={Register}></Paper>} */}
            </div>
        );
    }
}

App.propTypes = {
    classes: PropTypes.object.isRequired
};

export default withStyles(styles)(App);