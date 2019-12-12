import React, { useState } from 'react';
import { Container, Row, Col, Button, Modal } from 'flwww';

const IntroSection = props => {
  const [modalIsVisible, setModalIsVisible] = useState(false);
  const toggleModal = () => {
    setModalIsVisible(!modalIsVisible);
  };

  return (
    <Container className="full-page">
      <Row className="row-style title">
        <Col grid="4">
          <h1 id="title">OP Memory</h1>
        </Col>
      </Row>
      <Row className="row-style">
        <h1>{props.title}</h1>
        <Button round onClick={toggleModal}>
          Learn how to play here!
        </Button>

        <Modal isVisible={modalIsVisible} toggleModal={toggleModal}>
          <h3>How to play:</h3>
          <p>Earn points by matching cards</p>
          <p>Look out for Joker Cards!</p>
          <p>
            Each joker card automatically counts as 1 point and if picked
            second, Automatically finds the pair for the first card you clicked.
          </p>
        </Modal>
      </Row>
    </Container>
  );
};
export default IntroSection;
