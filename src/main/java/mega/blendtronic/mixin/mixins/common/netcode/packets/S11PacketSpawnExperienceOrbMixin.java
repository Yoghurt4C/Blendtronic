/*
 * Blendtronic
 *
 * Copyright (C) 2021-2024 SirFell, the MEGA team
 * All Rights Reserved
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package mega.blendtronic.mixin.mixins.common.netcode.packets;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import mega.blendtronic.modules.netcode.AccurateMotionContainer;
import mega.blendtronic.modules.netcode.AccuratePositionContainer;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Accessors(fluent = true, chain = false)
@Mixin(S11PacketSpawnExperienceOrb.class)
public abstract class S11PacketSpawnExperienceOrbMixin implements AccuratePositionContainer, AccurateMotionContainer {
    @Getter
    @Setter
    @Unique
    private double accurate$posX;

    @Getter
    @Setter
    @Unique
    private double accurate$posY;

    @Getter
    @Setter
    @Unique
    private double accurate$posZ;

    @Getter
    @Setter
    @Unique
    private double accurate$motionX;

    @Getter
    @Setter
    @Unique
    private double accurate$motionY;

    @Getter
    @Setter
    @Unique
    private double accurate$motionZ;

    @Inject(method = "<init>(Lnet/minecraft/entity/item/EntityXPOrb;)V",
            at = @At("RETURN"),
            require = 1)
    private void onConstruct(EntityXPOrb entity, CallbackInfo ci) {
        accurate$pos(entity.posX, entity.posY, entity.posZ);
        accurate$motion(entity.motionX, entity.motionY, entity.motionZ);
    }

    @Inject(method = "readPacketData(Lnet/minecraft/network/PacketBuffer;)V",
            at = @At("RETURN"),
            require = 1)
    private void onReadPacketData(PacketBuffer data, CallbackInfo ci) {
        accuratePosition$readPacketData(data);
        accurateMotion$readPacketData(data);
    }

    @Inject(method = "writePacketData(Lnet/minecraft/network/PacketBuffer;)V",
            at = @At("RETURN"),
            require = 1)
    private void onWritePacketData(PacketBuffer data, CallbackInfo ci) {
        accuratePosition$writePacketData(data);
        accurateMotion$writePacketData(data);
    }
}
