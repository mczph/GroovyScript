package com.cleanroommc.groovyscript.compat.mods.immersiveengineering;

import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;
import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.compat.mods.ModSupport;
import com.cleanroommc.groovyscript.helper.ArrayUtils;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.helper.ingredient.IngredientHelper;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArcFurnace extends VirtualizedRegistry<ArcFurnaceRecipe> {

    public ArcFurnace() {
        super();
    }

    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    @Override
    @GroovyBlacklist
    @ApiStatus.Internal
    public void onReload() {
        removeScripted().forEach(r -> ArcFurnaceRecipe.recipeList.removeIf(recipe -> recipe == r));
        ArcFurnaceRecipe.recipeList.addAll(restoreFromBackup());
    }

    public void add(ArcFurnaceRecipe recipe) {
        if (recipe != null) {
            ArcFurnaceRecipe.recipeList.add(recipe);
            addScripted(recipe);
        }
    }

    public ArcFurnaceRecipe add(ItemStack output, IIngredient input, List<IIngredient> additives, @Nonnull ItemStack slag, int time, int energyPerTick) {
        Object[] inputs = ArrayUtils.mapToArray(additives, ImmersiveEngineering::toIngredientStack);
        ArcFurnaceRecipe recipe = ArcFurnaceRecipe.addRecipe(output, ImmersiveEngineering.toIngredientStack(input), slag, time, energyPerTick, inputs);
        addScripted(recipe);
        return recipe;
    }

    public boolean remove(ArcFurnaceRecipe recipe) {
        if (ArcFurnaceRecipe.recipeList.removeIf(r -> r == recipe)) {
            addBackup(recipe);
            return true;
        }
        return false;
    }

    public void removeByOutput(ItemStack output) {
        if (IngredientHelper.isEmpty(output)) {
            GroovyLog.msg("Error removing Immersive Engineering Arc Furnace recipe")
                    .add("output must not be empty")
                    .error()
                    .post();
            return;
        }
        List<ArcFurnaceRecipe> recipes = ArcFurnaceRecipe.recipeList.stream().filter(r -> r.output != null && r.output.isItemEqual(output)).collect(Collectors.toList());
        for (ArcFurnaceRecipe recipe : recipes) {
            remove(recipe);
        }
        if (recipes.isEmpty()) {
            GroovyLog.msg("Error removing Immersive Engineering Arc Furnace recipe")
                    .add("no recipes found for {}", output)
                    .error()
                    .post();
        }
    }

    public void removeByInput(IIngredient main, List<IIngredient> inputAndAdditives) {
        if (main == null || main.isEmpty() || inputAndAdditives == null || inputAndAdditives.isEmpty()) {
            GroovyLog.msg("Error removing Immersive Engineering Arc Furnace recipe")
                    .add("inputs must not be empty")
                    .error()
                    .post();
            return;
        }
        List<ArcFurnaceRecipe> recipes = ArcFurnaceRecipe.recipeList.stream()
                .filter(r -> ImmersiveEngineering.areIngredientsEquals(r.input, main) &&
                             (inputAndAdditives.stream().anyMatch(check -> Arrays.stream(r.additives).anyMatch(target -> ImmersiveEngineering.areIngredientsEquals(target, check)))))
                .collect(Collectors.toList());
        for (ArcFurnaceRecipe recipe : recipes) {
            remove(recipe);
        }
        if (recipes.isEmpty()) {
            GroovyLog.msg("Error removing Immersive Engineering Arc Furnace recipe")
                    .add("no recipes found with a main ingredient of {}, and additives of {}", main, inputAndAdditives)
                    .error()
                    .post();
        }
    }


    public void removeByInput(List<IIngredient> inputAndAdditives) {
        if (inputAndAdditives == null || inputAndAdditives.isEmpty()) {
            GroovyLog.msg("Error removing Immersive Engineering Arc Furnace recipe")
                    .add("inputs must not be empty")
                    .error()
                    .post();
            return;
        }
        removeByInput(inputAndAdditives.remove(0), inputAndAdditives);
    }

    public void removeByInput(IIngredient... inputAndAdditives) {
        removeByInput(new ArrayList<>(Arrays.asList(inputAndAdditives)));
    }

    public SimpleObjectStream<ArcFurnaceRecipe> streamRecipes() {
        return new SimpleObjectStream<>(ArcFurnaceRecipe.recipeList).setRemover(this::remove);
    }

    public void removeAll() {
        ArcFurnaceRecipe.recipeList.forEach(this::addBackup);
        ArcFurnaceRecipe.recipeList.clear();
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<ArcFurnaceRecipe> {

        private int time;
        private IIngredient mainInput;
        private int energyPerTick;
        private ItemStack slag = ItemStack.EMPTY;
        private String specialRecipeType;


        public RecipeBuilder time(int time) {
            this.time = time;
            return this;
        }

        public RecipeBuilder mainInput(IIngredient mainInput) {
            this.mainInput = mainInput;
            return this;
        }

        public RecipeBuilder energyPerTick(int energy) {
            this.energyPerTick = energy;
            return this;
        }

        public RecipeBuilder slag(ItemStack slag) {
            this.slag = slag;
            return this;
        }

        public RecipeBuilder specialRecipeType(String specialRecipeType) {
            this.specialRecipeType = specialRecipeType;
            return this;
        }

        public RecipeBuilder alloying() {
            this.specialRecipeType = "Alloying";
            return this;
        }

        public RecipeBuilder ores() {
            this.specialRecipeType = "Ores";
            return this;
        }

        public RecipeBuilder recycling() {
            this.specialRecipeType = "Recycling";
            return this;
        }

        @Override
        public String getErrorMsg() {
            return "Error adding Immersive Engineering Arc Furnace recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            if (input.size() >= 2 && mainInput == null) {
                mainInput = input.remove(0);
            }
            validateItems(msg, 0, 4, 1, 1);
            validateFluids(msg);
            msg.add(mainInput == null, "mainInput must be defined");
            msg.add(time <= 0, "time must be greater than 0, yet it was {}", time);
            msg.add(energyPerTick <= 0, "energyPerTick must be greater than 0, yet it was {}", energyPerTick);
            msg.add(slag == null, "slag must be defined");
        }

        @Override
        public @Nullable ArcFurnaceRecipe register() {
            if (!validate()) return null;
            Object[] additives = ArrayUtils.mapToArray(input, ImmersiveEngineering::toIngredientStack);
            ArcFurnaceRecipe recipe = new ArcFurnaceRecipe(output.get(0), mainInput, slag, time, energyPerTick, additives);
            if (specialRecipeType != null) recipe.setSpecialRecipeType(specialRecipeType);
            ModSupport.IMMERSIVE_ENGINEERING.get().arcFurnace.add(recipe);
            return recipe;
        }
    }
}
