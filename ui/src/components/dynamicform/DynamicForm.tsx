import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  Box,
  CircularProgress,
  Grid,
  MenuItem,
  Select,
  TextField,
  Typography,
  Button,
} from "@mui/material";
import { getFieldsByServiceId, getLanguages } from "../../services/FormManagementService";
import { IField, ILanguage, UserFormRequest } from "../../types/Types";
import { PageContainer, LanguageDropdownContainer, LanguageDropdown, FormContainer, FormHeader } from "./DynamicForm.styled";
import useLocalStorage from '../../hooks/useLocalStorage';
import { submitFormData } from "../../services/UserFormService";

const DynamicForm: React.FC = () => {

    const navigate = useNavigate();
    
    const { id } = useParams<{ id: string }>();
    const [fields, setFields] = useState<IField[]>([]);
    const [language, setLanguage] = useState<number>(1);
    const [languages, setLanguages] = useState<ILanguage[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [formValues, setFormValues] = useState<Record<string, string>>({});
    const [alert, setAlert] = useState<{ message: string; type: "success" | "error" } | null>(null);
    const [isFormValid, setIsFormValid] = useState<boolean>(false);
  
    const { getItem: getUserName } = useLocalStorage("username" as string);
  
    useEffect(() => {
      const fetchData = async () => {
        setIsLoading(true);
        setError(null);
        try {
          const fields = await getFieldsByServiceId(id!);
          const languages = await getLanguages();
          setFields(fields);
          setLanguages(languages);
        } catch {
          setError("An error occurred while fetching the form.");
        } finally {
          setIsLoading(false);
        }
      };
  
      fetchData();
    }, [id]);
  
    useEffect(() => {
      const validateForm = () => {
        for (const field of fields) {
          const value = formValues[field.fieldName] || "";
  
          if (field.isRequired && !value) {
            setIsFormValid(false);
            return;
          }
  
          if (field.fieldValidation && !new RegExp(field.fieldValidation).test(value)) {
            setIsFormValid(false);
            return;
          }
        }
  
        setIsFormValid(true);
      };
  
      validateForm();
    }, [formValues, fields]);
  
    const handleLanguageChange = (event: React.ChangeEvent<{ value: unknown }>) => {
      setLanguage(event.target.value as number);
    };
  
    const handleInputChange = (fieldName: string, value: string) => {
      setFormValues((prev) => ({ ...prev, [fieldName]: value }));
    };
  
    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        console.log("Form submitted:", formValues);
      
        const userFormRequest: UserFormRequest = {
          formId: crypto.randomUUID(),
          serviceId: +(id ?? 0),
          userId: getUserName(),
          formData: formValues,
        };
      
        console.log("User form request:", userFormRequest);
      
        try {
          const response = await submitFormData(userFormRequest); // Get the response from submitFormData
      
          if (response.status) {
            // If the response status is true, show success message
            setAlert({ message: "Form submitted successfully!", type: "success" });
            console.log("Form submission response data:", response.formData);
          } else {
            // If the response status is false, show an error message
            setAlert({
              message: "Form submission failed. Please try again.",
              type: "error",
            });
          }
        } catch (error: any) {
          console.error("Error submitting form data:", error.message || error);
          setAlert({
            message: `Failed to submit form: ${error.message || "Unknown error occurred"}`,
            type: "error",
          });
        }
      };

      const onAllSubmittedFormsClick = ()=> {
        navigate(`/forms/${id}/${getUserName()}`);
      }
  
    const renderField = (field: IField) => {
      const label = field.labels.find((l) => l.languageId === language)?.labelName || "Select one";
      const placeholder =
        field.placeHolders.find((p) => p.languageId === language)?.placeHolderName || "";
      const errorMessage =
        field.fieldValidationErrorMessages.find((e) => e.languageId === language)
          ?.fieldValidationErrorMessage || "";
  
      switch (field.fieldType) {
        case "number":
          return (
            <TextField
              fullWidth
              type="number"
              label={label}
              placeholder={placeholder}
              value={formValues[field.fieldName] || ""}
              onChange={(e) => handleInputChange(field.fieldName, e.target.value)}
              error={
                !!field.fieldValidation &&
                !new RegExp(field.fieldValidation).test(formValues[field.fieldName] || "")
              }
              helperText={
                !!field.fieldValidation &&
                !new RegExp(field.fieldValidation).test(formValues[field.fieldName] || "")
                  ? errorMessage
                  : ""
              }
              inputProps={{
                maxLength: field.maxLength > 0 ? field.maxLength : undefined,
              }}
            />
          );
  
        case "text":
          return (
            <TextField
              fullWidth
              type="text"
              label={label}
              placeholder={placeholder}
              value={formValues[field.fieldName] || ""}
              onChange={(e) => handleInputChange(field.fieldName, e.target.value)}
              error={
                !!field.fieldValidation &&
                !new RegExp(field.fieldValidation).test(formValues[field.fieldName] || "")
              }
              helperText={
                !!field.fieldValidation &&
                !new RegExp(field.fieldValidation).test(formValues[field.fieldName] || "")
                  ? errorMessage
                  : ""
              }
              inputProps={{
                maxLength: field.maxLength > 0 ? field.maxLength : undefined,
              }}
            />
          );
  
        case "option":
          return (
            <Select
              fullWidth
              label={label}
              value={formValues[field.fieldName] || ""}
              onChange={(e) => handleInputChange(field.fieldName, e.target.value as string)}
              displayEmpty
            >
              <MenuItem value="" disabled>
                {placeholder}
              </MenuItem>
              {field.fieldOptions.map((option) => (
                <MenuItem key={option.fieldOptionId} value={option.name}>
                  {option.label}
                </MenuItem>
              ))}
            </Select>
          );
  
        default:
          return (
            <TextField
              fullWidth
              type="text"
              label={label}
              placeholder={placeholder}
              value={formValues[field.fieldName] || ""}
              onChange={(e) => handleInputChange(field.fieldName, e.target.value)}
              error={
                !!field.fieldValidation &&
                !new RegExp(field.fieldValidation).test(formValues[field.fieldName] || "")
              }
              helperText={
                !!field.fieldValidation &&
                !new RegExp(field.fieldValidation).test(formValues[field.fieldName] || "")
                  ? errorMessage
                  : ""
              }
              inputProps={{
                maxLength: field.maxLength > 0 ? field.maxLength : undefined,
              }}
            />
          );
      }
    };
  
    if (isLoading) {
      return (
        <PageContainer>
          <CircularProgress />
        </PageContainer>
      );
    }
  
    if (error) {
      return (
        <PageContainer>
          <Typography color="error">{error}</Typography>
        </PageContainer>
      );
    }
  
    return (
      <PageContainer>
        {alert && (
          <Box
            sx={{
              backgroundColor: alert.type === "success" ? "#f0fdf4" : "#fef2f2",
              color: alert.type === "success" ? "#166534" : "#991b1b",
              padding: "16px",
              borderRadius: "8px",
              marginBottom: "16px",
              textAlign: "center",
            }}
          >
            {alert.message}
          </Box>
        )}
        <LanguageDropdownContainer>
          <LanguageDropdown
            value={language}
            onChange={handleLanguageChange}
            displayEmpty
          >
            {languages.map((lang: ILanguage) => (
              <MenuItem key={lang.languageId} value={lang.languageId}>
                {lang.languageName}
              </MenuItem>
            ))}
          </LanguageDropdown>
        </LanguageDropdownContainer>
  
        <FormContainer>
          <FormHeader>
            <Typography variant="h4" component="h1" fontWeight="bold">
              Service Form
            </Typography>
          </FormHeader>
          <form onSubmit={handleSubmit}>
            <Grid container spacing={2}>
              {fields.map((field) => (
                <Grid item xs={12} sm={6} key={field.fieldId}>
                  {renderField(field)}
                </Grid>
              ))}
            </Grid>
            <Box mt={2} display="flex" justifyContent="center">
              <Button type="submit" variant="contained" color="primary" disabled={!isFormValid}>
                Submit
              </Button>
            </Box>
          </form>
          <Box mt={2} display="flex" justifyContent="center">
              <Button type="submit" variant="contained" color="primary" onClick={onAllSubmittedFormsClick}>
                All submitted forms
              </Button>
         </Box>
        </FormContainer>
      </PageContainer>
    );
  };
  
  export default DynamicForm;