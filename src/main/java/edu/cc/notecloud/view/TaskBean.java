package edu.cc.notecloud.view;

import edu.cc.notecloud.dto.TaskDTO;
import edu.cc.notecloud.entity.Task;
import edu.cc.notecloud.services.TaskService;
import jakarta.annotation.PostConstruct;
import jakarta.el.ELContext;
import jakarta.el.ValueExpression;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIData;
import jakarta.faces.component.html.HtmlSelectBooleanCheckbox;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;

import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class TaskBean implements Serializable {
    @Inject
    TaskService taskService;

    @Valid
    private TaskDTO taskDTO = new TaskDTO();

    private List<Task> tasks;

    @PostConstruct
    public void init() {
        try {
            tasks = taskService.getTasks();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void createTask() {
        taskService.createTask(taskDTO);
    }

    public void onCheckboxChange(AjaxBehaviorEvent event) {
        FacesContext ctx = FacesContext.getCurrentInstance();

        // 1) Asegurar que venimos de un checkbox booleano
        HtmlSelectBooleanCheckbox checkbox = (HtmlSelectBooleanCheckbox) event.getComponent();

        // 2) Obtener el nuevo valor booleano ya actualizado en el ciclo JSF
        ValueExpression ve = checkbox.getValueExpression("value");
        if (ve == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo obtener el valor del checkbox."));
            return;
        }
        ELContext el = ctx.getELContext();
        Boolean newCompleted = (Boolean) ve.getValue(el);

        // 3) Subir en el árbol hasta el UIData para recuperar la fila actual (Task)
        UIComponent c = checkbox;
        while (c != null && !(c instanceof UIData)) {
            c = c.getParent();
        }
        if (!(c instanceof UIData)) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo ubicar la tabla de tareas."));
            return;
        }

        UIData table = (UIData) c;
        Object rowData = table.getRowData();
        if (!(rowData instanceof Task)) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo obtener la tarea de la fila."));
            return;
        }

        Task currentTask = (Task) rowData;

        // 4) Persistir el nuevo estado
        currentTask.setCompleted(Boolean.TRUE.equals(newCompleted));
        try {
            taskService.updateTask(currentTask);
            ctx.addMessage(null, new FacesMessage("Tarea '" + currentTask.getTitle() + "' actualizada"));
        } catch (Exception ex) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar la tarea."));
        }
    }



    public TaskDTO getTaskDTO() {
        return taskDTO;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
