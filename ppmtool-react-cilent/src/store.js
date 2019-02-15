import { createStore, applyMiddleware, compose } from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const intialState = {};
const middleware = [thunk];

let store;
let REDUX_DEVTOOL =
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();

if (window.navigator.userAgent.includes("Chrome") && REDUX_DEVTOOL) {
  store = createStore(
    rootReducer,
    intialState,
    compose(
      applyMiddleware(...middleware),
      REDUX_DEVTOOL
    )
  );
} else {
  store = createStore(
    rootReducer,
    intialState,
    compose(applyMiddleware(...middleware))
  );
}

export default store;
