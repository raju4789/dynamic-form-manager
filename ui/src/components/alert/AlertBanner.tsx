import { useEffect, useState } from 'react';
import Alert from '@mui/material/Alert';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import ErrorIcon from '@mui/icons-material/Error';
import styled from 'styled-components';

interface AlertBannerProps {
  message?: string;
  type?: 'success' | 'error';
  duration?: number;
}

const StyledAlert = styled(Alert)`
  && {
    position: fixed;
    top: 16px;
    right: 16px;
    min-width: 300px;
    transition: all 0.3s ease-in-out;
    animation: slideIn 0.3s ease-in-out;

    @keyframes slideIn {
      from {
        transform: translateX(100%);
        opacity: 0;
      }
      to {
        transform: translateX(0);
        opacity: 1;
      }
    }
  }
`;

const AlertBanner = ({
  message = 'Alert message',
  type = 'success',
  duration = 5000,
}: AlertBannerProps) => {
  const [visible, setVisible] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setVisible(false);
    }, duration);

    return () => clearTimeout(timer);
  }, [duration]);

  if (!visible) return null;

  return (
    <StyledAlert
      severity={type}
      icon={type === 'success' ? <CheckCircleIcon /> : <ErrorIcon />}
      sx={{
        backgroundColor: type === 'success' ? '#f0fdf4' : '#fef2f2',
        color: type === 'success' ? '#166534' : '#991b1b',
        '& .MuiAlert-icon': {
          color: type === 'success' ? '#16a34a' : '#dc2626',
        },
      }}
    >
      {message}
    </StyledAlert>
  );
};

export default AlertBanner;
