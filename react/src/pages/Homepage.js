import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button } from 'flwww';
import UserInfo from '../components/UserInfo';
import Rankings from '../components/Rankings';
import HomeMenu from '../components/HomeMenu';
import { NavLink, useHistory, withRouter } from 'react-router-dom';

const Homepage = () => {
  const [id, setId] = useState('');

  let history = useHistory();

  const nextPath = path => {
    history.push(path);
  };

  const initialize = () => {
    setId(localStorage.getItem('id'));
  };

  useEffect(() => {
    initialize();
  }, []);

  const page = () => {
    return (
      <>
        <Row className='row-space-between'>
          <Col grid='6'>
            <UserInfo id={id} />
          </Col>
          <Col grid='4'>
            <Rankings />
          </Col>
        </Row>
        <Row className='home-button-container'>
          <HomeMenu id={id} />
        </Row>
        <Row className='row-style'>
          <Button outlined round type='danger' onClick={() => nextPath('/')}>
            {/* <a href='/loading'>Go to loading page</a> */}
            go to start page
          </Button>
          <Button
            outlined
            round
            type='danger'
            onClick={() => nextPath('/home')}
          >
            {/* <NavLink to='/home'>go to home screen</NavLink> */}
            go to home screen
          </Button>
          <NavLink to='/'>Go to loading</NavLink>
        </Row>
      </>
    );
  };
  return (
    <Container>
      {id === '' && <h1>Hi there</h1>}
      {id !== '' && page()}
    </Container>
  );
};

export default withRouter(Homepage);
