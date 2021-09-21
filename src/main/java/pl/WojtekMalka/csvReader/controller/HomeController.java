package pl.WojtekMalka.csvReader.controller;

import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.WojtekMalka.csvReader.entity.Client;
import pl.WojtekMalka.csvReader.repository.ClientRepository;
import pl.WojtekMalka.csvReader.service.ReaderService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    private static final String PAGE_HOME = "home";

    private static final String UPLOAD_DIR = "C:\\Users\\WojtekM\\Repozytorium\\";

    private final ClientRepository clientRepository;

    @GetMapping("/")
    String showHome(Model model) {
        return PAGE_HOME;
    }

    @PostMapping("/loadFile")
    String loadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/";
    }

//    @PostMapping("/saveFile")
//    String saveFile() throws IOException, CsvException {
//        List<String[]> file = ReaderService.readFile("C:\\Users\\WojtekM\\Repozytorium\\Test.csv");
//        for (String[] oneLine : file) {
//            Client client = new Client();
//                    client.setFirst_name(oneLine[0]);
//                    client.setLast_name(oneLine[1]);
//                    client.setBirth_date(oneLine[2]);
//                    client.setPhone_no(oneLine[3]);
//            clientRepository.save(client);
//        }
//        return "redirect:/";
//    }
}