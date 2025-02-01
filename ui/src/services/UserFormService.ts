import { AxiosResponse } from 'axios';
import {
  GetFormDataByServiceIdAndUserIdResponse, ICommonApiResponse, UserFormRequest, UserFormResponse,
} from '../types/Types';
import createAxiosInstance from '../utils/AxiosInstanceFactory';

const axiosInstance = createAxiosInstance('/api/v1/form');

// API function to submit form data
export const submitFormData = async (data: UserFormRequest): Promise<UserFormResponse> => {
  try {
    const response: AxiosResponse<ICommonApiResponse<UserFormResponse>> = await axiosInstance.post('/submit', data);
    if (!response.data.success) {
      throw new Error(response.data.errorDetails?.errorMessage || 'Failed to submit form data');
    }
    return response.data.data;
  } catch (error: any) {
    console.error('Unexpected error in submitFormData:', error.message || error);
    throw new Error('An unexpected error occurred while submitting form data.');
  }
};

// API function to get form data bu serviceId and userId
export const getFormDataByUserIdAndServiceId = async (serviceId: string, userId: string): Promise<GetFormDataByServiceIdAndUserIdResponse> => {
  try {
    const response: AxiosResponse<ICommonApiResponse<GetFormDataByServiceIdAndUserIdResponse>> = await axiosInstance.get(`/getFormData?serviceId=${serviceId}&userId=${userId}`);
    if (!response.data.success) {
      throw new Error(response.data.errorDetails?.errorMessage || 'Failed to fetch form data');
    }
    console.log('getFormDataByUserIdAndServiceId', response.data.data);
    return response.data.data;
  } catch (error: any) {
    console.error('Unexpected error in getFormData:', error.message || error);
    throw new Error('An unexpected error occurred while fetching form data.');
  }
};
