import React, { useState, useEffect } from "react";
import { CardContent, Typography, Grid, Box, CircularProgress } from "@mui/material";
import { Computer } from "@mui/icons-material";
import { getServices } from "../../services/FormManagementService";
import { IService } from "../../types/Types";
import { useNavigate } from "react-router";
import { ServiceGrid, ServiceCard, IconContainer, PageContainer, PageHeader } from "./AppServiceContainer.styled";
import log from "../../logger";
import { COLORS } from "../../constants/ColorConstants";

interface IServiceProps {
  id: string;
  icon: React.ReactNode;
  title: string;
  description: string;
  backgroundColor: string;
}

const Service: React.FC<IServiceProps> = ({ id, icon, title, description, backgroundColor }) => {
  const navigate = useNavigate();

  const handleServiceClick = () => {
    navigate(`/service/${id}`);
  };

  return (
    <ServiceGrid item xs={12} sm={6} md={4}>
      <ServiceCard style={{ backgroundColor }} onClick={handleServiceClick}>
        <CardContent>
          <IconContainer>{icon}</IconContainer>
          <Typography variant="h5" component="div" gutterBottom>
            {title}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {description}
          </Typography>
        </CardContent>
      </ServiceCard>
    </ServiceGrid>
  );
};

const AppServiceContainer: React.FC = () => {
  const [services, setServices] = useState<IService[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      setError(null);
      try {
        const data = await getServices();
        const mappedServices = data.map((service) => ({
          id: service.serviceId,
          title: service.serviceName,
          description: "Service description here",
          icon: "Computer",
        }));
        setServices(mappedServices);
      } catch (err) {
        log.error("Error fetching services:", err);
        setError("Failed to load services. Please try again later.");
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  const getRandomColor = (): string => {

    return COLORS[Math.floor(Math.random() * COLORS.length)];
  };

  return (
    <PageContainer>
      <PageHeader>
        <Typography variant="h4" component="h1" fontWeight="bold">
          App Services
        </Typography>
      </PageHeader>

      {isLoading ? (
        <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", height: "50vh" }}>
          <CircularProgress color="primary" />
        </Box>
      ) : error ? (
        <Typography variant="body1" color="error" textAlign="center">
          {error}
        </Typography>
      ) : services.length > 0 ? (
        <Grid container spacing={3}>
          {services.map((service) => (
            <Service
              key={service.id}
              id={service.id}
              icon={<Computer />}
              title={service.title}
              description={service.description}
              backgroundColor={getRandomColor()}
            />
          ))}
        </Grid>
      ) : (
        <Typography variant="body1" color="text.secondary" textAlign="center">
          No services available at the moment.
        </Typography>
      )}
    </PageContainer>
  );
};

export default AppServiceContainer;