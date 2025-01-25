import axios, { AxiosInstance, AxiosResponse } from 'axios';
import {
  IAppServiceResponse, ICommonApiResponse, IField, ILanguage,
} from '../types/Types';

// Create an Axios instance
const axiosInstance: AxiosInstance = axios.create({
  baseURL: '/api/v1/manage',
  headers: {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
  },
});

// Add an interceptor to dynamically set the Authorization header
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('jwt');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    console.error('Request error:', error);
    return Promise.reject(error);
  },
);

// API function to get services
export const getServices = async (): Promise<IAppServiceResponse[]> => {
  try {
    const response: AxiosResponse<ICommonApiResponse<IAppServiceResponse[]>> = await axiosInstance.get('/service');
    if (response.data.success) {
      return response.data.data; // Return the services data directly
    }
    throw new Error(response.data.errorDetails?.errorMessage || 'Failed to fetch services');
  } catch (error: any) {
    console.error('Unexpected error in getServices:', error.message || error);
    throw new Error('An unexpected error occurred while fetching services.');
  }
};

// API function to get all languages
export const getLanguages = async (): Promise<ILanguage[]> => {
  try {
    const response: AxiosResponse<ICommonApiResponse<ILanguage[]>> = await axiosInstance.get('/language');
    if (response.data.success) {
      return response.data.data; // Return the languages data directly
    }
    throw new Error(response.data.errorDetails?.errorMessage || 'Failed to fetch languages');
  } catch (error: any) {
    console.error('Unexpected error in getLanguages:', error.message || error);
    throw new Error('An unexpected error occurred while fetching languages.');
  }
};

// API function to get fields by service id
export const getFieldsByServiceId = async (serviceId: string): Promise<IField[]> => {
  try {
    const response: AxiosResponse<ICommonApiResponse<IField[]>> = await axiosInstance.get(`/service/fields/${serviceId}`);
    if (response.data.success) {
      return response.data.data;
    }
    throw new Error(response.data.errorDetails?.errorMessage || 'Failed to fetch fields');
  } catch (error: any) {
    console.error('Unexpected error in getFieldsByServiceId:', error.message || error);
    throw new Error('An unexpected error occurred while fetching fields.');
  }
};
