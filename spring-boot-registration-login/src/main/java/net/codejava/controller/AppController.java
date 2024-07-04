package net.codejava.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.codejava.model.Profile;
import net.codejava.model.User;
import net.codejava.model.User.Role;
import net.codejava.repository.UserRepository;

@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;

	@Value("${UPLOAD_DIR}")
	private String uploadDir;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}

	@GetMapping("/login")
	public String showLoginForm() {
		return "login_form";
	}

	@PostMapping("/login")
	public String processLogin(@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpSession session) {
		User user = userRepo.findByEmail(email);

		if (user != null && passwordMatches(password, user.getPassword())) {
			session.setAttribute("loggedInUser", user);
			return "redirect:/welcome";
		} else {
			return "redirect:/login?error";
		}
	}

	private boolean passwordMatches(String rawPassword, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "signup_form";
	}

	@PostMapping("/process_register")
	public String processRegister(User user, @RequestParam(name = "role", defaultValue = "USER") Role role,
			@RequestParam("file") MultipartFile file) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setRole(role);
		boolean isAdmin = role == Role.ADMIN;
		user.setIsAdmin(isAdmin);

		if (!file.isEmpty()) {
			try {
				File uploadDirFile = new File(uploadDir);
				if (!uploadDirFile.exists()) {
					uploadDirFile.mkdirs();
				}

				String originalFileName = file.getOriginalFilename();
				String filePath = uploadDir + File.separator + originalFileName;
				File dest = new File(filePath);
				file.transferTo(dest);
				user.setProfilePicture(originalFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		userRepo.save(user);
		return "register_success";
	}

	@GetMapping("/profile/view/{id}")
	public ModelAndView viewProfile(@PathVariable("id") Long id, HttpSession session) {
		User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID:" + id));
		session.setAttribute("loggedInUser", user);
		Profile profile = new Profile();
		profile.setFirstName(user.getFirstName());
		profile.setLastName(user.getLastName());
		profile.setFathersName(user.getFathersName());
		profile.setPassword(user.getPassword());
		profile.setPhoneNumber(user.getPhoneNumber());
		profile.setEmail(user.getEmail());
		profile.setFathersName(user.getFathersName());
		if (user.getBirthday() != null) {
			profile.setBirthday(user.getBirthday());
		}

		ModelAndView modelAndView = new ModelAndView("profile");
		modelAndView.addObject("profile", profile);
		modelAndView.addObject("profilePicture", user.getProfilePicture());
		return modelAndView;
	}

	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute("profile") Profile profile,
			@RequestParam("file") MultipartFile file,
			HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");
		user.setFirstName(profile.getFirstName());
		user.setLastName(profile.getLastName());
		user.setFathersName(profile.getFathersName());
		user.setPhoneNumber(profile.getPhoneNumber());
		user.setEmail(profile.getEmail());
		user.setFathersName(profile.getFathersName());
		if (profile.getBirthday() != null) {
			user.setBirthday(profile.getBirthday());
		}

		if (!file.isEmpty()) {
			try {
				File uploadDirFile = new File(uploadDir);
				if (!uploadDirFile.exists()) {
					uploadDirFile.mkdirs();
				}

				String originalFileName = file.getOriginalFilename();
				String filePath = uploadDir + File.separator + originalFileName;
				File dest = new File(filePath);
				file.transferTo(dest);
				user.setProfilePicture(originalFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		userRepo.save(user);
		session.setAttribute("loggedInUser", user);
		return "redirect:/";
	}
}
