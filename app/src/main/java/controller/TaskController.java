/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author Sheldon
 */
public class TaskController {

    public void save(Task task) throws ClassNotFoundException {
        String sql = "INSERT INTO tasks (idProject,"
                + "name, description,"
                + "completed,notes,"
                + "deadline, createdAt, updatedAt)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar a tarefa");

        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void updade(Task task) throws ClassNotFoundException {
        String sql = "UPDATE tasks SET"
                + " idProject = ?,"
                + " name = ?,"
                + " description = ?,"
                + " notes = ?,"
                + " completed = ?,"
                + " deadline = ?,"
                + " createdAt = ?,"
                + " updatedAt = ?"
                + " WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // estabelecendo conexao com o banco
            connection = ConnectionFactory.getConnection();
            // preparando a querry
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(5, task.isCompleted());
            statement.setString(4, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa");

        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void removeById(int taskId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // conexao com o banco
            conn = ConnectionFactory.getConnection();
            //preparando a query
            statement = conn.prepareStatement(sql);
            // setando os valores
            statement.setInt(1, taskId);
            //executando a  querry
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar tarefa");

        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public List<Task> getAll(int idProject) throws ClassNotFoundException {
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        List<Task> tasks = new ArrayList<Task>();
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            //setando valor que corresponde ao filtro da busca
            statement.setInt(1, idProject);
            // valor retornado pela execuçao da query
            resultSet = statement.executeQuery();
            
            while(resultSet . next()){
                
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                tasks.add(task);
            }
        
        
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir tarefa");

        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        return tasks;
    }

}
