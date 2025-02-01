import { AxiosResponse } from 'axios';
import {
  IAppServiceResponse, ICommonApiResponse, IField, ILanguage,
} from '../types/Types';
import log from '../logger';
import createAxiosInstance from '../utils/AxiosInstanceFactory';

const axiosInstance = createAxiosInstance('/api/v1/manage');

// API function to get services
export const getServices = async (): Promise<IAppServiceResponse[]> => {
  try {
    const response: AxiosResponse<ICommonApiResponse<IAppServiceResponse[]>> = await axiosInstance.get('/service');
    if (response.data.success) {
      return response.data.data; 
    }
    log.error('Failed to fetch services:', response.data.errorDetails?.errorMessage);
    throw new Error(response.data.errorDetails?.errorMessage || 'Failed to fetch services');
  } catch (error: any) {
    log.error('Unexpected error in getServices:', error.message || error);
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
    log.error('Failed to fetch languages:', response.data.errorDetails?.errorMessage);
    throw new Error(response.data.errorDetails?.errorMessage || 'Failed to fetch languages');
  } catch (error: any) {
    log.error('Unexpected error in getLanguages:', error.message || error);
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
    log.error('Failed to fetch fields:', response.data.errorDetails?.errorMessage);
    throw new Error(response.data.errorDetails?.errorMessage || 'Failed to fetch fields');
  } catch (error: any) {
    log.error('Unexpected error in getFieldsByServiceId:', error.message || error);
    throw new Error('An unexpected error occurred while fetching fields.');
  }
};