package me.despical.papi.party.member;

import me.despical.papi.party.Party;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Despical
 * @since 1.0.0
 * <p>
 * Created at 16.01.2021
 */
public class Member {

	@Nullable
	private Party party;

	/**
	 * Player instance
	 */
	@NotNull
	private final Player player;

	public Member(@NotNull Player player) {
		this.player = player;
	}

	public boolean isInParty() {
		return false;
	}

	@NotNull
	public Player getPlayer() {
		return player;
	}

	@Nullable
	@Contract(pure = true)
	public Party getParty() {
		return party;
	}
}