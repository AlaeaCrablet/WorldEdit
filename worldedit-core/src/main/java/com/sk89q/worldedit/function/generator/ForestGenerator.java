/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.function.generator;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.function.RegionFunction;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.util.TreeGenerator;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;

/**
 * Generates forests by searching for the ground starting from the given upper Y
 * coordinate for every column given.
 */
public class ForestGenerator implements RegionFunction {

    private final TreeGenerator.TreeType treeType;
    private final EditSession editSession;

    /**
     * Create a new instance.
     *
     * @param editSession the edit session
     * @param treeType a tree generator
     */
    public ForestGenerator(EditSession editSession, TreeGenerator.TreeType treeType) {
        this.editSession = editSession;
        this.treeType = treeType;
    }

    @Override
    public boolean apply(BlockVector3 position) throws WorldEditException {
        BlockStateHolder block = editSession.getBlock(position);
        BlockType t = block.getBlockType();

        if (t == BlockTypes.GRASS_BLOCK || t == BlockTypes.DIRT) {
            treeType.generate(editSession, position.add(0, 1, 0));
            return true;
        } else if (t == BlockTypes.GRASS || t == BlockTypes.DEAD_BUSH || t == BlockTypes.POPPY || t == BlockTypes.DANDELION) { // TODO: This list needs to be moved
            editSession.setBlock(position, BlockTypes.AIR.getDefaultState());
            treeType.generate(editSession, position);
            return true;
        } else if (t == BlockTypes.SNOW) {
            editSession.setBlock(position, BlockTypes.AIR.getDefaultState());
            return false;
        } else { // Trees won't grow on this!
            return false;
        }
    }
}
