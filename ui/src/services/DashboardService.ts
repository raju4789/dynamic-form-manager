import axios, { AxiosInstance, AxiosResponse } from "axios";
import { DashboardData, ICommonApiResponse } from "../types/Types";
import log from "../logger";

// Create an Axios instance
const axiosInstance: AxiosInstance = axios.create({
  baseURL: "/api/v1/dashboard",
  headers: {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
  },
});

// Add an interceptor to dynamically set the Authorization header
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("jwt");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    // Handle request errors
    log.error("Request error:", error);
    return Promise.reject(error);
  }
);


// API function to get dashboard stats
export const getDashboardStats = async (): Promise<DashboardData> => {
    const response: AxiosResponse<ICommonApiResponse<DashboardData>> = await axiosInstance.get("/stats");
    if (response.data.success) {
      return response.data.data;
    } else {
      const errorMessage = response.data.errorDetails?.errorMessage || "Failed to fetch dashboard stats";
      log.error(errorMessage);
      throw new Error(errorMessage);
    }
  };