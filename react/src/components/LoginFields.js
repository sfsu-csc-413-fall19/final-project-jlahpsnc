import React from 'react';
import logo from '../assets/logo.svg';
import { Container, Row, Col, Input, Button, Card } from 'flwww';

const LoginFields = () => {
  return (
    <Container>
      <Row className='row-style'>
        <h1 className='start-tagline'>Let's Play!</h1>
      </Row>
      <Row className='row-style'>
        <Card className='card-shadow'>
          <img src={logo} className='App-logo' alt='logo' />
          <p>Filler logo. To be replaced later</p>
        </Card>
      </Row>
      <Row className='row-style'>
        <Col grid='6'>
          <Input placeholder='Username' icon='user'></Input>
        </Col>
      </Row>
      <Row className='row-style'>
        <Col grid='6'>
          <Input placeholder='Password' icon='lock' type='password'></Input>
        </Col>
      </Row>
      <Row className='row-style'>
        <Col grid='3'>
          <Button outlined round>
            Login
          </Button>
        </Col>
        <Col grid='3'>
          <Button round>Register</Button>
        </Col>
      </Row>
    </Container>
  );
};

export default LoginFields;
