import { styled } from '@mui/material/styles';
import {
  Box,
  Select,
} from '@mui/material';

export const PageContainer = styled(Box)(({ theme }) => ({
  padding: theme.spacing(2),
  position: 'relative',
}));

export const FormContainer = styled(Box)(({ theme }) => ({
  padding: theme.spacing(2),
  border: `1px solid ${theme.palette.divider}`,
  borderRadius: theme.shape.borderRadius,
  width: '50%',
  margin: '0 auto',
  marginTop: theme.spacing(2),
}));

export const LanguageDropdownContainer = styled(Box)(({ theme }) => ({
  display: 'flex',
  justifyContent: 'flex-end',
  marginBottom: theme.spacing(2),
}));

export const LanguageDropdown = styled(Select)(() => ({
  minWidth: '150px',
}));

export const FormHeader = styled(Box)(({ theme }) => ({
  textAlign: 'center',
  marginBottom: theme.spacing(4),
  color: '#1976d2',
}));
