import * as React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import TableRowsIcon from '@mui/icons-material/TableRows';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import { AddBox } from '@mui/icons-material';
import { useNavigate } from 'react-router';
import { Anchor, Role } from '../../types/Types';
import useLocalStorage from '../../hooks/useLocalStorage';

interface ISidebarProps {
  sideBarDirection: { left: boolean; };
  toggleDrawer: (anchor: Anchor, open: boolean) => (event: React.KeyboardEvent | React.MouseEvent) => void;
}

export default function Sidebar(props: ISidebarProps) {
  const { sideBarDirection, toggleDrawer } = props;

  const navigate = useNavigate();

  const { getItem: getRole } = useLocalStorage('role' as string);
  const { getItem: getIsAuthenticated } = useLocalStorage('isAuthenticated' as string);

  const sideBarItems: string[] = ['Home'];

  if (getIsAuthenticated() && getRole() === Role.ADMIN) {
    sideBarItems.push('Dashboard');
  }

  const handleDashboardClick = () => {
    navigate('/dashboard');
  };

  const handleHomeClick = () => {
    navigate('/services');
  };

  const list = (anchor: Anchor) => (
    <Box
      sx={{ width: anchor === 'top' || anchor === 'bottom' ? 'auto' : 250 }}
      role="presentation"
      onClick={toggleDrawer(anchor, false)}
      onKeyDown={toggleDrawer(anchor, false)}
    >
      <List>
        {sideBarItems.map((text, index) => (
          <ListItem key={text} disablePadding>
            <ListItemButton>
              {(() => {
                switch (index) {
                  case 0:
                    return (
                      <Box onClick={handleHomeClick} sx={{ display: 'flex' }}>
                        <TableRowsIcon />
                        <ListItemText primary={text} sx={{ marginLeft: '8px', marginTop: 0 }} />
                      </Box>
                    );

                  case 1:
                    return (
                      <Box onClick={handleDashboardClick} sx={{ display: 'flex' }}>
                        <AddBox />
                        <ListItemText primary={text} sx={{ marginLeft: '8px', marginTop: 0 }} />
                      </Box>
                    );
                  default:
                    return null;
                }
              })()}
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </Box>
  );

  return (
    <div>
      {(['left'] as const).map((anchor) => (
        <React.Fragment key={anchor}>
          <Drawer
            anchor={anchor}
            open={sideBarDirection[anchor]}
            onClose={toggleDrawer(anchor, false)}
          >
            {list(anchor)}
          </Drawer>
        </React.Fragment>
      ))}
    </div>
  );
}
