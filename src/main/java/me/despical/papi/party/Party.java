package me.despical.papi.party;

import me.despical.papi.party.member.Member;
import me.despical.papi.party.member.PartyLeader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * The base class of all parties
 *
 * @author Despical
 * @since 1.0.0
 * <p>
 * Created at 16.01.2021
 */
public class Party {

	@Nullable
	private String partyName;

	@Nullable
	private PartyLeader partyLeader;

	@NotNull
	private final Set<Member> partyMembers;

	public Party() {
		this(null, null, null);
	}

	public Party(@NotNull String partyName) {
		this(null, partyName);
	}

	public Party(@Nullable PartyLeader partyLeader, @Nullable String partyName) {
		this(partyLeader, null, partyName);
	}

	public Party(@Nullable PartyLeader partyLeader, @Nullable Set<Member> partyMembers, @Nullable String partyName) {
		this.partyLeader = partyLeader;
		this.partyMembers = partyMembers == null ? new HashSet<>() : partyMembers;
		this.partyName = partyName;
	}

	public void addMember(@NotNull Member member) {
		this.partyMembers.add(member);
	}

	public void removeMember(@NotNull Member member) {
		this.partyMembers.remove(member);
	}

	@Nullable
	@Contract(pure = true)
	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(@NotNull String partyName) {
		this.partyName = partyName;
	}

	@Nullable
	@Contract(pure = true)
	public PartyLeader getPartyLeader() {
		return partyLeader;
	}

	public void setPartyLeader(@NotNull PartyLeader partyLeader) {
		if (partyLeader.isInParty()) {
			throw new IllegalStateException("Party leader is already in another team!");
		}

		this.partyLeader = partyLeader;
	}

	@NotNull
	@Contract(pure = true)
	public Set<Member> getPartyMembers() {
		Set<Member> members = new HashSet<>(partyMembers);

		if (partyLeader != null) {
			members.add(partyLeader);
		}

		return Collections.unmodifiableSet(members);
	}
}