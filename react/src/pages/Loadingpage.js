import React, { useState } from 'react';
import { Container, Row, Col } from 'flwww';

const wsSession = new WebSocket(`ws://localhost:1234/wsLoading`);
const Loadingpage = () => {
  const [userCount, setUserCount] = useState(0);
  const ws = React.useRef(wsSession);

  ws.current.onopen = () => {
    console.log('Connection open!');
  };

  ws.current.onmessage = message => {
    setUserCount(Number(message.data));
    console.log(message);
  };

  ws.current.onclose = () => {
    console.log('connection closed');
  };

  ws.current.onerror = () => {
    console.log('ws error');
  };

  return (
    <Container full className='full-page'>
      <h1>Hello World</h1>
      <h1>Number of Users in loading room: {userCount}</h1>
    </Container>
  );
};

export default Loadingpage;
