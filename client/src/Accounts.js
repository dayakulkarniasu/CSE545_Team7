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
import history from './history';

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
                    Accounts
                </Typography>
                <br />
                <div>
                    <Button variant="contained" color="primary" onClick={() => history.push('/AccountsView')}>
                        Create New Account
                    </Button>
                    <br />

                    <Card className={classes.root}>
                        <CardMedia
                            className={classes.media}
                            image="/static/images/cards/contemplative-reptile.jpg"
                            title="Contemplative Reptile"
                        />
                        <CardContent>
                            <Typography gutterBottom variant="h5" component="h2">
                                Checking Account
                                </Typography>
                            <Typography variant="body2" color="textSecondary" component="p">
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar sic tempor. Sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus pronin sapien nunc accuan eget.
                                </Typography>
                        </CardContent>
                        <CardActions>
                            <Button size="small" color="primary">
                                Debit
                            </Button>
                            <Button size="small" color="primary">
                                Download Statement
                            </Button>
                        </CardActions>
                    </Card>
                    <br />
                    <Card className={classes.root}>
                        <CardMedia
                            className={classes.media}
                            image="/static/images/cards/contemplative-reptile.jpg"
                            title="Contemplative Reptile"
                        />
                        <CardContent>
                            <Typography gutterBottom variant="h5" component="h2">
                                Savings Account
                                </Typography>
                            <Typography variant="body2" color="textSecondary" component="p">
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar sic tempor. Sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus pronin sapien nunc accuan eget.
                                </Typography>
                        </CardContent>
                        <CardActions>
                            <Button size="small" color="primary">
                                Debit
                            </Button>
                            <Button size="small" color="primary">
                                Download Statement
                            </Button>
                        </CardActions>
                    </Card>
                    <br />
                    <Card className={classes.root}>
                        <CardMedia
                            className={classes.media}
                            image="/static/images/cards/contemplative-reptile.jpg"
                            title="Contemplative Reptile"
                        />
                        <CardContent>
                            <Typography gutterBottom variant="h5" component="h2">
                                Credit Account
                                </Typography>
                            <Typography variant="body2" color="textSecondary" component="p">
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar sic tempor. Sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus pronin sapien nunc accuan eget.
                                </Typography>
                        </CardContent>
                        <CardActions>
                            <Button size="small" color="primary">
                                Credit
                            </Button>
                            <Button size="small" color="primary">
                                Download Statement
                            </Button>
                        </CardActions>
                    </Card>
                </div>
            </Paper>
        </div>
    );
}