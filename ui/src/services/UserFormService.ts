import axios, { AxiosInstance, AxiosResponse } from "axios";
import { GetFormDataByServiceIdAndUserIdResponse, ICommonApiResponse, UserFormRequest, UserFormResponse } from "../types/Types";
import log from "../logger";

// Create an Axios instance
const axiosInstance: AxiosInstance = axios.create({
  baseURL: "/api/v1/form",
  headers: {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
  },
});

// Add an interceptor to dynamically set the Authorization header
axiosInstance.interceptors.request.use(
  (config) => {
    try {
      const token = localStorage.getItem("jwt");
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    } catch (error) {
      log.error("Error setting Authorization header:", error);
      return Promise.reject(error);
    }
  },
  (error) => {
    log.error("Request error:", error);
    return Promise.reject(error);
  }
);

// API function to submit form data
export const submitFormData = async (data: UserFormRequest): Promise<UserFormResponse> => {
  try {
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