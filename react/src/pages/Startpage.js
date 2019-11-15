import React from 'react';
import { Container, Row, Col } from 'flwww';
import LoginFields from '../components/LoginFields.js';
import IntroSection from '../components/IntroSection.js';

const Startpage = () => {
  return (
    <Container full className='full-page'>
      <Row className='full-page'>
        <Col grid='5'>
          <LoginFields />
        </Col>
        <Col grid='7' id='splash'>
          <IntroSection />
        </Col>
      </Row>
    </Container>
  );
};

export default Startpage;
