import React, { useContext } from 'react';
import { Row, Col, Button } from 'flwww';
import { Context as AuthContext } from '../context/AuthContext';

const HomeMenu = () => {
  const { state, logout } = useContext(AuthContext);

  return (
    <Col grid='2'>
      <Row className='button-row'>
        <Button style={{ width: 200, height: 50 }}>Start</Button>
      </Row>
      <Row className='button-row'>
        <Button style={{ width: 200, height: 50 }}>Menu</Button>
      </Row>
      <Row className='button-row'>
        <Button
          style={{ width: 200, height: 50 }}
          onClick={() => logout(state.id)}
        >
          Quit
        </Button>
      </Row>
    </Col>
  );
};

export default HomeMenu;
