package sec.helloweb;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWebWithDatabaseController {

    @Autowired
    private HelloMessageRepository helloMessageRepository;

    @RequestMapping("/")
    @ResponseBody
    public String home() {
      
         List<HelloMessage> messages = helloMessageRepository.findAll();
        if (messages.isEmpty()) {
            return "No content.";
        }
 
        Collections.shuffle(messages);
        return messages.get(0).getContent();

    }
}
