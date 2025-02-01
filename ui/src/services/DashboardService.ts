import { AxiosResponse } from 'axios';
import { DashboardData, ICommonApiResponse } from '../types/Types';
import log from "../logger";
import createAxiosInstance from '../utils/AxiosInstanceFactory';

const axiosInstance = createAxiosInstance('/api/v1/dashboard');

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