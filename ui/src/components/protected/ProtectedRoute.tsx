import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import useLocalStorage from "../../hooks/useLocalStorage";


const ProtectedRoute: React.FC = () => {
    const { getItem: getIsAuthenticated } = useLocalStorage('isAuthenticated' as string);

    console.log("ProtectedRoute: isAuthenticated: ", getIsAuthenticated());

    if (getIsAuthenticated()) {
        return <Outlet />;
    } else {
        return (
        <Navigate
            to="/error"
            replace
            state={{ message: "Authentication required" }} 
        />
        );
    }
};

export default ProtectedRoute;