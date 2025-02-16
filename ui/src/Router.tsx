import { createBrowserRouter } from 'react-router-dom';
import Login from './components/login/Login';
import Root from './components/root/Root';
import AppServiceContainer from './components/appservices/AppServiceContainer';
import DynamicForm from './components/dynamicform/DynamicForm';
import Dashboard from './components/dashbaord/Dashboard';
import SubmittedForms from './components/dynamicform/SubmittedForms';
import { ErrorPage } from './components/error/ErrorPage';
import ProtectedRoute from './components/protected/ProtectedRoute'; 
import { UserRole } from './constants/roles';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Root />,
    errorElement: <ErrorPage />,
    children: [
      {
        index: true,
        element: <Login />,
      },
      {
        path: 'login',
        element: <Login />,
      },
      {
        path: 'error',
        element: <ErrorPage />,
      },
      // Protected routes
      {
        element: <ProtectedRoute />, // Wrap protected routes with ProtectedRoute
        children: [
          {
            path: 'services',
            element: <AppServiceContainer />,
          },
          {
            path: 'service/:id',
            element: <DynamicForm />,
          },
          {
            path: 'forms/:serviceId/:userId',
            element: <SubmittedForms />,
          },
        ],
      },
      // Protected route with role-based access for dashboard
      {
        element: <ProtectedRoute allowedRoles={[UserRole.ADMIN]} />, // Only allow ADMIN role
        children: [
          {
            path: 'dashboard',
            element: <Dashboard />,
          },
        ],
      },
    ],
  },
]);

export default router;