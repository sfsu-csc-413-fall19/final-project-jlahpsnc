import React, { useContext } from 'react';
import { Row, Col, Button } from 'flwww';
import { Context as AuthContext } from '../context/AuthContext';
import { useHistory } from 'react-router-dom';

const HomeMenu = ({ id }) => {
  let history = useHistory();
  const { logout } = useContext(AuthContext);

  return (
    <Col grid="2">
      <Row className="button-row">
        <Button
          round
          style={{ width: 200, height: 50 }}
          onClick={() => {
            history.push('/loading');
          }}
        >
          Start
        </Button>
      </Row>
      <Row className="button-row">
        <Button
          round
          style={{ width: 200, height: 50 }}
          onClick={() => logout({ id })}
        >
          Quit
        </Button>
      </Row>
    </Col>
  );
};

export default HomeMenu;
