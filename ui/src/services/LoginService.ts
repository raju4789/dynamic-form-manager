import axios, { AxiosResponse } from 'axios';
import {
  ICommonApiResponse,
  ILoginRequest, ILoginResponse,
} from '../types/Types';

const headers = {
  'Content-Type': 'application/json',
  'Access-Control-Allow-Origin': '*',
};

const axiosInstance = axios.create({
  baseURL: '/api/v1/auth',
  headers,
});

export const loginUser = (loginCredentials: ILoginRequest): Promise<AxiosResponse<ICommonApiResponse<ILoginResponse>>> => axiosInstance.post('/authenticate', loginCredentials, { headers });
