import { useParams } from 'react-router';
import BidPage from './BidPage';

const BidPageWrapper: React.FC = () => {
  const { auctionId } = useParams<{ auctionId: string }>();
  if (!auctionId) return null;

  return <BidPage auctionId={parseInt(auctionId)} onMessage={() => {}} />;
};

export default BidPageWrapper;