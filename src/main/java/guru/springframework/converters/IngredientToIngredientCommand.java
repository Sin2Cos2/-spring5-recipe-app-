package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    @Synchronized
    @NonNull
    @Override
    public IngredientCommand convert(Ingredient source) {

        if (source == null)
            throw new RuntimeException("Source is null!");

        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setUom(converter.convert(source.getUnitOfMeasure()));
        if (source.getRecipe() != null) {
            ingredientCommand.setRecipeId(source.getRecipe().getId());
        }
        ingredientCommand.setId(source.getId());
        ingredientCommand.setDescription(source.getDescription());
        ingredientCommand.setAmount(source.getAmount());

        return ingredientCommand;
    }
}
