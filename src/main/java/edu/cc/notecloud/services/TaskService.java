package edu.cc.notecloud.services;

import edu.cc.notecloud.dto.TaskDTO;
import edu.cc.notecloud.entity.Task;
import edu.cc.notecloud.entity.User;
import edu.cc.notecloud.view.UserBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

@Stateless
public class TaskService {
    @Inject
    CrudGenericService crudService;
    @Inject
    UserBean userBean;

    public Task createTask(TaskDTO taskDTO) {
        Task task = new Task();
        User user = userBean.getLoggedUser();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setActive(true);
        task.setCompleted(false);
        task.setUser(user);
        crudService.create(task);
        return task;
    }

    public void uncheckTask(Long taskId) {
        Task task = crudService.find(Task.class, taskId);
        if (task != null) {
            task.setCompleted(false);
            crudService.update(task);
        }
    }

    public List<Task> getTasks() {
        String query = "SELECT t FROM Task t WHERE t.user.id = :userId AND t.active = true AND t.completed = false";

        Map<String, Object> parameters = Map.of("userId", userBean.getLoggedUser().getId());

        return crudService.findWithQuery(query, parameters);
    }

    public Task updateTask(Task task) {
        if (task != null) {
            return crudService.update(task);
        }
        return null;
    }
}
