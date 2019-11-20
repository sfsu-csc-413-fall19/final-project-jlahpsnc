import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import { Route, BrowserRouter as Router, Switch } from 'react-router-dom';
import Loadingpage from './pages/Loadingpage';
import Homepage from './pages/Homepage';
import { ThemeProvider } from 'flwww';

const theme = {
  defaultColor: '#226597',
  defaultTextColor: '#f3f9fb',
  primaryColor: '#226597',
  primaryTextColor: '#f3f9fb',
  successColor: '#226597',
  successTextColor: '#f3f9fb',
  dangerColor: '#226597',
  dangerTextColor: '#f3f9fb'
};

const routing = (
  <Router>
    <Switch>
      <Route exact path='/'>
        <ThemeProvider theme={theme}>
          <App />
        </ThemeProvider>
      </Route>
      <Route exact path='/loading'>
        <ThemeProvider theme={theme}>
          <Loadingpage />
        </ThemeProvider>
      </Route>
      <Route exact path='/home'>
        <ThemeProvider theme={theme}>
          <Homepage />
        </ThemeProvider>
      </Route>
    </Switch>
  </Router>
);

ReactDOM.render(routing, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
