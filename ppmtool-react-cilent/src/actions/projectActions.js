//talking with the backend
import axios from "axios";
//import the types of actions
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "./types";

export const createProject = (project, history) => async dispatch => {
  try {
    const res = await axios.post("/api/project", project);
    history.push("/dashboard");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

//get projects
export const getProjects = () => async dispatch => {
  const res = await axios.get("/api/project/all");

  dispatch({
    type: GET_PROJECTS,
    payload: res.data
  });
};

//get project
export const getProject = (id, history) => async dispatch => {
  try {
    const res = await axios.get(`/api/project/${id}`);

    dispatch({
      type: GET_PROJECT,
      payload: res.data
    });
  } catch (err) {
    history.push("/dashboard");
  }
};

//delete project
export const deleteProject = id => async dispatch => {
  if (window.confirm("Are you sure want to delete this?")) {
    const res = await axios.delete(`/api/project/${id}`);

    dispatch({
      type: DELETE_PROJECT,
      payload: id
    });
  }
};
