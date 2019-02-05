import { combineReducers } from "redux";
import errorReducers from "./errorReducers";
import projectReducer from "./projectReducer";

export default combineReducers({
  //Combine the error with main reducer
  errors: errorReducers,
  project: projectReducer
});
