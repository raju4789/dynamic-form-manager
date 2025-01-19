import { createBrowserRouter } from 'react-router-dom';
import Login from './components/login/Login';
import Root from './components/root/Root';
import AppServiceContainer from './components/appservices/AppServiceContainer';
import DynamicForm from './components/dynamicform/DynamicForm';
import Dashboard from './components/dashbaord/Dashboard';
import SubmittedForms from './components/dynamicform/SubmittedForms';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Root />,
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
        path: 'services',
        element: <AppServiceContainer />
      },
      {
        path: 'service/:id',
        element: <DynamicForm />
      },
      {
        path: 'dashboard',
        element: <Dashboard />
      },
      {
        path: 'forms/:serviceId/:userId',
        element: <SubmittedForms />
      }
    ],
  },
]);

export default router;
