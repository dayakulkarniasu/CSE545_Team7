import React from "react";
import Paper from "@material-ui/core/Paper";
import { makeStyles } from '@material-ui/core/styles';

import { Typography } from "@material-ui/core";

import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

import Button from '@material-ui/core/Button';

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
    formControl: {
        margin: theme.spacing(1),
        minWidth: 130,
    },
}));

export default function CreateAccount() {
    const classes = useStyles();
    const [type, setType] = React.useState('');

    const handleTypeChange = event => {
        setType(event.target.value);
    };

    return (
        <div className="CreateAccount">
            <Paper elevation={0} className={classes.root}>
                <Typography variant="h4">
                    Apply for a new account today!
                </Typography>
                <FormControl required className={classes.formControl}>
                    <InputLabel id="demo-simple-select-label">Account Type</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        value={type}
                        onChange={handleTypeChange}
                    >
                        <MenuItem value={10}>Checking</MenuItem>
                        <MenuItem value={20}>Savings</MenuItem>
                        <MenuItem value={30}>Credit</MenuItem>
                    </Select>
                </FormControl>

                <br />
                <br />
                <Button variant="contained" color="primary">
                    Request
                </Button>
            </Paper>
        </div>
    );
}