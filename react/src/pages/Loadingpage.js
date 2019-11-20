import React, { useState, useEffect } from 'react';
import { Container, Row, Button } from 'flwww';
import { NavLink, useHistory } from 'react-router-dom';

const wsSession = new WebSocket('ws://localhost:1234/wsLoading');
const Loadingpage = () => {
  let history = useHistory();
  const [userCount, setUserCount] = useState(1);
  const ws = React.useRef(wsSession);

  ws.current.onopen = () => {
    console.log('Connection open!');
    ws.current.send(userCount);
  };

  ws.current.onmessage = message => {
    setUserCount(Number(message.data));
    console.log(message.data);
  };

  window.onbeforeunload = function() {
    ws.current.onclose = function() {}; // disable onclose handler first
    let currCount = -1;
    console.log('connection closed');
    ws.current.send(currCount.toString());
    ws.close();
  };

  ws.current.onclose = () => {
    let currCount = -1;
    console.log('connection closed');
    ws.current.send(currCount.toString());
  };

  ws.current.onerror = () => {
    console.log('ws error');
  };

  useEffect(ws.current.onopen, []);

  const nextPath = path => {
    ws.current.send(-1 + '');
    history.push(path);
  };

  return (
    <Container className='full-page'>
      <h1>Hello World</h1>
      <h1>Number of Users in loading room: {userCount}</h1>
      <Row className='row-style'>
        <Button outlined round type='danger' onClick={() => nextPath('/')}>
          {/* <a href='/loading'>Go to loading page</a> */}
          go to start page
        </Button>
        <Button outlined round type='danger' onClick={() => nextPath('/home')}>
          {/* <NavLink to='/home'>go to home screen</NavLink> */}
          go to home screen
        </Button>
        <NavLink to='/'>Go to loading</NavLink>
      </Row>
    </Container>
  );
};

export default Loadingpage;
