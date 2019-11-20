import React from 'react';
import { Container, Row, Col, Button } from 'flwww';
import UserInfo from '../components/UserInfo';
import Rankings from '../components/Rankings';
import HomeMenu from '../components/HomeMenu';
import { NavLink, useHistory } from 'react-router-dom';

const Homepage = () => {
  let history = useHistory();
  const nextPath = path => {
    history.push(path);
  };

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
      <Row className='home-button-container'>
        <HomeMenu />
      </Row>
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

export default Homepage;
