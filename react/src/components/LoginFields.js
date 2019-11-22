import React, { useContext, useState } from 'react';
import logo from '../assets/logo.svg';
import { Container, Row, Col, Input, Button, Card } from 'flwww';
import { Context as AuthContext } from '../context/AuthContext';

const LoginFields = () => {
  const { state, register, login } = useContext(AuthContext);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  return (
    <Container>
      <Row className='row-style'>
        <h1 className='start-tagline'>Let's Play!</h1>
      </Row>
      <Row className='row-style'>
        <Card className='card-shadow'>
          <img src={logo} className='App-logo' alt='logo' color='#113f67' />
          <p>Filler logo. To be replaced later</p>
        </Card>
      </Row>
      <Row className='row-style'>
        <Col grid='6'>
          <Input
            placeholder='Username'
            icon='user'
            value={username}
            onChange={e => setUsername(e.target.value)}
          ></Input>
        </Col>
      </Row>
      <Row className='row-style'>
        <Col grid='6'>
          <Input
            placeholder='Password'
            icon='lock'
            type='password'
            value={password}
            onChange={e => setPassword(e.target.value)}
          ></Input>
        </Col>
      </Row>
      <Row className='row-style'>
        <Col grid='3'>
          <Button outlined round onClick={() => login({ username, password })}>
            Login
          </Button>
        </Col>
        <Col grid='3'>
          <Button
            round
            onClick={() => {
              register({ username, password });
            }}
          >
            Register
          </Button>
        </Col>
      </Row>
      {state.errorMessage !== '' && (
        <h1 style={{ color: 'red' }}>{state.errorMessage}</h1>
      )}
    </Container>
  );
};

export default LoginFields;
