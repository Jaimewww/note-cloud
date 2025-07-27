package edu.cc.notecloud.services;

import edu.cc.notecloud.dto.TaskDTO;
import edu.cc.notecloud.entity.Task;
import edu.cc.notecloud.entity.User;
import edu.cc.notecloud.view.UserBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class TaskService {
    @Inject
    CrudGenericService crudService;
    @Inject
    UserBean userBean;

    public void createTask(TaskDTO taskDTO) {
        Task task = new Task();
        User user = userBean.getLoggedUser();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setActive(true);
        task.setCompleted(false);
        task.setUser(user);
        crudService.create(task);
    }

    public void uncheckTask(Long taskId) {
        Task task = crudService.find(Task.class, taskId);
        if (task != null) {
            task.setCompleted(false);
            crudService.update(task);
        }
    }
}
