import React from "react";
import Typography from "@material-ui/core/Typography";
import Paper from "@material-ui/core/Paper";
import "./App.css";
import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
        marginTop: theme.spacing(3),
        marginBottom: theme.spacing(2),
    }
});

class Home extends React.Component {
    constructor(props) {
        super(props);
        return this;
    }

    render() {
        const { classes } = this.props;
        return (
            <div className="Home">
                <Paper elevation={0} className={classes.root}>
                    <Typography variant="h4">
                        Welcome to Sparky's Secure Bank! <br></br>
                        Where privacy and security are our #1 priority.
                    </Typography>
                </Paper>
            </div>
        );
    }
}

export default withStyles(styles)(Home);