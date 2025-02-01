import React from "react";
import { Link, useLocation } from "react-router-dom";

interface ErrorState {
  message?: string; // Optional custom error message
}

export const ErrorPage: React.FC = () => {
  const location = useLocation();
  const state = location.state as ErrorState; // Access the state passed via navigation

  // Default error message
  const defaultMessage = "An unexpected error occurred while loading this page.";

  // Determine the redirect path based on the error type
  const redirectPath = state?.message === "Authentication required" ? "/login" : "/services";

  // Determine the link text based on the error type
  const linkText = state?.message === "Authentication required" ? "Go to Login Page" : "Go to Services Page";

  return (
    <div style={{ padding: "2rem", textAlign: "center" }}>
      <h1>Oops! Something went wrong.</h1>
      <p>{state?.message || defaultMessage}</p> {/* Display custom or default message */}
      <Link
        to={redirectPath} // Redirect dynamically based on error type
        style={{
          display: "inline-block",
          marginTop: "1rem",
          padding: "0.5rem 1rem",
          backgroundColor: "#007bff",
          color: "#fff",
          textDecoration: "none",
          borderRadius: "4px",
          fontWeight: "bold",
        }}
      >
        {linkText}
      </Link>
    </div>
  );
};