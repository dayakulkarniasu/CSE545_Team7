import React from "react";
import Paper from "@material-ui/core/Paper";
import { makeStyles } from '@material-ui/core/styles';

import { Typography } from "@material-ui/core";

import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';

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

export default function RequestTransfer() {
    const classes = useStyles();
    const [type, setType] = React.useState('');

    const handleTypeChange = event => {
        setType(event.target.value);
    };

    return (
        <div className="CreateAccount">
            <Paper elevation={0} className={classes.root}>
                <Typography variant="h4">
                    Transfer Requests
                </Typography>
                <FormControl required className={classes.formControl}>
                    <InputLabel id="demo-simple-select-label">Transfer Type</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        value={type}
                        onChange={handleTypeChange}
                    >
                        <MenuItem value={10}>Between Accounts</MenuItem>
                        <MenuItem value={20}>Between Customers</MenuItem>
                        <MenuItem value={30}>Between Banks</MenuItem>
                    </Select>
                </FormControl>
                <br />
                <FormControl required className={classes.formControl}>
                    <InputLabel id="demo-simple-select-label">From</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        value={type}
                        onChange={handleTypeChange}
                    >
                    </Select>
                </FormControl>
                <br />
                <FormControl required className={classes.formControl}>
                    <InputLabel id="demo-simple-select-label">To</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        value={type}
                        onChange={handleTypeChange}
                    >
                    </Select>
                </FormControl>
                <br />
                <TextField required id="standard-required" label="Amount" />
                <br />
                <br />
                <br />
                <Button variant="contained" color="primary">
                    Request Transfer
                </Button>
            </Paper>
        </div>
    );
}