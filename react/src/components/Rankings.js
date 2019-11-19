import React from 'react';
import { Card, Table } from 'flwww';

const Rankings = () => {
  const columns = ['Username', 'Score'];

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

  return (
    <div>
      <Card title='Rankings' padding='0'>
        <Table columns={columns} rows={products} />
      </Card>
    </div>
  );
};

export default Rankings;
