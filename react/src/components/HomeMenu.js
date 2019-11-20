import React from 'react';
import { Row, Col, Button } from 'flwww';

const HomeMenu = () => {
  return (
    <Col grid='2'>
      <Row className='button-row'>
        <Button style={{ width: 200, height: 50 }}>Start</Button>
      </Row>
      <Row className='button-row'>
        <Button style={{ width: 200, height: 50 }}>Menu</Button>
      </Row>
      <Row className='button-row'>
        <Button style={{ width: 200, height: 50 }}>Quit</Button>
      </Row>
    </Col>
  );
};

export default HomeMenu;
