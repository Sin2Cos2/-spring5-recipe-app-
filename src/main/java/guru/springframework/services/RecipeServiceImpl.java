package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");

        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long l) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(l);
        if (recipeOptional.isEmpty()) {
            throw new RuntimeException("Recipe was not found!");
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipe) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipe);
        Recipe res = recipeRepository.save(detachedRecipe);

        log.debug(recipe.getDescription() + " was saved");
        return recipeToRecipeCommand.convert(res);
    }

    @Override
    public RecipeCommand findCommandById(Long l) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(l);
        if (recipeOptional.isEmpty())
            throw new RuntimeException("Recipe with " + l + " doesn't exist");

        return recipeToRecipeCommand.convert(recipeOptional.get());
    }

    @Override
    public void deleteById(Long l) {
        recipeRepository.deleteById(l);
    }
}
