import axios, { AxiosInstance, AxiosResponse } from "axios";
import { IAppServiceResponse, ICommonApiResponse, IField, ILanguage } from "../types/Types";
import log from "../logger";

// Create an Axios instance
const axiosInstance: AxiosInstance = axios.create({
  baseURL: "/api/v1/manage",
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

// API function to get services
export const getServices = async (): Promise<IAppServiceResponse[]> => {
  try {
    log.info("Fetching services...");
    const response: AxiosResponse<ICommonApiResponse<IAppServiceResponse[]>> = await axiosInstance.get("/service");

    if (response.data.success) {
      log.info("Services fetched successfully.");
      return response.data.data; // Return the services data directly
    } else {
      const errorMessage = response.data.errorDetails?.errorMessage || "Failed to fetch services";
      log.error("Error fetching services:", errorMessage);
      throw new Error(errorMessage);
    }
  } catch (error: any) {
    const errorMessage = `Unexpected error in getServices: ${error.message || error}`;
    log.error(errorMessage);
    throw new Error(errorMessage);
  }
};

// API function to get all languages
export const getLanguages = async (): Promise<ILanguage[]> => {
  try {
    log.info("Fetching languages...");
    const response: AxiosResponse<ICommonApiResponse<ILanguage[]>> = await axiosInstance.get("/language");

    if (response.data.success) {
      log.info("Languages fetched successfully.");
      return response.data.data; // Return the languages data directly
    } else {
      const errorMessage = response.data.errorDetails?.errorMessage || "Failed to fetch languages";
      log.error("Error fetching languages:", errorMessage);
      throw new Error(errorMessage);
    }
  } catch (error: any) {
    const errorMessage = `Unexpected error in getLanguages: ${error.message || error}`;
    log.error(errorMessage);
    throw new Error("An unexpected error occurred while fetching languages.");
  }
};

// API function to get fields by service ID
export const getFieldsByServiceId = async (serviceId: string): Promise<IField[]> => {
  try {
    log.info(`Fetching fields for service ID: ${serviceId}...`);
    const response: AxiosResponse<ICommonApiResponse<IField[]>> = await axiosInstance.get(`/service/fields/${serviceId}`);

    if (response.data.success) {
      log.info(`Fields fetched successfully for service ID: ${serviceId}.`);
      return response.data.data;
    } else {
      const errorMessage = response.data.errorDetails?.errorMessage || "Failed to fetch fields";
      log.error(`Error fetching fields for service ID ${serviceId}:`, errorMessage);
      throw new Error(errorMessage);
    }
  } catch (error: any) {
    const errorMessage = `Unexpected error in getFieldsByServiceId for service ID ${serviceId}: ${error.message || error}`;
    log.error(errorMessage);
    throw new Error("An unexpected error occurred while fetching fields.");
  }
};