package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository repository;
    private final IngredientToIngredientCommand toIngredientCommand;
    private final IngredientCommandToIngredient toIngredient;
    private final UnitOfMeasureRepository uomRepository;


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = repository.findById(recipeId);
        if (recipeOptional.isEmpty())
            throw new RuntimeException("Recipe with " + recipeId + " id doesn't exits");

        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .map(toIngredientCommand::convert)
                .findFirst();

        if (ingredientCommandOptional.isEmpty())
            throw new RuntimeException("Ingredient with " + id + " id doesn't  exist");

        return ingredientCommandOptional.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = repository.findById(command.getRecipeId());

        if (recipeOptional.isEmpty())
            throw new RuntimeException("Recipe with " + command.getRecipeId() + " id doesn't exits");

        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

        Ingredient savedIngredient;
        if (ingredientOptional.isPresent()) {
            savedIngredient = ingredientOptional.get();
            savedIngredient.setAmount(command.getAmount());
            savedIngredient.setDescription(command.getDescription());
            savedIngredient.setUnitOfMeasure(uomRepository.findById(command
                            .getUom()
                            .getId())
                    .orElseThrow(() -> new RuntimeException("Unit of measure was not found")));
        } else {
            savedIngredient = toIngredient.convert(command);
            recipe.addIngredient(savedIngredient);
        }

        Recipe savedRecipe = repository.save(recipe);

        if (command.getId() == null) {
            command.setId(recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                    .filter(ingredient -> ingredient.getDescription().equalsIgnoreCase(command.getDescription()))
                    .filter(ingredient -> ingredient.getUnitOfMeasure().getId().equals(command.getUom().getId()))
                    .findFirst()
                    .get()
                    .getId());
        }

        return toIngredientCommand.convert(savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ingredient with " + command.getId() + " id doesn't  exist")));
    }

    @Override
    public void deleteByRecipeIdAndIngredientId(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = repository.findById(recipeId);

        if (recipeOptional.isEmpty())
            throw new RuntimeException("Recipe with " + recipeId + " id doesn't exits");

        Recipe recipe = recipeOptional.get();

        Ingredient ingredient = recipe.getIngredients().stream()
                .filter(ingredient1 -> ingredient1.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ingredient with " + id + " id doesn't  exist"));

        recipe.getIngredients().remove(ingredient);
        repository.save(recipe);
    }
}
