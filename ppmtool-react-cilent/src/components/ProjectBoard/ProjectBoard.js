import React, { Component } from "react";
import { Link } from "react-router-dom";
import Backlog from "./Backlog";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { getBacklog } from "../../actions/backlogActions";
import { BoardAlgo } from "../ProjectBoard/BoardAlgo";
import classnames from "classnames";

class ProjectBoard extends Component {
  constructor() {
    super();
    this.state = {
      errors: {}
    };
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getBacklog(id);
  }

  render() {
    const { id } = this.props.match.params;
    const { project_tasks } = this.props.backlog;
    const { errors } = this.state;

    let BoardContent;

    BoardContent = BoardAlgo(errors, project_tasks);

    return (
      <div className="container">
        <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
          <i className="fas fa-plus-circle"> Create Project Task</i>
        </Link>
        <br />
        <hr />
        {BoardContent}
      </div>
    );
  }
}
//Tell react this function is required to connect
ProjectBoard.propTypes = {
  getBacklog: PropTypes.func.isRequired,
  backlog: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStatetoProps = state => ({
  backlog: state.backlog,
  errors: state.errors
});
//connect -connect state to component property
export default connect(
  mapStatetoProps,
  { getBacklog }
)(ProjectBoard);
