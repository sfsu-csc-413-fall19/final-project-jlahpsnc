import React, { useState } from 'react';
import { Container, Row, Col, Button, Modal } from 'flwww';
import { NavLink } from 'react-router-dom';
import { createBrowserHistory } from 'history';

const IntroSection = props => {
  let history = createBrowserHistory({ forceRefresh: true });
  const [modalIsVisible, setModalIsVisible] = useState(false);
  const toggleModal = () => {
    setModalIsVisible(!modalIsVisible);
  };

  const nextPath = path => {
    history.push(path);
  };

  return (
    <Container className='full-page'>
      <Row className='row-style title'>
        <Col grid='6'>
          <h1 id='title'>OP Memory</h1>
        </Col>
      </Row>
      <Row className='row-style'>
        <h1>{props.title}</h1>
        <Button round onClick={toggleModal}>
          Learn how to play here!
        </Button>

        <Modal isVisible={modalIsVisible} toggleModal={toggleModal}>
          <h3>How to play</h3>
          <p>Instructions on how to play the game:</p>
          <Button
            onClick={toggleModal}
            outlined
            round
            colors={{ mainColor: '#282f5a', secondColor: '#282f5a' }}
          >
            Continue
          </Button>
        </Modal>
      </Row>
      <Row className='row-style'>
        {/* ONLY FOR TESTING */}
        <Button
          outlined
          round
          type='danger'
          onClick={() => nextPath('/loading')}
        >
          {/* <a href='/loading'>Go to loading page</a> */}
          go to loading
        </Button>
        <Button outlined round type='danger' onClick={() => nextPath('/home')}>
          {/* <NavLink to='/home'>go to home screen</NavLink> */}
          go to home screen
        </Button>
        <NavLink to='/loading'>Go to loading</NavLink>
        {/* ONLY FOR TESTING */}
      </Row>
    </Container>
  );
};
export default IntroSection;
