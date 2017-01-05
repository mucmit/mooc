package sec.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sec.domain.Task;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private List<Task> tasks;

    public TaskController() {
        this.tasks = new ArrayList<>();
        Task fixme = new Task();
        fixme.setName("Fix me.");
        this.tasks.add(fixme);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Task> list() {
        return this.tasks;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Task add(@RequestBody Task task) {
        this.tasks.add(task);
        return task;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Task delete(@PathVariable String id) {
        Task t = this.tasks.stream().filter(task -> task.getId().equals(id)).findFirst().get();
        this.tasks.remove(t);
        return t;
    }
}
