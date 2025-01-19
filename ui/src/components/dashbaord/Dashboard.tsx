import React from "react";
import { styled } from "@mui/material/styles";
import { Typography, Grid, Box, Paper, Divider, CircularProgress } from "@mui/material";
import {
  BarChart,
  Bar,
  LineChart,
  Line,
  PieChart,
  Pie,
  Cell,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";
import { getDashboardStats } from "../../services/DashboardService";

interface ServiceSubmission {
  serviceId: number;
  serviceName: string;
  totalSubmissions: number;
}

interface SubmissionTrend {
  date: string;
  totalSubmissions: number;
}

interface DashboardStats {
  totalSubmissionsPerService: ServiceSubmission[];
  submissionTrends: SubmissionTrend[];
}

const Container = styled(Box)(({ theme }) => ({
  padding: theme.spacing(4),
  marginTop: theme.spacing(8),
  backgroundColor: theme.palette.background.default,
  minHeight: "100vh",
}));

const Card = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(3),
  borderRadius: theme.shape.borderRadius,
  boxShadow: theme.shadows[3],
  height: "100%",
  display: "flex",
  flexDirection: "column",
  justifyContent: "space-between",
  marginBottom: theme.spacing(4),
  "&:hover": {
    boxShadow: theme.shadows[6],
  },
}));

const ChartContainer = styled(Box)(({ theme }) => ({
  width: "100%",
  height: 400,
  padding: theme.spacing(2),
  margin: theme.spacing(2),
}));

const Header = styled(Typography)(({ theme }) => ({
  fontWeight: "bold",
  color: theme.palette.primary.main,
  marginBottom: theme.spacing(2),
}));

const COLORS = ["#0088FE", "#00C49F", "#FFBB28", "#FF8042", "#A28DD0"];

const Dashboard: React.FC = () => {
  const [dashboardStats, setDashboardStats] = React.useState<DashboardStats>({
    totalSubmissionsPerService: [],
    submissionTrends: [],
  });
  const [isLoading, setIsLoading] = React.useState<boolean>(true);

  React.useEffect(() => {
    const fetchStats = async () => {
      try {
        const data = await getDashboardStats();
        setDashboardStats(data);
      } catch (error) {
        console.error("Error fetching dashboard stats:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchStats();
  }, []);

  const totalSubmissions = dashboardStats.totalSubmissionsPerService.reduce(
    (acc, curr) => acc + curr.totalSubmissions,
    0
  );

  if (isLoading) {
    return (
      <Container>
        <Typography variant="h4" align="center" gutterBottom>
          Loading Dashboard...
        </Typography>
        <Box display="flex" justifyContent="center" alignItems="center" height="50vh">
          <CircularProgress />
        </Box>
      </Container>
    );
  }

  return (
    <Container>
      <Typography variant="h3" gutterBottom>
        Dashboard
      </Typography>
      <Divider sx={{ marginBottom: 4 }} />

      <Grid container spacing={4} alignItems="stretch">
        <Grid item xs={12} md={4} marginBottom={5}>
          <Card
            sx={{
              display: "flex",
              flexDirection: "column",
              justifyContent: "center",
              alignItems: "center",
              textAlign: "center",
              position: "relative",
            }}
          >
            <Typography
              variant="h6"
              color="textSecondary"
              sx={{
                position: "absolute",
                top: "10px",
                left: "50%",
                transform: "translateX(-50%)",
                fontSize: "1.5rem",
                fontWeight: "bold",
              }}
            >
              Total Submissions
            </Typography>
            <Typography
              variant="h1"
              color="primary"
              sx={{
                fontSize: "5rem",
                fontWeight: "bold",
              }}
            >
              {totalSubmissions}
            </Typography>
          </Card>
        </Grid>

        <Grid item xs={12} md={8} marginBottom={5}>
          <Card>
            <Header variant="h6">Submissions Per Service</Header>
            <ChartContainer>
              <ResponsiveContainer>
                <BarChart
                  data={dashboardStats.totalSubmissionsPerService}
                  margin={{ top: 20, right: 30, left: 30, bottom: 80 }}
                >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis
                    dataKey="serviceName"
                    angle={-45}
                    textAnchor="end"
                    interval={0}
                  />
                  <YAxis />
                  <Tooltip />
                  <Legend layout="horizontal" align="center" verticalAlign="bottom" />
                  <Bar
                    dataKey="totalSubmissions"
                    fill="url(#colorUv)"
                    name="Total Submissions"
                  />
                  <defs>
                    <linearGradient id="colorUv" x1="0" y1="0" x2="0" y2="1">
                      <stop offset="5%" stopColor="#8884d8" stopOpacity={0.8} />
                      <stop offset="95%" stopColor="#8884d8" stopOpacity={0.2} />
                    </linearGradient>
                  </defs>
                </BarChart>
              </ResponsiveContainer>
            </ChartContainer>
          </Card>
        </Grid>

        <Grid item xs={12} marginBottom={5} paddingBottom={0}>
          <Card>
            <Header variant="h6">Submission Trends</Header>
            <ChartContainer>
              <ResponsiveContainer>
                <LineChart
                  data={dashboardStats.submissionTrends}
                  margin={{ top: 20, right: 30, left: 30, bottom: 50 }}
                >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis
                    dataKey="date"
                    tickFormatter={(date) =>
                      new Date(date).toLocaleDateString()
                    }
                  />
                  <YAxis />
                  <Tooltip
                    labelFormatter={(date) =>
                      new Date(date).toLocaleDateString()
                    }
                  />
                  <Legend layout="horizontal" align="center" verticalAlign="bottom" />
                  <Line
                    type="monotone"
                    dataKey="totalSubmissions"
                    stroke="url(#colorLine)"
                    name="Daily Submissions"
                    strokeWidth={3}
                  />
                  <defs>
                    <linearGradient id="colorLine" x1="0" y1="0" x2="1" y2="0">
                      <stop offset="5%" stopColor="#82ca9d" stopOpacity={0.8} />
                      <stop offset="95%" stopColor="#82ca9d" stopOpacity={0.2} />
                    </linearGradient>
                  </defs>
                </LineChart>
              </ResponsiveContainer>
            </ChartContainer>
          </Card>
        </Grid>

        <Grid item xs={12} marginBottom={10}>
          <Card>
            <Header variant="h6">Most Frequently Used Services</Header>
            <ChartContainer>
              <ResponsiveContainer>
                <PieChart>
                  <Pie
                    data={dashboardStats.totalSubmissionsPerService}
                    dataKey="totalSubmissions"
                    nameKey="serviceName"
                    cx="50%"
                    cy="50%"
                    outerRadius={150}
                    fill="#8884d8"
                    label={(entry) => entry.serviceName}
                  >
                    {dashboardStats.totalSubmissionsPerService.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                    ))}
                  </Pie>
                  <Tooltip />
                </PieChart>
              </ResponsiveContainer>
            </ChartContainer>
          </Card>
        </Grid>
      </Grid>
    </Container>
  );
};

export default Dashboard;