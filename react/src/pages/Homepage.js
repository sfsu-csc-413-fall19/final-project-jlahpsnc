import React, { useState, useEffect } from 'react';
import { Container, Row, Col } from 'flwww';
import UserInfo from '../components/UserInfo';
import Rankings from '../components/Rankings';
import HomeMenu from '../components/HomeMenu';
import { withRouter } from 'react-router-dom';

const Homepage = () => {
  const [id, setId] = useState('');

  const initialize = () => {
    setId(localStorage.getItem('id'));
  };

  useEffect(() => {
    initialize();
  }, []);

  const page = () => {
    return (
      <>
        <Row className="row-space-between">
          <Col grid="6">
            <UserInfo id={id} />
          </Col>
          <Col grid="4">
            <Rankings />
          </Col>
        </Row>
        <Row className="home-button-container">
          <HomeMenu id={id} />
        </Row>
      </>
    );
  };
  return (
    <Container>
      {id === '' && <h1>Loading...</h1>}
      {id !== '' && page()}
    </Container>
  );
};

export default withRouter(Homepage);
