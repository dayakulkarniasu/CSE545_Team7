import React from "react";
import Paper from "@material-ui/core/Paper";
import { withStyles } from "@material-ui/core/styles";

import MaterialTable from 'material-table';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import { forwardRef } from 'react';
import ArrowUpward from '@material-ui/icons/ArrowUpward';
import EditIcon from '@material-ui/icons/Edit';

const styles = theme => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
        marginTop: theme.spacing(3),
        width: "70%",
        overflowX: "auto",
        marginBottom: theme.spacing(2),
        margin: "auto"
    },
    buttonRoot: {
        '& > *': {
            margin: theme.spacing(1),
        },
    },
    table: {
        maxWidth: '100%',
    },
    tableDiv: {
        marginTop: theme.spacing(3),
        marginLeft: theme.spacing(5),
    }
});

const tableIcons = {
    Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
    SortArrow: forwardRef((props, ref) => <ArrowUpward {...props} ref={ref} />),
    Edit: forwardRef((props, ref) => <EditIcon {...props} ref={ref} />),
};

class Users extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [
                { username: 'A', role: 'Tier 1' },
                { username: 'B', role: 'Customer' },
            ]
        }
    }

    render() {
        const { classes } = this.props;

        return (
            <div className="Users">
                <Paper elevation={0} className={classes.root}>
                    <MaterialTable
                        className={classes.root}
                        title="User Information"
                        icons={tableIcons}
                        columns={[
                            { title: 'Username', field: 'username' },
                            { title: 'Role', field: 'role' },
                        ]}
                        data={this.state.data}
                        actions={[
                            rowData => ({
                                icon: tableIcons.Delete,
                                tooltip: 'Delete Person',
                                onClick: (event, rowData) => this.props.deletePerson(rowData.id)
                            }),
                            rowData => ({
                                icon: tableIcons.Edit,
                                tooltip: 'Edit Person',
                                onClick: (event, rowData) => this.props.deletePerson(rowData.id)
                            }),
                        ]}
                        options={{
                            search: false,
                            sorting: true,
                            paging: false,
                            showTitle: true,
                        }}
                    />
                </Paper>
            </div>
        );
    }
}

export default withStyles(styles)(Users);