import React from 'react';
import { Container, Row, Col } from 'flwww';
import LoginFields from '../components/LoginFields.js';
import IntroSection from '../components/IntroSection.js';
import axios from 'axios'

const Startpage = props => {

  React.useEffect(()=>{
    axios
      .get('/userEntered')
      .then(console.log)
      .catch(console.log)
  },[])

  return (
    <Container className='full-page' id='start-page'>
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
