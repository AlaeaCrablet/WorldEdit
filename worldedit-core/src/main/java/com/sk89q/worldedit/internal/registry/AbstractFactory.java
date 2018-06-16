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

package com.sk89q.worldedit.internal.registry;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extension.input.ParserContext;
import com.sk89q.worldedit.extension.input.InputParseException;
import com.sk89q.worldedit.extension.input.NoMatchException;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An abstract implementation of a factory for internal usage.
 *
 * @param <E> the element that the factory returns
 */
@SuppressWarnings("ProtectedField")
public abstract class AbstractFactory<E> {

    protected final WorldEdit worldEdit;
    protected final List<InputParser<E>> parsers = new ArrayList<>();

    /**
     * Create a new factory.
     *
     * @param worldEdit the WorldEdit instance
     */
    protected AbstractFactory(WorldEdit worldEdit) {
        checkNotNull(worldEdit);
        this.worldEdit = worldEdit;
    }

    public E parseFromInput(String input, ParserContext context) throws InputParseException {
        E match;

        for (InputParser<E> parser : parsers) {
            match = parser.parseFromInput(input, context);

            if (match != null) {
                return match;
            }
        }

        throw new NoMatchException("No match for '" + input + "'");
    }

}
