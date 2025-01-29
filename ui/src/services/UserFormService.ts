<<<<<<< HEAD
import axios, { AxiosInstance, AxiosResponse } from "axios";
import { GetFormDataByServiceIdAndUserIdResponse, ICommonApiResponse, UserFormRequest, UserFormResponse } from "../types/Types";
import log from "../logger";
=======
import axios, { AxiosInstance, AxiosResponse } from 'axios';
import {
  GetFormDataByServiceIdAndUserIdResponse, ICommonApiResponse, UserFormRequest, UserFormResponse,
} from '../types/Types';
>>>>>>> cfa8ebcee4cc2bbba2d72b2256ee7b506b5e1b76

// Create an Axios instance
const axiosInstance: AxiosInstance = axios.create({
  baseURL: '/api/v1/form',
  headers: {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
  },
});

// Add an interceptor to dynamically set the Authorization header
axiosInstance.interceptors.request.use(
  (config) => {
<<<<<<< HEAD
    try {
      const token = localStorage.getItem("jwt");
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    } catch (error) {
      log.error("Error setting Authorization header:", error);
      return Promise.reject(error);
=======
    const token = localStorage.getItem('jwt');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
>>>>>>> cfa8ebcee4cc2bbba2d72b2256ee7b506b5e1b76
    }
  },
  (error) => {
<<<<<<< HEAD
    log.error("Request error:", error);
=======
    // Handle request errors
    console.error('Request error:', error);
>>>>>>> cfa8ebcee4cc2bbba2d72b2256ee7b506b5e1b76
    return Promise.reject(error);
  },
);

// API function to submit form data
export const submitFormData = async (data: UserFormRequest): Promise<UserFormResponse> => {
  try {
<<<<<<< HEAD
    log.info("Submitting form data...");
    const response: AxiosResponse<ICommonApiResponse<UserFormResponse>> = await axiosInstance.post("/submit", data);

    if (!response.data.success) {
      const errorMessage = response.data.errorDetails?.errorMessage || "Failed to submit form data";
      log.error("Error submitting form data:", errorMessage);
      throw new Error(errorMessage);
    }

    log.info("Form data submitted successfully.");
    return response.data.data;
  } catch (error: any) {
    const errorMessage = `Unexpected error in submitFormData: ${error.message || error}`;
    log.error(errorMessage);
    throw new Error("An unexpected error occurred while submitting form data.");
  }
};

// API function to get form data by serviceId and userId
export const getFormDataByUserIdAndServiceId = async (
  serviceId: string,
  userId: string
): Promise<GetFormDataByServiceIdAndUserIdResponse> => {
  try {
    log.info(`Fetching form data for serviceId: ${serviceId} and userId: ${userId}...`);
    const response: AxiosResponse<ICommonApiResponse<GetFormDataByServiceIdAndUserIdResponse>> = await axiosInstance.get(
      `/getFormData?serviceId=${serviceId}&userId=${userId}`
    );

    if (!response.data.success) {
      const errorMessage = response.data.errorDetails?.errorMessage || "Failed to fetch form data";
      log.error(`Error fetching form data for serviceId: ${serviceId} and userId: ${userId}:`, errorMessage);
      throw new Error(errorMessage);
    }

    log.info(`Form data fetched successfully for serviceId: ${serviceId} and userId: ${userId}.`);
    log.debug("Fetched form data:", response.data.data);
    return response.data.data;
  } catch (error: any) {
    const errorMessage = `Unexpected error in getFormDataByUserIdAndServiceId for serviceId: ${serviceId} and userId: ${userId}: ${error.message || error}`;
    log.error(errorMessage);
    throw new Error("An unexpected error occurred while fetching form data.");
  }
};
=======
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
>>>>>>> cfa8ebcee4cc2bbba2d72b2256ee7b506b5e1b76
