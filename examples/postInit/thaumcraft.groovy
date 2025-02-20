
// MODS_LOADED: thaumcraft
println 'mod \'thaumcraft\' detected, running script'

mods.thaumcraft.Crucible.removeByOutput(item('minecraft:gunpowder'))

mods.thaumcraft.Crucible.recipeBuilder()
        .researchKey('UNLOCKALCHEMY@3')
        .catalyst(item('minecraft:rotten_flesh'))
        .output(item('minecraft:gold_ingot'))
        .aspect(aspect('metallum') * 10)
        .register()

mods.thaumcraft.InfusionCrafting.removeByOutput(item('thaumcraft:crystal_terra'))

mods.thaumcraft.InfusionCrafting.recipeBuilder()
        .researchKey('UNLOCKALCHEMY@3')
        .mainInput(item('minecraft:gunpowder'))
        .output(item('minecraft:gold_ingot'))
        .aspect(aspect('terra') * 20)
        .aspect('ignis', 30)
        .input(crystal('aer'))
        .input(crystal('ignis'))
        .input(crystal('aqua'))
        .input(crystal('terra'))
        .input(crystal('ordo'))
        .instability(10)
        .register()

mods.thaumcraft.ArcaneWorkbench.removeByOutput(item('thaumcraft:mechanism_simple'))

mods.thaumcraft.ArcaneWorkbench.shapelessBuilder()
        .researchKey('UNLOCKALCHEMY@3')
        .input(item('minecraft:pumpkin'))
        .input(item('minecraft:stick'))
        .input(item('minecraft:stick'))
        .output(item('thaumcraft:void_hoe'))
        .vis(0)
        .register()

mods.thaumcraft.ArcaneWorkbench.shapedBuilder()
        .researchKey('UNLOCKALCHEMY@3')
        .output(item('minecraft:pumpkin'))
        .row('SS ')
        .row('   ')
        .row('   ')
        .key('S', item('minecraft:pumpkin_seeds'))
        .aspect('terra')
        .vis(5)
        .register()

//mods.thaumcraft.Aspect.aspectBuilder()
//        .tag('humor')
//        .chatColor(14013676)
//        .component(aspect('cognito'))
//        .component('perditio')
//        .image(resource('thaumcraft:textures/aspects/humor.png'))
//        .register()

mods.thaumcraft.AspectHelper.aspectBuilder()
        .object(item('minecraft:stone'))
        .stripAspects()
        .aspect(aspect('ignis') * 20)
        .aspect('ordo', 5)
        .register()

mods.thaumcraft.AspectHelper.aspectBuilder()
        .object(ore('cropPumpkin'))
        .stripAspects()
        .aspect(aspect('herba') * 20)
        .register()

mods.thaumcraft.AspectHelper.aspectBuilder()
        .entity(entity('minecraft:chicken'))
        .stripAspects()
        .aspect('bestia', 20)
        .register()

mods.thaumcraft.Warp.addWarp(item('minecraft:pumpkin'), 3)
mods.thaumcraft.Warp.removeWarp(item('thaumcraft:void_hoe'))

mods.thaumcraft.DustTrigger.removeByOutput(item('thaumcraft:arcane_workbench'))

mods.thaumcraft.DustTrigger.triggerBuilder()
        .researchKey('UNLOCKALCHEMY@3')
        .target(block('minecraft:obsidian'))
        .output(item('minecraft:enchanting_table'))
        .register()

mods.thaumcraft.DustTrigger.triggerBuilder()
        .researchKey('UNLOCKALCHEMY@3')
        .target(ore('cropPumpkin'))
        .output(item('minecraft:lit_pumpkin'))
        .register()

mods.thaumcraft.LootBag.getRare().removeAll()
mods.thaumcraft.LootBag.getRare().addItem(item('minecraft:diamond_block'), 100)
mods.thaumcraft.LootBag.getCommon().removeItem(item('minecraft:ender_pearl'))
mods.thaumcraft.LootBag.getCommon().addItem(item('minecraft:dirt'), 100)

mods.thaumcraft.SmeltingBonus.recipeBuilder()
        .input(item('minecraft:cobblestone'))
        .output(item('minecraft:stone_button'))
        .chance(0.2F)
        .register()

mods.thaumcraft.SmeltingBonus.recipeBuilder()
        .input(ore('stone'))
        .output(item('minecraft:obsidian'))
        .register()

mods.thaumcraft.SmeltingBonus.removeByOutput(item('minecraft:gold_nugget'))

//mods.thaumcraft.SmeltingBonus.stream()
//        .filter{ bonus -> bonus.in == 'oreDiamond' }
//        .removeAll()

//mods.thaumcraft.Research.researchCategoryBuilder()
//    .key('BASICS2')
//    .researchKey('UNLOCKAUROMANCY')
//    .formulaAspect(aspect('herba') * 5)
//    .formulaAspect(aspect('ordo') * 5)
//    .formulaAspect(aspect('perditio') * 5)
//    .formulaAspect('aer', 5)
//    .formulaAspect('ignis', 5)
//    .formulaAspect(aspect('terra') * 5)
//    .formulaAspect('aqua', 5)
//    .icon(resource('thaumcraft:textures/aspects/humor.png'))
//    .background(resource('thaumcraft:textures/gui/gui_research_back_1.jpg'))
//    .background2(resource('thaumcraft:textures/gui/gui_research_back_over.png'))
//    .register()
//
//mods.thaumcraft.Research.addResearchLocation(resource('thaumcraft:research/new.json'))

mods.thaumcraft.Research.addScannable('KNOWLEDGETYPEHUMOR', item('minecraft:pumpkin'))

//mods.thaumcraft.Research.removeCategory('BASICS');