package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    @Column(length = 2000)
    private String directions;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    //TODO: Удаление игрендиента из сета и обновления рецепта в базе данных, удаляет ингредиент из БД. Благодаря orphanRemoval
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", orphanRemoval = true)
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);

        return this;
    }
}
