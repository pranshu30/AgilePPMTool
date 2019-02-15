import { combineReducers } from "redux";
import errorReducers from "./errorReducers";
import projectReducer from "./projectReducer";
import backlogReducer from "./backlogReducer";
import securityReducer from "./securityReducer";

export default combineReducers({
  //Combine the error with main reducer
  errors: errorReducers,
  project: projectReducer,
  backlog: backlogReducer,
  security: securityReducer
});
