import React, { useState, useEffect } from 'react';
import { Card, Row, Col } from 'flwww';
import axios from 'axios';

const UserInfo = ({ id }) => {
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
    <div>
      {user !== {} && (
        <Card>
          <Col grid='offset-2 8'>
            <Row style={{ justifyContent: 'center' }}>
              <h1>{user.username}</h1>
            </Row>
          </Col>

          <Row className='row-space-around'>
            <Col grid='5'>
              <h3>High Score: {user.highScore}</h3>
            </Col>
            <Col grid='4'>
              <h3>Ranking: #1</h3>
            </Col>
          </Row>
        </Card>
      )}
    </div>
  );
};

export default UserInfo;
