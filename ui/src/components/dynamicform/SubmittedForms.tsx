import React, { useEffect, useState } from "react";
import { useTable, useSortBy, useFilters, Column } from "react-table";
import { useParams } from "react-router-dom";
import {
  Box,
  CircularProgress,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  TableSortLabel,
  TextField,
} from "@mui/material";
import { getFormDataByUserIdAndServiceId } from "../../services/UserFormService";
import { GetFormDataByServiceIdAndUserIdResponse } from "../../types/Types";

interface ApiResponse {
  [key: string]: any;
}

const SubmittedForms: React.FC = () => {
  const { serviceId, userId } = useParams<{ serviceId: string; userId: string }>();
  const [formData, setFormData] = useState<Record<string, any>[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  useEffect(() => {
    const fetchSubmittedForms = async () => {
      setLoading(true);
      setErrorMessage(null);
      try {
        const response: GetFormDataByServiceIdAndUserIdResponse = await getFormDataByUserIdAndServiceId(serviceId || "", userId || "");
        setFormData(response.formData);
      } catch (err: any) {
        setErrorMessage("Failed to fetch submitted forms. Please try again.");
      } finally {
        setLoading(false);
      }
    };

    fetchSubmittedForms();
  }, [serviceId, userId]);

  const tableColumns: Column<ApiResponse>[] = React.useMemo(
    () =>
      formData.length > 0
        ? Object.keys(formData[0]).map((key) => ({
            Header: key,
            accessor: key,
          }))
        : [],
    [formData]
  );

  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    prepareRow,
    setFilter,
  } = useTable<ApiResponse>(
    {
      columns: tableColumns,
      data: formData,
    },
    useFilters,
    useSortBy
  );

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
        <CircularProgress />
      </Box>
    );
  }

  if (errorMessage) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
        <Typography color="error">{errorMessage}</Typography>
      </Box>
    );
  }

  if (formData.length === 0) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
        <Typography variant="h6" color="textSecondary">
          No submitted forms available.
        </Typography>
      </Box>
    );
  }

  return (
    <Box padding={4}>
      <Typography variant="h4" gutterBottom>
        Submitted Forms
      </Typography>
      <Table {...getTableProps()} style={{ border: "1px solid #ddd", width: "100%" }}>
        <TableHead>
          {headerGroups.map((headerGroup) => (
            <TableRow {...headerGroup.getHeaderGroupProps()}>
              {headerGroup.headers.map((column) => (
                <TableCell
                  {...column.getHeaderProps(column.getSortByToggleProps())}
                  style={{ fontWeight: "bold", cursor: "pointer" }}
                >
                  {column.render("Header")}
                  <TableSortLabel
                    active={column.isSorted}
                    direction={column.isSortedDesc ? "desc" : "asc"}
                  />
                  <TextField
                    size="small"
                    variant="outlined"
                    placeholder={`Filter ${column.render("Header")}`}
                    onChange={(e) => setFilter(column.id, e.target.value)}
                    style={{ marginTop: "8px", width: "100%" }}
                  />
                </TableCell>
              ))}
            </TableRow>
          ))}
        </TableHead>
        <TableBody {...getTableBodyProps()}>
          {rows.map((row) => {
            prepareRow(row);
            return (
              <TableRow {...row.getRowProps()}>
                {row.cells.map((cell) => (
                  <TableCell {...cell.getCellProps()}>{cell.render("Cell")}</TableCell>
                ))}
              </TableRow>
            );
          })}
        </TableBody>
      </Table>
    </Box>
  );
};

export default SubmittedForms;