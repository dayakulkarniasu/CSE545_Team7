import React from "react";
import Paper from "@material-ui/core/Paper";
import { makeStyles } from '@material-ui/core/styles';

import { Typography } from "@material-ui/core";

import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';

import Button from '@material-ui/core/Button';

import CreateAccount from './CreateAccount';

const useStyles = makeStyles(theme => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
        marginTop: theme.spacing(3),
        width: "60%",
        overflowX: "auto",
        marginBottom: theme.spacing(2),
        margin: "auto"
    },
}));

export default function AccountsView() {
    const classes = useStyles();

    return (
        <div className="AccountsView">
            <Paper elevation={0} className={classes.root}>
                <Typography variant="h4">
                    hihihihihi
                </Typography>
            </Paper>
        </div>
    );
}