import React, { useContext, useState, useEffect } from 'react';
import { Row, Col, Button, message } from 'flwww';
import { Context as AuthContext } from '../context/AuthContext';
import { useHistory } from 'react-router-dom';
import axios from 'axios';

const HomeMenu = ({ id }) => {
  let history = useHistory();
  const { logout } = useContext(AuthContext);
  const [user, setUser] = useState({});

  const getUserInfo = id => {
    axios
      .get(`/playerInfo?playerId=${id}`)
      .then(res => {
        setUser(JSON.parse(res.data.responseBody));
      })
      .catch(console.log);
  };

  useEffect(() => {
    getUserInfo(id);
  }, [id]);

  return (
    <Col grid='2'>
      <Row className='button-row'>
        <div
          onClick={() => {
            if (user.inGame) {
              message('You are already in a game!', 'error');
            } else if (user.inQueue) {
              message('You are already in queue!', 'error');
            }
          }}
        >
          <Button
            round
            disabled={user.inGame || user.inQueue}
            style={{ width: 200, height: 50 }}
            onClick={() => {
              history.push('/loading');
            }}
          >
            Start
          </Button>
        </div>
      </Row>
      <Row className='button-row'>
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
