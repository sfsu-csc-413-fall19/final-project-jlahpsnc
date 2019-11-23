import React from 'react';
import './App.css';
import Startpage from './pages/Startpage';
import { ThemeProvider } from 'flwww';
import { Route, BrowserRouter as Router, Switch } from 'react-router-dom';
import Loadingpage from './pages/Loadingpage';
import Homepage from './pages/Homepage';
import { Provider as AuthProvider } from './context/AuthContext';
import ProtectedRoute from './ProtectedRoute';

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

const App = () => {
  return (
    <Router>
      <Switch>
        <Route exact path='/'>
          <ThemeProvider theme={theme}>
            <Startpage />
          </ThemeProvider>
        </Route>
        <ProtectedRoute exact path='/loading'>
          <ThemeProvider theme={theme}>
            <Loadingpage />
          </ThemeProvider>
        </ProtectedRoute>

        <ProtectedRoute exact path='/home'>
          <ThemeProvider theme={theme}>
            <Homepage />
          </ThemeProvider>
        </ProtectedRoute>
      </Switch>
    </Router>
  );
};

export default () => {
  return (
    <AuthProvider>
      <App />
    </AuthProvider>
  );
};
