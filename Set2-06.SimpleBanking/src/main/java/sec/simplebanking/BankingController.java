package sec.simplebanking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BankingController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String add(Model model, @RequestParam String name, @RequestParam String iban) {
        if (name.trim().isEmpty() || iban.trim().isEmpty()) {
            return "redirect:/";
        }

        Client client = clientRepository.findByName(name);
        if (client == null) {
            client = new Client();
            client.setName(name);
            client = clientRepository.save(client);
        }

        Account account = accountRepository.findByIban(iban);
        if (account == null) {
            account = new Account();
            account.setIban(iban);
            account.setBalance(100);
            account = accountRepository.save(account);
        }

        // DO SOMETHING HERE
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        client.setAccounts(accountList);
        clientRepository.save(client);
        model.addAttribute("clients", clientRepository.findAll());
        return "redirect:/";
    }
}