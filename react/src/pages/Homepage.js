import React from 'react';
import { Container, Row, Col } from 'flwww';
import UserInfo from '../components/UserInfo';
import Rankings from '../components/Rankings';

const Homepage = () => {
  return (
    <Container>
      <Row className='row-space-between'>
        <Col grid='6'>
          <UserInfo />
        </Col>
        <Col grid='4'>
          <Rankings />
        </Col>
      </Row>
    </Container>
  );
};

export default Homepage;
