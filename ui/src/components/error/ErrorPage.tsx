import React from "react";
import { Link } from "react-router-dom";

export const ErrorPage: React.FC = () => (
  <div style={{ padding: "2rem", textAlign: "center" }}>
    <h1>Oops! Something went wrong.</h1>
    <p>An unexpected error occurred while loading this page.</p>
    <Link
      to="/services"
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
      Go to Home Page
    </Link>
  </div>
);