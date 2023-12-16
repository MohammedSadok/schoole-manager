package com.university.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.university.dao.DatabaseConnection;

/**
 * Servlet implementation class AddTask
 */
public class AddTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTask() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            // Implement your GET logic here
            // Example: Retrieve tasks from the database
            String sql = "SELECT * FROM tasks";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // Process the result set, e.g., format and send as response
                while (resultSet.next()) {
                    int taskId = resultSet.getInt("id");
                    String taskName = resultSet.getString("title");
                    String taskDescription = resultSet.getString("text");

                    response.getWriter().println("Task ID: " + taskId);
                    response.getWriter().println("Task Name: " + taskName);
                    response.getWriter().println("Task Description: " + taskDescription);
                    response.getWriter().println();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database connection error: " + e.getMessage());
        }
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            String taskName = request.getParameter("taskName");
            String taskDescription = request.getParameter("taskDescription");

            String sql = "INSERT INTO tasks (task_name, task_description) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, taskName);
                preparedStatement.setString(2, taskDescription);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    response.getWriter().println("Task added successfully!");
                } else {
                    response.getWriter().println("Failed to add task.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database connection error: " + e.getMessage());
        }
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            // Implement your PATCH logic here
            // Example: Update an existing task based on task ID
            String taskId = request.getParameter("taskId");
            String updatedTaskDescription = request.getParameter("updatedTaskDescription");

            String sql = "UPDATE tasks SET task_description = ? WHERE task_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, updatedTaskDescription);
                preparedStatement.setString(2, taskId);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    response.getWriter().println("Task updated successfully!");
                } else {
                    response.getWriter().println("Failed to update task.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database connection error: " + e.getMessage());
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            // Implement your DELETE logic here
            // Example: Delete a task based on task ID
            String taskId = request.getParameter("taskId");

            String sql = "DELETE FROM tasks WHERE task_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, taskId);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    response.getWriter().println("Task deleted successfully!");
                } else {
                    response.getWriter().println("Failed to delete task.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database connection error: " + e.getMessage());
        }
    }

}
