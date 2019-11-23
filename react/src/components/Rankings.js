import React from 'react';
import { Card, Table } from 'flwww';
// import axios from 'axios';

const Rankings = () => {
  const columns = ['Username', 'Score'];
  // const [rankings, setRankings] = useState([]);

  // Here is the data for the table rows. Pay attention to have the same key name as the one in your columns array!
  const products = [
    {
      Username: 'GamerTag1',
      Score: 15000
    },
    {
      Username: 'GamerTag2',
      Score: 14500
    }
  ];

  // const getRankings = () => {
  //   axios
  //     .post('/rankings')
  //     .then(res => {
  //       console.log(res);
  //     })
  //     .catch(console.log);
  // };

  // useEffect(getRankings(), []);

  return (
    <div>
      <Card title='Rankings' padding='0'>
        <Table columns={columns} rows={products} />
      </Card>
    </div>
  );
};

export default Rankings;
