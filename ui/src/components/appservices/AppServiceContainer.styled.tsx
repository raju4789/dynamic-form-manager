import { styled } from "@mui/material/styles";
import { Card, Grid, Box } from "@mui/material";

export const PageContainer = styled(Box)(({ theme }) => ({
    margin: theme.spacing(2),
    [theme.breakpoints.up("sm")]: {
      margin: theme.spacing(3),
    },
    [theme.breakpoints.up("md")]: {
      margin: theme.spacing(4),
    },
  }));
  
  export const ServiceGrid = styled(Grid)(() => ({
    display: "flex",
    flexDirection: "column",
    justifyContent: "stretch",
  }));
  
  export const ServiceCard = styled(Card)(({ theme }) => ({
    cursor: "pointer",
    transition: "transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out",
    height: "100%",
    "&:hover": {
      transform: "translateY(-5px)",
      boxShadow: theme.shadows[8],
    },
  }));
  
  export const IconContainer = styled("div")(({ theme }) => ({
    fontSize: "3rem",
    marginBottom: theme.spacing(2),
    color: theme.palette.primary.main,
  }));
  
  export const PageHeader = styled(Box)(({ theme }) => ({
    textAlign: "center",
    marginBottom: theme.spacing(4),
    color: "#1976d2",
  }));