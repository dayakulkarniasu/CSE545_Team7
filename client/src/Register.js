import React from "react";
import Paper from "@material-ui/core/Paper";
import { makeStyles } from '@material-ui/core/styles';

import { Typography } from "@material-ui/core";
import TextField from '@material-ui/core/TextField';

import Grid from '@material-ui/core/Grid';
import DateFnsUtils from '@date-io/date-fns';
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
} from '@material-ui/pickers';

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
        width: "70%",
        overflowX: "auto",
        marginBottom: theme.spacing(2),
        margin: "auto"
    },
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
}));

export default function Register() {
    const classes = useStyles();

    const [selectedDate, setSelectedDate] = React.useState(new Date('2014-08-18T21:11:54'));
    const [type, setType] = React.useState('');

    const handleDateChange = date => {
        setSelectedDate(date);
    };

    const handleTypeChange = event => {
        setType(event.target.value);
    };

    return (
        <div className="Register">
            <Paper elevation={0} className={classes.root}>
                <Typography variant="h4">
                    Register a New User
                    </Typography>
                <div>
                    <TextField required id="standard-required" label="First Name" /> &nbsp;&nbsp;&nbsp;
                    <TextField required id="standard-required" label="Last Name" />
                    <br />
                    <TextField required id="standard-required" helperText="e.g. john@abc.com" label="Email" /> &nbsp;&nbsp;&nbsp;
                    <TextField required id="standard-required" label="Password" />
                    <br />
                    <TextField required id="standard-required" helperText="e.g. 123-456-7890" label="Phone" /> &nbsp;&nbsp;&nbsp;
                    <TextField required id="standard-required" helperText="e.g. 123-45-6789" label="Social Security #" />
                    <br />
                    <FormControl required className={classes.formControl}>
                        <InputLabel id="demo-simple-select-label">Role</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={type}
                            onChange={handleTypeChange}
                        >
                            <MenuItem value={10}>Admin</MenuItem>
                            <MenuItem value={20}>Customer</MenuItem>
                            <MenuItem value={30}>Tier 1</MenuItem>
                            <MenuItem value={40}>Tier 2</MenuItem>
                            <MenuItem value={50}>Tier 3</MenuItem>
                        </Select>
                    </FormControl>
                    <br />
                    <MuiPickersUtilsProvider utils={DateFnsUtils}>
                        <Grid container justify="space-around">
                            <KeyboardDatePicker
                                disableToolbar
                                variant="inline"
                                format="MM/dd/yyyy"
                                margin="normal"
                                required id="date-picker-inline"
                                label="Date picker inline"
                                value={selectedDate}
                                onChange={handleDateChange}
                                KeyboardButtonProps={{
                                    'aria-label': 'change date',
                                }}
                            />
                        </Grid>
                    </MuiPickersUtilsProvider>
                    <TextField required id="standard-required" label="Address" />
                    <br />
                    <br />
                    <Button variant="contained" color="primary">
                        Submit
                    </Button>
                </div>
            </Paper>
        </div>
    );
}