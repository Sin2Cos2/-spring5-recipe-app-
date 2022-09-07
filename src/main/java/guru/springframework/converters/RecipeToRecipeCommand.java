package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final IngredientToIngredientCommand toIngredientCommand;
    private final NotesToNotesCommand toNotesCommand;
    private final CategoryToCategoryCommand toCategoryCommand;

    @NonNull
    @Synchronized
    @Override
    public RecipeCommand convert(Recipe source) {

        if (source == null)
            throw new RuntimeException("Source is null!");

        final RecipeCommand recipe = new RecipeCommand();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setDirections(source.getDirections());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setNotes(toNotesCommand.convert(source.getNotes()));
        recipe.setImage(source.getImage());

        if (source.getIngredients() != null && source.getIngredients().size() > 0)
            source.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(toIngredientCommand.convert(ingredient)));

        if (source.getCategories() != null && source.getCategories().size() > 0)
            source.getCategories()
                    .forEach(category -> recipe.getCategories().add(toCategoryCommand.convert(category)));

        return recipe;
    }
}
