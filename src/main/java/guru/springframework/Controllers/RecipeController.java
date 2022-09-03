package guru.springframework.Controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@AllArgsConstructor
public class RecipeController {

    private final RecipeService service;

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable Long id, Model model) {

        model.addAttribute("recipe", service.findById(id));

        return "/recipe/show";
    }

    @RequestMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", service.findCommandById(id));

        return "recipe/recipeForm";
    }

    @RequestMapping({"/recipe/new"})
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/newRecipeForm";
    }

    @PostMapping("/recipe/new")
    public String saveNewRecipe(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = service.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/ingredients";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = service.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show" ;
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable Long id) {
        log.debug("Deleting id: " + id);
        service.deleteById(id);

        return "redirect:/";
    }
}
