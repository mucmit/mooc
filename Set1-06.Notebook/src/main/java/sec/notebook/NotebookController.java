package sec.notebook;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotebookController {
     private List<String> list;

    public NotebookController() {
        this.list = new ArrayList<>();
    }

    @RequestMapping("/")
    public String home(Model model, @RequestParam(required = false) String content) {
        if (content != null && !content.trim().isEmpty()) {
            if(list.size() == 10)
            {
             list.remove(0);
            }
            
            this.list.add(content.trim());
        }
        
        

        model.addAttribute("list", list);
        return "index";
    }

}
