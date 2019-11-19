import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import { Route, BrowserRouter as Router, Switch } from 'react-router-dom';
import Loadingpage from './pages/Loadingpage';
import Homepage from './pages/Homepage';

const routing = (
  <Router>
    <Switch>
      <Route exact path='/'>
        <App />
      </Route>
      <Route exact path='/loading'>
        <Loadingpage />
      </Route>
      <Route exact path='/home'>
        <Homepage />
      </Route>
    </Switch>
  </Router>
);

ReactDOM.render(routing, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
