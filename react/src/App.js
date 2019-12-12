import React, { lazy, Suspense } from 'react';
import './App.css';
import Startpage from './pages/Startpage';
import { ThemeProvider } from 'flwww';
import {
  Route,
  BrowserRouter as Router,
  Switch,
  Redirect
} from 'react-router-dom';
import Homepage from './pages/Homepage';
import { Provider as AuthProvider } from './context/AuthContext';
import ProtectedRoute from './ProtectedRoute';
import Gamepage from './pages/Gamepage';

const Loadingpage = lazy(() => import('./pages/Loadingpage'));
const theme = {
  defaultColor: '#413c69',
  defaultTextColor: '#f9f9f9',
  primaryColor: '#413c69',
  primaryTextColor: '#f9f9f9',
  successColor: '#413c69',
  successTextColor: '#f9f9f9',
  dangerColor: '#413c69',
  dangerTextColor: '#f9f9f9'
};

const App = () => {
  const isAuthenticated = localStorage.getItem('id');
  return (
    <Router forceRefresh={true}>
      <Switch>
        <Route exact path="/">
          <ThemeProvider theme={theme}>
            {isAuthenticated !== null ? <Redirect to="/home" /> : <Startpage />}
          </ThemeProvider>
        </Route>
        <ProtectedRoute exact path="/loading">
          <ThemeProvider theme={theme}>
            <Suspense fallback={<div>Loading...</div>}>
              <Loadingpage />
            </Suspense>
          </ThemeProvider>
        </ProtectedRoute>

        <ProtectedRoute exact path="/home">
          <ThemeProvider theme={theme}>
            <Homepage />
          </ThemeProvider>
        </ProtectedRoute>
        <Route path="/game">
          <Gamepage />
        </Route>
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
