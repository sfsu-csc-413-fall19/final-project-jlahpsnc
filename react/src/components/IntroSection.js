import React, { useState } from 'react';
import { Container, Row, Col, Button, Modal } from 'flwww';
import { NavLink } from 'react-router-dom';

const IntroSection = () => {
  const [modalIsVisible, setModalIsVisible] = useState(false);
  const toggleModal = () => {
    setModalIsVisible(!modalIsVisible);
  };

  return (
    <Container>
      <Row className='row-style'>
        <Col grid='6'>
          <h1 id='title'>OP Memory</h1>
        </Col>
      </Row>
      <Row className='row-style'>
        <Button
          outlined
          round
          colors={{ mainColor: '#ffffff', secondColor: '#fff' }}
          onClick={toggleModal}
        >
          Learn how to play here!
        </Button>
        <Button>
          <NavLink to='/loading'>go to loading</NavLink>
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
    </Container>
  );
};
export default IntroSection;
