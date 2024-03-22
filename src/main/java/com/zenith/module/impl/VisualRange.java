package com.zenith.module.impl;

import com.zenith.Proxy;
import com.zenith.cache.data.entity.EntityPlayer;
import com.zenith.event.module.VisualRangeEnterEvent;
import com.zenith.event.module.VisualRangeLeaveEvent;
import com.zenith.event.module.VisualRangeLogoutEvent;
import com.zenith.event.proxy.NewPlayerInVisualRangeEvent;
import com.zenith.event.proxy.PlayerLeftVisualRangeEvent;
import com.zenith.event.proxy.PlayerLogoutInVisualRangeEvent;
import com.zenith.module.Module;
import com.zenith.util.Config.Client.Extra.ReplayMod.AutoRecordMode;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.github.rfresh2.EventConsumer.of;
import static com.zenith.Shared.*;

public class VisualRange extends Module {

    private @Nullable ScheduledFuture<?> visualRangeLeaveRecordingStopFuture;

    @Override
    public void subscribeEvents() {
        EVENT_BUS.subscribe(
            this,
            of(NewPlayerInVisualRangeEvent.class, this::handleNewPlayerInVisualRangeEvent),
            of(PlayerLeftVisualRangeEvent.class, this::handlePlayerLeftVisualRangeEvent),
            of(PlayerLogoutInVisualRangeEvent.class, this::handlePlayerLogoutInVisualRangeEvent)
        );
    }

    @Override
    public boolean shouldBeEnabled() {
        return CONFIG.client.extra.visualRange.enabled;
    }

    public void handleNewPlayerInVisualRangeEvent(NewPlayerInVisualRangeEvent event) {
        var isFriend = PLAYER_LISTS.getFriendsList().contains(event.playerEntity().getUuid());
        if (isFriend && CONFIG.client.extra.visualRange.ignoreFriends) {
            MODULE_LOG.debug("Ignoring visual range enter alert for friend: " + event.playerEntry().getName());
            return;
        }
        if (CONFIG.client.extra.visualRange.enterAlert) {
            EVENT_BUS.post(new VisualRangeEnterEvent(event.playerEntry(), event.playerEntity(), isFriend));
        }
        if (CONFIG.client.extra.visualRange.replayRecording && !isFriend) {
            MODULE_LOG.info("[VisualRange] Starting replay recording");
            MODULE.get(ReplayMod.class).enable();
            cancelVisualRangeLeaveRecordingStopFuture();
        }
    }

    public void handlePlayerLeftVisualRangeEvent(final PlayerLeftVisualRangeEvent event) {
        var isFriend = PLAYER_LISTS.getFriendsList().contains(event.playerEntity().getUuid());
        if (isFriend && CONFIG.client.extra.visualRange.ignoreFriends) {
            MODULE_LOG.debug("Ignoring visual range leave alert for friend: " + event.playerEntry().getName());
            return;
        }
        if (CONFIG.client.extra.visualRange.leaveAlert) {
            EVENT_BUS.post(new VisualRangeLeaveEvent(event.playerEntry(), event.playerEntity(), isFriend));
        }
        if (CONFIG.client.extra.visualRange.replayRecording && !isFriend) {
            if (!anyEnemyInVisualRange()) {
                cancelVisualRangeLeaveRecordingStopFuture();
                visualRangeLeaveRecordingStopFuture = EXECUTOR.schedule(
                    this::disableReplayRecordingConditional,
                    CONFIG.client.extra.visualRange.replayRecordingCooldownMins,
                    TimeUnit.MINUTES);
            }
        }
    }

    private void cancelVisualRangeLeaveRecordingStopFuture() {
        if (visualRangeLeaveRecordingStopFuture != null && !visualRangeLeaveRecordingStopFuture.isDone()) {
            visualRangeLeaveRecordingStopFuture.cancel(false);
        }
    }

    private void disableReplayRecordingConditional() {
        if (!MODULE.get(ReplayMod.class).isEnabled()) return;
        if (anyEnemyInVisualRange()) return;
        if (CONFIG.client.extra.replayMod.autoRecordMode == AutoRecordMode.PROXY_CONNECTED) return;
        if (CONFIG.client.extra.replayMod.autoRecordMode == AutoRecordMode.PLAYER_CONNECTED)
            if (Proxy.getInstance().hasActivePlayer()) return;
        MODULE_LOG.info("[VisualRange] Stopping replay recording");
        MODULE.get(ReplayMod.class).disable();
    }

    private boolean anyEnemyInVisualRange() {
        return CACHE.getEntityCache().getEntities().values().stream()
            .filter(entity -> entity instanceof EntityPlayer)
            .anyMatch(entityPlayer -> !PLAYER_LISTS.getFriendsList().contains(entityPlayer.getUuid()));
    }

    public void handlePlayerLogoutInVisualRangeEvent(final PlayerLogoutInVisualRangeEvent event) {
        if (!CONFIG.client.extra.visualRange.logoutAlert) return;
        var isFriend = PLAYER_LISTS.getFriendsList().contains(event.playerEntity().getUuid());
        if (isFriend && CONFIG.client.extra.visualRange.ignoreFriends) {
            MODULE_LOG.debug("Ignoring visual range leave alert for friend: " + event.playerEntry().getName());
            return;
        }
        EVENT_BUS.post(new VisualRangeLogoutEvent(event.playerEntry(), event.playerEntity(), isFriend));
    }
}