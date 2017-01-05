package sec.millionaire.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sec.millionaire.domain.DifficultyLevel;
import sec.millionaire.domain.Topic;
import sec.millionaire.domain.UserDetails;
import sec.millionaire.repository.DifficultyLevelRepository;
import sec.millionaire.repository.TopicRepository;
import sec.millionaire.repository.UserDetailsRepository;
import javax.servlet.http.HttpSession;

@Controller
public class MillionaireController {

    @Autowired
    private UserDetailsRepository userDetailsRepository;
    

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private DifficultyLevelRepository difficultyLevelRepository;
    
    @Autowired
    private HttpSession session;

    @RequestMapping("*")
    public String main() {
        return "redirect:/topics";
    }

    @RequestMapping("/incorrect")
    public String incorrect() {
        return "incorrect";
    }

    @RequestMapping("/finish")
    public String finish() {
        return "finish";
    }

  @RequestMapping(value = "/details", method = RequestMethod.POST)
    public String postData(@ModelAttribute UserDetails details) {

        Long topicId = (Long) session.getAttribute("topic");
        Long levelId = (Long) session.getAttribute("level");

        try {
            Topic topic = topicRepository.findOne(topicId);
            List<DifficultyLevel> difficultyLevels = difficultyLevelRepository.findByTopic(topic);
            Long max = difficultyLevels.stream().map(d -> d.getLevel()).max(Long::compareTo).get();
            if (levelId > max) {
                session.removeAttribute("level");
                session.removeAttribute("topic");
                session.removeAttribute("question");
                userDetailsRepository.save(details);
            }
        } catch (Exception e) {
            return "cheater";
        }

        return "thanks";
    }
}
