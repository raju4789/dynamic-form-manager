import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import useLocalStorage from '../../hooks/useLocalStorage';

interface ProtectedRouteProps {
    allowedRoles?: string[]; 
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ allowedRoles }) => {
    const { getItem: getIsAuthenticated } = useLocalStorage('isAuthenticated' as string);
    const { getItem: getRole } = useLocalStorage('role' as string);

    const isAuthenticated = getIsAuthenticated();
    const userRole = getRole() as string;

    if (!isAuthenticated) {
        return (
            <Navigate
                to="/error"
                replace
                state={{ message: "Authentication required" }}
            />
        );
    }

    if (allowedRoles && !allowedRoles.includes(userRole)) {
        return (
            <Navigate
                to="/error"
                replace
                state={{ message: "You do not have access to this page" }}
            />
        );
    }

    return <Outlet />;
};

export default ProtectedRoute;