export interface ILoginRequest {
  username: string;
  password: string;
}

export interface IErrorDetails {
  errorCode: number;
  errorMessage: string;
}

export interface ICommonApiResponse<T> {
  success: boolean;
  data: T;
  errorDetails: IErrorDetails | null;
}

export interface ILoginResponse {
  fullName: string;
  username: string;
  token: string;
  role?: Role;
}

export interface IAppServiceResponse {
  serviceId: string;
  serviceName: string;
}

export interface IAppOutletContext {
  userName: string;
  jwt: string;
  isAuthenticated: boolean;
  roles: string[];
  onLogin: (data: ILoginRequest) => Promise<void>;
  onLogout: () => void;
  apiErrorMessage: string;
}

export interface IHeaderProps {
  onLogout: () => void;
  isAuthenticated: boolean;
  userName: string;
  roles?: string[];
}

export interface DropdownOption {
  value: string;
  label: string;
}

export type Anchor = 'top' | 'left' | 'bottom' | 'right';
export enum Role { ADMIN = 'ADMIN', USER = 'USER' }

export interface IFieldValidationErrorMessage {
  languageId: number;
  fieldValidationErrorMessage: string;
}

export interface ILabel {
  languageId: number;
  labelName: string;
}

export interface IPlaceHolder {
  languageId: number;
  placeHolderName: string;
}

export interface IFieldOption {
  fieldOptionId: number;
  fieldId: number;
  name: string;
  label: string;
  createdAt: string;
  updatedAt: string;
}

export interface IField {
  fieldId: number;
  fieldName: string;
  fieldType: "text" | "number" | "option";
  fieldValidation: string;
  fieldValidationErrorMessages: IFieldValidationErrorMessage[];
  labels: ILabel[];
  placeHolders: IPlaceHolder[];
  maxLength: number;
  defaultValue: string;
  fieldOptions: IFieldOption[];
}

export interface ILanguage {
  languageId: number;
  languageCode: string;
  languageName: string;
}

export interface IService {
  id: string;
  title: string;
  description: string;
  icon: string; // Icon name or identifier
}

export interface DashboardData {
  totalSubmissionsPerService: SubmissionPerService[];
  submissionTrends: SubmissionTrend[];
}

export interface SubmissionPerService {
  serviceId: number;
  serviceName: string;
  totalSubmissions: number;
}

interface SubmissionTrend {
  date: string; 
  totalSubmissions: number;
}

export interface UserFormRequest {
  formId: string;
  serviceId: number;
  userId: string;
  formData: { [key: string]: any };
}

export interface UserFormResponse {
  status: boolean; 
  formData: Record<string, any>; 
}

export interface GetFormDataByServiceIdAndUserIdResponse {
  formData: Record<string, any>[]; 
}


