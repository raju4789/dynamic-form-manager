import axios, { AxiosInstance } from 'axios';
import log from '../logger';

// Factory function to create an Axios instance with a custom baseURL
const createAxiosInstance = (baseURL: string): AxiosInstance => {
  const instance = axios.create({
    baseURL,
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
    },
  });

  // Add an interceptor to dynamically set the Authorization header
  instance.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem('jwt');
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error) => {
      log.error('Request error:', error);
      return Promise.reject(error);
    },
  );

  return instance;
};

export default createAxiosInstance;