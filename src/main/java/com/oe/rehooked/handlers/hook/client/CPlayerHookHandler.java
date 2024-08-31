package com.oe.rehooked.handlers.hook.client;

import com.mojang.logging.LogUtils;
import com.oe.rehooked.data.HookData;
import com.oe.rehooked.data.HookRegistry;
import com.oe.rehooked.entities.hook.HookEntity;
import com.oe.rehooked.handlers.hook.def.IClientPlayerHookHandler;
import com.oe.rehooked.handlers.hook.def.ICommonPlayerHookHandler;
import com.oe.rehooked.item.hook.HookItem;
import com.oe.rehooked.network.handlers.PacketHandler;
import com.oe.rehooked.network.packets.server.SHookCapabilityPacket;
import com.oe.rehooked.utils.CurioUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CPlayerHookHandler implements IClientPlayerHookHandler {
    private static final Logger LOGGER = LogUtils.getLogger();
    
    private final List<HookEntity> hooks;
    private Optional<Player> owner;
    
    private Vec3 moveVector; 
    
    public CPlayerHookHandler() {
        hooks = new ArrayList<>();
        owner = Optional.empty();
        moveVector = null;
    }
    
    @Override
    public void addHook(int id) {
        owner.map(Player::level).ifPresent(level -> {
            Entity entity = level.getEntity(id);
            if (entity instanceof HookEntity hookEntity) {
                hooks.add(hookEntity);
                hookEntity.setOwner(owner.get());
            }
        });
    }

    @Override
    public void addHook(HookEntity hookEntity) {
        owner.ifPresentOrElse(owner -> {
            hooks.add(hookEntity);
            hookEntity.setOwner(owner);
        }, () -> hooks.add(hookEntity));
    }

    @Override
    public void removeHook(int id) {
        hooks.removeIf(hookEntity -> hookEntity.getId() == id);
    }

    @Override
    public void removeHook(HookEntity hookEntity) {
        PacketHandler.sendToServer(new SHookCapabilityPacket(SHookCapabilityPacket.State.RETRACT_HOOK, hookEntity.getId()));
        hooks.remove(hookEntity);
    }

    @Override
    public void removeAllHooks() {
        // this is a response to a request from the player
        // notify the server
        getOwner().ifPresent(owner -> 
                PacketHandler.sendToServer(new SHookCapabilityPacket(SHookCapabilityPacket.State.RETRACT_ALL_HOOKS)));
        // clear hooks
        hooks.clear();
    }

    @Override
    public void shootFromRotation(float xRot, float yRot) {
        LOGGER.debug("Shooting from rotation: {}, {}", xRot, yRot);
        PacketHandler.sendToServer(new SHookCapabilityPacket(SHookCapabilityPacket.State.SHOOT, 0, xRot, yRot));
    }

    @Override
    public ICommonPlayerHookHandler setOwner(Player owner) {
        this.owner = Optional.of(owner);
        return this;
    }

    @Override
    public Optional<Player> getOwner() {
        return owner;
    }

    @Override
    public Optional<HookData> getHookData() {
        return getOwner()
                .flatMap(owner -> CurioUtils.GetCuriosOfType(HookItem.class, owner))
                .flatMap(CurioUtils::GetIfUnique)
                .map(ItemStack::getItem)
                .map(item -> ((HookItem) item).getHookType())
                .flatMap(HookRegistry::getHookData);
    }

    @Override
    public void update() {
        moveVector = null;
        getOwner().ifPresent(owner -> {
            owner.setNoGravity(false);
            getHookData().ifPresent(hookData -> {
                float vPT = hookData.pullSpeed() / 20f;
                int count = 0;
                double x = 0, y = 0, z = 0;
                for (HookEntity hookEntity : hooks) {
                    if (hookEntity.getState().equals(HookEntity.State.PULLING)) {
                        count++;
                        Vec3 vectorTo = owner.position().vectorTo(hookEntity.position());
                        // todo: maybe scale the vector down based on how close the player is to the hook
                        // todo: if the player is closer the hooks pull should be less strong
                        x += vectorTo.x;
                        y += vectorTo.y;
                        z += vectorTo.z;
                    }
                }
                if (count == 0) return;
                owner.setNoGravity(true);
                Vec3 baseVector = new Vec3(x, y, z);
                if (baseVector.length() < THRESHOLD) return;
                moveVector = baseVector.normalize().scale(vPT * count);
            });
        });
    }

    @Override
    public boolean shouldMoveThisTick() {
        return moveVector != null;
    }

    @Override
    public Vec3 getDeltaVThisTick() {
        return moveVector;
    }
}
