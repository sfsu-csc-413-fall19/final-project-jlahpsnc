import createDataContext from './createDataContext';
import axios from 'axios';
import { createBrowserHistory } from 'history';

let history = createBrowserHistory({ forceRefresh: true });

const authReducer = (state, action) => {
  switch (action.type) {
    case 'add_error':
      return { ...state, errorMessage: action.payload };
    case 'add_user':
      return {
        ...state,
        id: action.payload,
        errorMessage: ''
      };
    case 'quit':
      return {
        ...state,
        id: '',
        logoutMessage: action.payload
      };
    default:
      return state;
  }
};

const register = dispatch => {
  return ({ username, password }) => {
    //make api request to register
    //if signed up, modify our state
    //if signed up fail reflect error message
    axios
      .post(`/register?username=${username}&password=${password}`)
      .then(res => {
        if (res.data === 'Player already exists') {
          dispatch({ type: 'add_error', payload: 'Player already exists' });
        } else {
          localStorage.setItem('id', res.data);
          dispatch({ type: 'add_user', payload: res.data });
          history.push('/home');
        }
      })
      .catch(console.log);
  };
};

const login = dispatch => {
  return ({ username, password }) => {
    //make api request to register
    //if signed up, modify our state
    //if signed up fail reflect error message
    axios
      .post(`/login?username=${username}&password=${password}`)
      .then(res => {
        if (
          res.data === 'Incorrect Password' ||
          res.data === 'Player not found'
        ) {
          dispatch({ type: 'add_error', payload: res.data });
        } else {
          localStorage.setItem('id', res.data);
          dispatch({ type: 'add_user', payload: res.data });
          history.push('/home');
        }
      })
      .catch(console.log);
  };
};

const logout = dispatch => {
  return ({ id }) => {
    //make api request to register
    //if signed up, modify our state
    //if signed up fail reflect error message
    axios
      .post(`/quit?playerId=${id}`)
      .then(res => {
        if (res.data === 'Player was logged out') {
          dispatch({ type: 'quit', payload: res.data });
          localStorage.removeItem('id');
          history.push('/');
        }
      })
      .catch(console.log);
  };
};

const tryLocalLogin = dispatch => async () => {
  const id = await localStorage.getItem('id');
  if (id) {
    dispatch({ type: 'add_user', payload: id });
    history.push('/home');
  } else {
    history.push('/');
  }
};

export const { Provider, Context } = createDataContext(
  authReducer,
  { login, logout, register, tryLocalLogin },
  { errorMessage: '', id: '', logoutMessage: '' }
);
