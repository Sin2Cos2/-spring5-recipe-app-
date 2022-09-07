package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService uomService;

    @GetMapping("/recipe/{id}/ingredients")
    public String getIngredients(@PathVariable String id, Model model) {
        Recipe recipe = recipeService.findById(Long.parseLong(id));
        model.addAttribute("recipe", recipe);

        return "/recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable Long recipeId, @PathVariable Long id, Model model) {
        IngredientCommand ingredient = ingredientService.findByRecipeIdAndIngredientId(recipeId, id);
        model.addAttribute("ingredient", ingredient);

        return "/recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable Long recipeId, @PathVariable Long id, Model model) {
        IngredientCommand ingredient = ingredientService.findByRecipeIdAndIngredientId(recipeId, id);
        model.addAttribute("ingredient", ingredient);

        model.addAttribute("uomList", uomService.findAll());

        return "/recipe/ingredient/ingredientForm";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable Long recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        model.addAttribute("ingredient", ingredientCommand);
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("uomList", uomService.findAll());

        return "/recipe/ingredient/ingredientForm";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable Long recipeId, @PathVariable Long id) {
        ingredientService.deleteByRecipeIdAndIngredientId(recipeId, id);

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    @PostMapping("/recipe/{id}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand command) {
        ingredientService.saveIngredientCommand(command);

        return "redirect:/recipe/" + command.getRecipeId() + "/ingredient/" + command.getId() + "/show";
    }

}
