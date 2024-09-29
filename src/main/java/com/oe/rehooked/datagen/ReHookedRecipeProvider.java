package com.oe.rehooked.datagen;

import com.oe.rehooked.item.ReHookedItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ReHookedRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ReHookedRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReHookedItems.WOOD_HOOK.get())
                .pattern("WWP")
                .pattern(" SW")
                .pattern("W W")
                .define('W', Tags.Items.RODS_WOODEN)
                .define('S', Tags.Items.STRING)
                .define('P', Items.WOODEN_PICKAXE)
                .unlockedBy("has_rod_wooden", 
                        inventoryTrigger(ItemPredicate.Builder.item().of(Tags.Items.RODS_WOODEN).build()))
                .unlockedBy("has_string", 
                        inventoryTrigger(ItemPredicate.Builder.item().of(Tags.Items.STRING).build()))
                .save(pWriter);
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReHookedItems.IRON_HOOK.get())
                .pattern("IIP")
                .pattern(" HI")
                .pattern("C I")
                .define('C', Items.CHAIN)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Items.IRON_PICKAXE)
                .define('H', ReHookedItems.WOOD_HOOK.get())
                .unlockedBy("has_wooden_hook", 
                        inventoryTrigger(ItemPredicate.Builder.item().of(ReHookedItems.WOOD_HOOK.get()).build()))
                .unlockedBy("has_iron_ingot", 
                        inventoryTrigger(ItemPredicate.Builder.item().of(Tags.Items.INGOTS_IRON).build()))
                .unlockedBy("has_chain", 
                        inventoryTrigger(ItemPredicate.Builder.item().of(Items.CHAIN).build()))
                .save(pWriter);
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReHookedItems.DIAMOND_HOOK.get())
                .pattern("DDP")
                .pattern(" HD")
                .pattern("C D")
                .define('C', Items.CHAIN)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('P', Items.DIAMOND_PICKAXE)
                .define('H', ReHookedItems.IRON_HOOK.get())
                .unlockedBy("has_iron_hook", 
                        inventoryTrigger(ItemPredicate.Builder.item().of(ReHookedItems.IRON_HOOK.get()).build()))
                .unlockedBy("has_diamond", 
                        inventoryTrigger(ItemPredicate.Builder.item().of(Tags.Items.GEMS_DIAMOND).build()))
                .save(pWriter);
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReHookedItems.RED_HOOK.get())
                .pattern("HRH")
                .pattern(" CR")
                .pattern("C H")
                .define('C', Items.CHAIN)
                .define('R', Items.REDSTONE_BLOCK)
                .define('H', ReHookedItems.IRON_HOOK.get())
                .unlockedBy("has_iron_hook",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ReHookedItems.IRON_HOOK.get()).build()))
                .unlockedBy("has_redstone", 
                        inventoryTrigger(ItemPredicate.Builder.item().of(Tags.Items.DUSTS_REDSTONE).build()))
                .save(pWriter);
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReHookedItems.BLAZING_HOOK.get())
                .pattern("DBH")
                .pattern(" BB")
                .pattern("B D")
                .define('B', Tags.Items.RODS_BLAZE)
                .define('H', ReHookedItems.DIAMOND_HOOK.get())
                .define('D', Tags.Items.DUSTS_GLOWSTONE)
                .unlockedBy("has_diamond_hook",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ReHookedItems.DIAMOND_HOOK.get()).build()))
                .unlockedBy("has_blaze_rod", 
                        inventoryTrigger(ItemPredicate.Builder.item().of(Tags.Items.RODS_BLAZE).build()))
                .unlockedBy("has_glowstone", 
                        inventoryTrigger(ItemPredicate.Builder.item().of(Tags.Items.DUSTS_GLOWSTONE).build()))
                .save(pWriter);
                
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReHookedItems.ENDER_HOOK.get())
                .pattern("EEH")
                .pattern(" PE")
                .pattern("P E")
                .define('P', Items.ENDER_PEARL)
                .define('E', Items.ENDER_EYE)
                .define('H', ReHookedItems.DIAMOND_HOOK.get())
                .unlockedBy("has_diamond_hook",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ReHookedItems.DIAMOND_HOOK.get()).build()))
                .save(pWriter);
    }
}
