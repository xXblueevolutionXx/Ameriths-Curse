package com.amerith.curse.block.custom;

import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Cursed Grass block - replaces all vanilla grass in Amerith's Curse.
 * Decayed, ash-grey appearance that blankets the landscape.
 */
public class CursedGrass extends BlockGrass {

    public CursedGrass() {
        super();
        this.setUnlocalizedName("cursed_grass");
        this.setRegistryName("cursed_grass");
        this.setHardness(0.6f);
        this.setResistance(0.6f);
        this.setLightLevel(0.0f); // No light emission
    }

    @Override
    public Item getItemDropped(IBlockState state, java.util.Random rand, int fortune) {
        // Drop cursed grass item, not dirt
        return Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(java.util.Random random) {
        return 1;
    }
}
